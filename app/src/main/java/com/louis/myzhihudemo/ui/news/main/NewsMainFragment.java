package com.louis.myzhihudemo.ui.news.main;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.api.bean.ThemeInfo;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerNewsMainComponent;
import com.louis.myzhihudemo.injector.modules.NewsMainModule;
import com.louis.myzhihudemo.local.table.NewsTypeInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.news.newslist.NewsListFragment;
import com.louis.myzhihudemo.ui.news.newslist_home.HomePageFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by louis on 17-4-17.
 */

public class NewsMainFragment extends BaseFragment<NewsMainPresent> implements INewsMainView {
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
        initToolbar(mToolbar, true, "新闻");
        setHasOptionsMenu(true);//让onCreateOptionsMenu生效
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);

    }

    @Override
    public void loadData(List<ThemeInfo.ThemeBean> checkList) {
        // 加载数据
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        fragments.add(0, new HomePageFragment());
        titles.add(0, "首页");
        for (ThemeInfo.ThemeBean themeBean : checkList) {
            titles.add(themeBean.name);
            fragments.add(NewsListFragment.newInstance(themeBean.id));
        }
        mPagerAdapter.setItems(fragments, titles);

    }
}
