package com.louis.myzhihudemo.ui.news.main;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.api.bean.ThemeInfo;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerNewsMainComponent;
import com.louis.myzhihudemo.injector.modules.NewsMainModule;
import com.louis.myzhihudemo.local.table.NewsTypeInfo;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.home.HomeActivity;
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
    @BindView(R.id.fab)
    FloatingActionButton mFab;

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
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mFab.setVisibility(View.VISIBLE);
                } else {
                    mFab.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // TODO: 2017/5/24 查看MVPAPP实现该功能的方法
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).openDrawer();
            }
        });
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
