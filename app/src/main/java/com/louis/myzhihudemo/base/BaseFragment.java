package com.louis.myzhihudemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louis.myzhihudemo.AndroidApplication;
import com.louis.myzhihudemo.injector.components.ApplicationComponent;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.SwipeRefreshHelper;
import com.louis.myzhihudemo.widget.EmptyLayout;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by louis on 17-4-17.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements IBaseView {
    @Nullable
    @BindView(R.id.empty_layout)
    EmptyLayout mEmptyLayout;
    @Nullable
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @Inject
    protected T mPresenter;
    private Context mContext;
    private View mRootView;
    private boolean mIsMulti = false;

    private String TAG = getClass().getName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            ButterKnife.bind(this, mRootView);
            initInjector();
            initViews();
            initSwipeRefresh();
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
        mIsMulti = true;
        updateViews(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews(false);

        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void showLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_HIDE);
        }
    }

    @Override
    public void showNetError(EmptyLayout.OnRetryListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(onRetryListener);

        }
    }

    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            Logger.w("finishRefresh");
            mSwipeRefresh.setRefreshing(false);

        }

    }

    protected abstract int attachLayoutRes();

    protected abstract void initInjector();

    protected abstract void initViews();


    private void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            SwipeRefreshHelper.init(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateViews(true);
                }
            });
        }
    }

    /**
     * 更新视图控件
     *
     * @param isRefresh 新增参数，用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    protected abstract void updateViews(boolean isRefresh);

    protected ApplicationComponent getAppComponent() {
        AndroidApplication application = (AndroidApplication) getActivity().getApplication();
        return application.getAppComponent();
    }

    protected void initToolbar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.initToolbar(toolbar, homeAsUpEnabled, title);
    }

}
