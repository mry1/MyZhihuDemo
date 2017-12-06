package com.louis.myzhihudemo.ui.manage.love;

import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfoDao;
import com.louis.myzhihudemo.local.table.DaoSession;
import com.louis.myzhihudemo.ui.manage.photo.LovePhotoFragment;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by louis on 17-12-4.
 */

public class LovePhotoPresent extends BasePresenter {
    private LovePhotoFragment mView;
    private BeautyPhotoInfoDao mDao;

    public LovePhotoPresent(LovePhotoFragment view, BeautyPhotoInfoDao daoSession) {
        mView = view;
        mDao = daoSession;
    }

    @Override
    public void getData(boolean isRefresh) {
        mDao.queryBuilder().where(BeautyPhotoInfoDao.Properties.IsLove.eq(true))
                .rx()
                .list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BeautyPhotoInfo>>() {
                    @Override
                    public void call(List<BeautyPhotoInfo> beautyPhotoInfos) {
                        if (beautyPhotoInfos.size() == 0) {
                            mView.loadNoData();


                        } else {
                            mView.loadData(beautyPhotoInfos);
                        }
                    }
                });

    }

    @Override
    public void getMoreData() {

    }
}
