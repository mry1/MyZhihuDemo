package com.louis.myzhihudemo.ui.news.newslist;

import android.util.Log;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import io.reactivex.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/5/13.
 */

public class NewsListPresent extends BasePresenter {

    private NewsListFragment mView;
    private int mStroryID;

    public NewsListPresent(NewsListFragment view, int storyID) {
        mView = view;
        mStroryID = storyID;

    }

    public void test() {
        {
        }
    }

    @Override
    public void getData(final boolean isRefresh) {

        RetrofitService.getInstance().getThemeStories(mStroryID)
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
                .subscribe(new Subscriber<StoryList>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted", isRefresh);
                        if (isRefresh) {
                            mView.finishRefresh();
                        } else {
                            mView.hideLoading();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage() + isRefresh);
                        if (isRefresh){
                            mView.finishRefresh();
                            ToastUtils.showToast("刷新失败提示什么根据实际情况");

                        }else{
                            mView.showNetError();
                        }

                    }

                    @Override
                    public void onNext(StoryList storyList) {
                        mView.loadData(storyList);

                    }
                });


    }

    @Override
    public void getMoreData() {

    }
}
