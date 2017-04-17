package com.louis.myzhihudemo.base;

import android.content.Context;

/**
 * Created by louis on 17-4-11.
 */

public abstract class BasePresenter {
    protected Context mContext;


    protected void setContext(Context context) {
        mContext = context;
    }

/**
 * 获取网络数据，更新界面
 */
    /**
     * 获取网络数据，更新界面
     *
     * @param isRefresh 新增参数，用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    protected abstract void getData(boolean isRefresh);

    /**
     * 加载更多数据
     */
    protected abstract void getMoreData();
}
