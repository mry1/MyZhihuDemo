package com.louis.myzhihudemo.ui.news.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerNewsMainComponent;
import com.louis.myzhihudemo.injector.modules.NewsMainModule;
import com.louis.myzhihudemo.local.table.NewsTypeInfo;
import com.louis.myzhihudemo.ui.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by louis on 17-4-17.
 */

public class NewsMainFragment extends BaseFragment implements INewsMainView {
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @Inject
    ViewPagerAdapter mPagerAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_main;
    }

    @Override
    protected void initInjector() {
        DaggerNewsMainComponent.builder()
                .applicationComponent(getAppComponent())
                .newsMainModule(new NewsMainModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void initViews() {
        initToolbar(mToolbar,true, "新闻");
        setHasOptionsMenu(true);//让onCreateOptionsMenu生效
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);

    }

    @Override
    public void loadData(List<NewsTypeInfo> checkList) {

    }
}
