package com.louis.myzhihudemo.utils;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by louis on 17-4-17.
 * 下拉刷新帮助类
 */

public class SwipeRefreshHelper {

    private SwipeRefreshHelper() {

    }

    public static void init(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener refreshListener) {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(refreshListener);
    }
}
