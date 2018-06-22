package com.louis.myzhihudemo.rxbus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by quekangkang on 2016/10/12.
 * <p/>
 * PublishSubject:只会把订阅发生的时间地点之后来原始的Observable的数据发射给观察者
 */
public class RxBus {
    private final Subject<Object,Object> mBus;

    /**
     * sticky事件以 clazz作为key，一个clazz对应只能保存一个sticky事件
     */
    private final Map<Class<?>,Object> mStickyEventMap;

    public RxBus() {
        mBus = PublishSubject.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getDefault() {
        //使用holder模式得到单例
        return RxBusHolder.getInstance();
    }


    /**
     * 解释一下，因为java机制规定，内部类RxBusHolder只有在getInstance()方法第一次调用的时候才会被加载（实现了lazy），而且其加载过程是线程安全的（实现线程安全）。内部类加载的时候实例化一次instance。
     */
    private static class RxBusHolder{
        private static volatile RxBus mDefaultInstance = new RxBus();

        public static RxBus getInstance() {
            return RxBusHolder.mDefaultInstance;
        }
    }

    /**
     * 发送事件
     * @param event
     */
    public void post(Object event) {
        mBus.onNext(event);
    }

    /**
     * 初始化一个新的可观察者  根据传递的 eventType 类型返回特定类型(eventType)的 可观察者
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType){
        return mBus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     * @return
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    //-------------------------------Sticky事件相关
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    /**
     * 初始化一个新的可观察者  根据传递的eventType类型  返回特定类型的可观察者
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = mBus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);

            //存有未发送的sticky事件
            if (event != null) {
                return observable.mergeWith(Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(eventType.cast(event));
                    }
                }));
            }else{
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取stikcky事件
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的sticky事件
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }

}
