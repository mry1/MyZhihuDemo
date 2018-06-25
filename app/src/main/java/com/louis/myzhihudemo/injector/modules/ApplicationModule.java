package com.louis.myzhihudemo.injector.modules;

import android.content.Context;

import com.louis.myzhihudemo.AndroidApplication;
import com.louis.myzhihudemo.local.table.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louis on 17-4-17.
 */

@Module
public class ApplicationModule {

    private final AndroidApplication mApplication;
    private final DaoSession mDaoSession;

    public ApplicationModule(AndroidApplication application, DaoSession daoSession) {
        mApplication = application;
        mDaoSession = daoSession;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @Singleton
    @Provides
    DaoSession provideDaoSession() {
        return mDaoSession;
    }
}
