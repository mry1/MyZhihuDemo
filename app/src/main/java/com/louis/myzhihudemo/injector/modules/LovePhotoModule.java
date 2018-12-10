package com.louis.myzhihudemo.injector.modules;

import android.content.Context;

import com.louis.myzhihudemo.adapter.BeautyListAdapter;
import com.louis.myzhihudemo.api.bean.DaoSession;
import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.ui.manage.love.LovePhotoPresent;
import com.louis.myzhihudemo.ui.manage.photo.LovePhotoFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louis on 17-12-4.
 */

@Module
public class LovePhotoModule {
    LovePhotoFragment mView;

    public LovePhotoModule(LovePhotoFragment view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public LovePhotoPresent provideLovePhotoPresent(DaoSession daoSession) {

        return new LovePhotoPresent(mView, daoSession.getBeautyPhotoInfoDao());
    }

    @PerFragment
    @Provides
    public BeautyListAdapter provideBeautyListAdapter() {
        return new BeautyListAdapter(mView.getContext(), null);
    }

}
