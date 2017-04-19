package com.louis.myzhihudemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.louis.myzhihudemo.injector.components.ApplicationComponent;
import com.louis.myzhihudemo.injector.components.DaggerApplicationComponent;
import com.louis.myzhihudemo.injector.modules.ApplicationModule;
import com.louis.myzhihudemo.local.table.DaoMaster;
import com.louis.myzhihudemo.local.table.DaoSession;

/**
 * Created by louis on 17-4-17.
 */

public class AndroidApplication extends Application {

    private ApplicationComponent mAppComponent;
    private DaoSession mDaoSession;
    private static final String DB_NAME = "news-db";

    @Override
    public void onCreate() {
        super.onCreate();

        initDatabase();
        initInjector();
    }

    /**
     * 初始化greenDao数据库
     */
    private void initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), DB_NAME);
        SQLiteDatabase database = helper.getWritableDatabase();
        mDaoSession = new DaoMaster(database).newSession();

    }

    private void initInjector() {
        //不做注入操作，只提供全局单例数据
        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this, mDaoSession))
                .build();

    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}
