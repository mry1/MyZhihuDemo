package com.louis.myzhihudemo.ui.news.detail;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.StoryDetail;
import com.louis.myzhihudemo.base.BasePresenter;

import io.reactivex.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/5/18.
 */

public class NewsDetailPresent extends BasePresenter {
    private NewsDetailActivity mView;

    public NewsDetailPresent(NewsDetailActivity view) {
        mView = view;
    }

    /**
     * 获取详情
     *
     * @param storyID
     */
    public void getNewsDetail(long storyID) {
        RetrofitService.getInstance().getStoryDetail(storyID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StoryDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StoryDetail storyDetail) {

                    }
                });

    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
