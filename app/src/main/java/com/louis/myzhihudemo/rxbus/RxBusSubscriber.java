package com.louis.myzhihudemo.rxbus;

import com.louis.myzhihudemo.utils.LogUtils;

import rx.Subscriber;

/**
 * Created by quekangkang on 2016/10/12.
 * 为了使用的Subscriber,主要提供next事件的try，catch
 */
public abstract class RxBusSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "RxBusSubscriber";

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        LogUtils.d(TAG, "onError");
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        try {
            onEvent(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void onEvent(T t);
}
