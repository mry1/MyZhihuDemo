package com.louis.myzhihudemo.ui.news.newslist;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.base.BasePresenter;

import io.reactivex.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/5/13.
 */

public class NewsListPresent extends BasePresenter {

    private INewsListView mView;
    private int mStroryID;

    public NewsListPresent(INewsListView view, int storyID) {
        mView = view;
        mStroryID = storyID;

    }

    @Override
    public void getData(boolean isRefresh) {

        RetrofitService.getInstance().getThemeStories(mStroryID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StoryList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StoryList storyList) {

                    }
                });


    }

    @Override
    public void getMoreData() {

    }
}
