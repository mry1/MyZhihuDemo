package com.louis.myzhihudemo.ui.photo.main;

import com.louis.myzhihudemo.base.BasePresenter;

/**
 * Created by louis on 17-11-22.
 */

public class PhotoMainPresent extends BasePresenter {
    private PhotoMainFragment mView;

    public PhotoMainPresent(PhotoMainFragment view) {
        mView = view;
    }

    @Override
    public void getData(boolean isRefresh) {

        mView.loadData();

    }

    @Override
    public void getMoreData() {

    }
}
