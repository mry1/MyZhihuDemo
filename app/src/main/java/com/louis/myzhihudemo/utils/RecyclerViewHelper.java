package com.louis.myzhihudemo.utils;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by Louis on 2017/5/14.
 */

public class RecyclerViewHelper {
    private RecyclerViewHelper() {
        throw new RuntimeException("RecyclerViewHelper cannot be initialized");
    }

    /**
     * 配置垂直列表的recyclerView
     *
     * @param context
     * @param view
     * @param isDivided
     * @param adapter
     */
    public static void initRecyclerView(Context context, RecyclerView view, boolean isDivided, BaseQuickAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        //添加recyclerView动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        if (isDivided) {
            view.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
        view.setAdapter(adapter);

    }

    /**
     * 配置瀑布流列表RecyclerView
     *
     * @param context
     * @param view
     * @param isDivided
     * @param adapter
     * @param colum
     */
    public static void initRecyclerViewSV(Context context, RecyclerView view, boolean isDivided, BaseQuickAdapter adapter, int colum) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(colum, StaggeredGridLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        if (isDivided) {
            view.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
        view.setAdapter(adapter);

    }


}
