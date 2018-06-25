package com.louis.myzhihudemo.ui.photo.beauty;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.utils.ImageLoader;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louis on 17-11-23.
 */

public class BeautyListPresent extends BasePresenter {
    private BeautyListFragment mView;
    private int mPage = 1;

    public BeautyListPresent(BeautyListFragment view) {
        mView = view;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getInstance().getBeautyPhotoList(mPage)
                .doOnSubscribe(new Action0() {

                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<BeautyPhotoInfo>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                        mView.showNetError();

                    }

                    @Override
                    public void onNext(List<BeautyPhotoInfo> datas) {
                        mView.loadData(datas);
                        mPage++;
                    }
                });

    }

    @Override
    public void getMoreData() {
        RetrofitService.getInstance().getBeautyPhotoList(mPage)
                .compose(mView.<BeautyPhotoInfo>bindToLifecycle())
                .compose(mTransformer)
                .subscribe(new Subscriber<List<BeautyPhotoInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                        mView.loadMoreFail();

                    }

                    @Override
                    public void onNext(List<BeautyPhotoInfo> datas) {
                        if (datas == null) {
                            mView.loadMoreEnd();
                        } else {
                            mView.loadMoreData(datas);
                        }
                        mPage++;
                    }
                });
    }


    /**
     * 统一变换
     */
    private Observable.Transformer<BeautyPhotoInfo, List<BeautyPhotoInfo>> mTransformer = new Observable.Transformer<BeautyPhotoInfo, List<BeautyPhotoInfo>>() {

        @Override
        public Observable<List<BeautyPhotoInfo>> call(Observable<BeautyPhotoInfo> BeautyPhotoInfoObservable) {
            return BeautyPhotoInfoObservable
                    .observeOn(Schedulers.io())
                    // 接口返回的数据是没有宽高参数的，所以这里设置图片的宽度和高度，速度会慢一点
                    .filter(new Func1<BeautyPhotoInfo, Boolean>() {
                        @Override
                        public Boolean call(BeautyPhotoInfo photoBean) {
                            try {
                                photoBean.setPixel(ImageLoader.calePhotoSize(mView.getContext(), photoBean.getUrl()));
                                return true;
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                return false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .toList();
        }
    };
}
