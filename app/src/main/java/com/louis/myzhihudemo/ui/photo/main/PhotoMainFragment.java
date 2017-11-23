package com.louis.myzhihudemo.ui.photo.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerPhotoMainComponent;
import com.louis.myzhihudemo.injector.modules.PhotoMainModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.home.HomeActivity;
import com.louis.myzhihudemo.ui.photo.beauty.BeautyListFragment;
import com.louis.myzhihudemo.ui.photo.news.PhotoNewsFragment;
import com.louis.myzhihudemo.ui.photo.welfare.WelfareListFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by louis on 17-11-22.
 * 图片主界面
 */

public class PhotoMainFragment extends BaseFragment<PhotoMainPresent> implements IPhotoMainView {

    @BindView(R.id.tool_bar)
    protected Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    protected TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    protected ViewPager mViewPager;
    @Inject
    ViewPagerAdapter mPagerAdapter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    @Override
    protected int attachLayoutRes() {

        return R.layout.fragment_photo_main;
    }

    @Override
    protected void initInjector() {

        DaggerPhotoMainComponent.builder()
                .applicationComponent(getAppComponent())
                .photoMainModule(new PhotoMainModule(this))
                .build()
                .inject(this);


    }


    @Override
    protected void initViews() {
        initToolbar(mToolbar, true, "图片");
        setHasOptionsMenu(true);//设置menu生效

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).openDrawer();
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        System.out.println("updateViews:" + isRefresh);
        mPresenter.getData(isRefresh);

    }

    @Override
    public void loadData() {  fragments = new ArrayList<>();
        fragments.add(new BeautyListFragment());
        fragments.add(new PhotoNewsFragment());
        fragments.add(new WelfareListFragment());
        titles = new ArrayList<>();
        titles.add("美图");
        titles.add("福利");
        titles.add("生活");


        mPagerAdapter.setItems(fragments, titles);


    }
}
