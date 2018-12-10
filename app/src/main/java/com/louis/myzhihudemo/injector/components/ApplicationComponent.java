package com.louis.myzhihudemo.injector.components;

import android.content.Context;

import com.louis.myzhihudemo.api.bean.DaoSession;
import com.louis.myzhihudemo.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by louis on 17-4-17.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getContext();

    DaoSession getDaoSession();

}
