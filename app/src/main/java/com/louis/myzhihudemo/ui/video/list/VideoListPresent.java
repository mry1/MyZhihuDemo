package com.louis.myzhihudemo.ui.video.list;

import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.local.table.DaoSession;

public class VideoListPresent extends BasePresenter {
    private VideoListFragment mView;
    private String videoId;
    private DaoSession mDaoSession;

    public VideoListPresent(VideoListFragment view, DaoSession daoSession, String videoId) {
        mView = view;
        this.mDaoSession = daoSession;
        this.videoId = videoId;
    }


    @Override
    public void getData(boolean isRefresh) {


    }

    @Override
    public void getMoreData() {

    }
}
