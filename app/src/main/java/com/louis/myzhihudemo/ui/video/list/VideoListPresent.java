package com.louis.myzhihudemo.ui.video.list;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.VideoInfo;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.local.table.DaoSession;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

public class VideoListPresent extends BasePresenter {
    private VideoListFragment mView;
    private String mVideoId;
    private DaoSession mDaoSession;
    private int mPage = 0;

    public VideoListPresent(VideoListFragment view, DaoSession daoSession, String videoId) {
        mView = view;
        this.mDaoSession = daoSession;
        this.mVideoId = videoId;
    }


    @Override
    public void getData(final boolean isRefresh) {
        RetrofitService.getInstance().getVideoList(mVideoId, mPage)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<List<VideoInfo>>bindToLife())
                .subscribe(new Subscriber<List<VideoInfo>>() {
                    @Override
                    public void onCompleted() {
                        if (isRefresh) {
                            mView.finishRefresh();
                        } else {
                            mView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(List<VideoInfo> videoInfos) {
                        mView.loadData(videoInfos);
                        mPage++;
                    }
                });

    }

    @Override
    public void getMoreData() {
        RetrofitService.getInstance().getVideoList(mVideoId, mPage)
                .compose(mView.<List<VideoInfo>>bindToLife())
                .subscribe(new Subscriber<List<VideoInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.loadMoreFail();
                    }

                    @Override
                    public void onNext(List<VideoInfo> videoList) {
                        if (videoList == null) {
                            mView.loadMoreEnd();
                        } else {
                            mView.loadMoreData(videoList);
                        }
                        mPage++;
                    }
                });

    }
}
