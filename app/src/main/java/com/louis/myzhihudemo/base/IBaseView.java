package com.louis.myzhihudemo.base;

import com.louis.myzhihudemo.widget.EmptyLayout;

/**
 * Created by louis on 17-4-11.
 */

public interface IBaseView {

    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示网络错误
     *
     * @param onRetryListener 点击监听
     */
    void showNetError(EmptyLayout.OnRetryListener onRetryListener);

    /**
     * 完成刷新, 新增控制刷新
     */
    void finishRefresh();
}
