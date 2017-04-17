package com.louis.myzhihudemo;

import android.app.Application;

import com.louis.myzhihudemo.injector.components.ApplicationModule;
import com.louis.myzhihudemo.injector.modules.ApplicationComponent;
import com.louis.myzhihudemo.injector.modules.DaggerAplicationComponent;

/**
 * Created by louis on 17-4-17.
 */

public class AndroidApplication extends Application {

    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initInjector();
    }

    private void initInjector() {
        //不做注入操作，只提供全局单例数据
        mAppComponent = DaggerAplicationComponent.builder().applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}
