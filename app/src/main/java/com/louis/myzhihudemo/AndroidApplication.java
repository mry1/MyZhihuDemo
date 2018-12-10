package com.louis.myzhihudemo;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.DaoMaster;
import com.louis.myzhihudemo.api.bean.DaoSession;
import com.louis.myzhihudemo.injector.components.ApplicationComponent;
import com.louis.myzhihudemo.injector.components.DaggerApplicationComponent;
import com.louis.myzhihudemo.injector.modules.ApplicationModule;
import com.louis.myzhihudemo.utils.PreferencesUtils;
import com.louis.myzhihudemo.utils.ToastUtils;
import com.louis.myzhihudemo.utils.imageloader.GlideLoader;
import com.louis.myzhihudemo.utils.imageloader.ImageLoader2;

/**
 * Created by louis on 17-4-17.
 */

public class AndroidApplication extends Application {
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;

    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;


    private ApplicationComponent mAppComponent;
    private DaoSession mDaoSession;
    private static final String DB_NAME = "news-db";
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        initDatabase();
        initInjector();
        initConfig();

        mMainLooper = getMainLooper();
        mMainThreadHandler = new Handler(mMainLooper);
        mMainThreadId = android.os.Process.myTid();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
////        MultiDex.install(this);
    }

    public static Context getContext() {
        return sContext;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
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

    /**
     * 初始化配置
     */
    private void initConfig() {
        ToastUtils.init(getApplicationContext());
        PreferencesUtils.init(sContext);
        RetrofitService.init();
        ImageLoader2.getInstance().setImageLoader(new GlideLoader());

    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}
