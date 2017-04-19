package com.louis.myzhihudemo.ui.news.main;

import com.louis.myzhihudemo.base.BasePresenter;

/**
 * Created by louis on 17-4-19.
 */

public class NewsMainPresent extends BasePresenter{
    private final INewsMainView mView;

    public NewsMainPresent(INewsMainView view) {
        mView = view;
    }


    @Override
    public void getData(boolean isRefresh) {


    }

    @Override
    public void getMoreData() {

    }
}
