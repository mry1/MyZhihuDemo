package com.louis.myzhihudemo.ui.news.newslist_home;

import android.nfc.Tag;
import android.util.Log;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/5/16.
 */

public class HomePagePresent extends BasePresenter {
    HomePageFragment mView;
    private String TAG = getClass().getName();

    public HomePagePresent(HomePageFragment view) {
        mView = view;
    }

    @Override
    public void getData(final boolean isRefresh) {
        RetrofitService.getInstance().getHomeStory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .subscribe(new Subscriber<HomeStory>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted" + isRefresh);
                        if (!isRefresh) {
                            mView.hideLoading();
                        } else {
                            mView.finishRefresh();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isRefresh) {
                            mView.finishRefresh();
                            ToastUtils.showToast("刷新失败");
                        } else {
                            mView.showNetError();
                        }

                    }

                    @Override
                    public void onNext(HomeStory homeStory) {
                        System.out.println("homeStory::" + homeStory);
                        mView.loadData(homeStory);

                    }
                });


    }

    @Override
    public void getMoreData() {

    }
}
