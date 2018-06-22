package com.louis.myzhihudemo.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.louis.myzhihudemo.AndroidApplication;
import com.louis.myzhihudemo.injector.components.ApplicationComponent;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.rxbus.RxBus;
import com.louis.myzhihudemo.widget.EmptyLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by louis on 17-4-11.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView, EmptyLayout.OnRetryListener {
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


    protected abstract int getResId();

    /**
     * Dagger注入
     */
    protected abstract void initInjector();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    protected abstract void updateViews(boolean isRefresh);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
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
    public void showNetError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(this);
        }
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    protected ApplicationComponent getAppComponent() {
        AndroidApplication application = (AndroidApplication) getApplication();
        return application.getAppComponent();
    }

    //管理 rxjava subscriber引用，防止内存泄漏 每一个activity都会有一个
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    public void addSubscription(Subscription subscription) {
        if (subscription == null)
            return;
        mSubscriptions.add(subscription);
    }

    //注册 subscriber
    public <M> void addSubscription(Class<M> clazz, Subscriber<M> subscriber) {
        addSubscription(RxBus.getDefault().toObservable(clazz).subscribe(subscriber));
    }

    /**
     * 初始化Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolbar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);

    }

    /**
     * 替换Fragment
     *
     * @param containerViewId
     * @param fragment
     * @param tag
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            //这里设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();

        } else {
            // 存在则弹出他上面所有的fragment，并显示对应fragment
            getSupportFragmentManager().popBackStack(tag, 0);
        }


    }


}
