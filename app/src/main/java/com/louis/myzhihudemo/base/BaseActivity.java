package com.louis.myzhihudemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.widget.EmptyLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by louis on 17-4-11.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;

    @Nullable
    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefresh;

    @Inject
    protected T mPresenter;
    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        mContext = this;

//        mPresenter = TUtil.getT(this, 0);
        mUnbinder = ButterKnife.bind(this);
        initInjector();
        initView();
        initData();
        updateViews(false);


    }



    /**
     * Dagger注入
     */
    protected abstract void initInjector();

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    protected abstract void updateViews(boolean isRefresh);

    protected abstract void initData();

    protected abstract int getResId();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    @Override
    public void showLoading() {
        if (mEmptyLayout != null){
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null){
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_HIDE);
        }
    }

    @Override
    public void showNetError(EmptyLayout.OnRetryListener onRetryListener) {
        if (mEmptyLayout != null){
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setOnRetryListener(onRetryListener);
        }
    }

    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null){
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
