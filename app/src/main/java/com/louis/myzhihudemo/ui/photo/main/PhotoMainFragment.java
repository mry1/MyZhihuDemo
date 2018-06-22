package com.louis.myzhihudemo.ui.photo.main;

import android.animation.Animator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerPhotoMainComponent;
import com.louis.myzhihudemo.injector.modules.PhotoMainModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.home.HomeActivity;
import com.louis.myzhihudemo.ui.manage.love.LoveActivity;
import com.louis.myzhihudemo.ui.photo.beauty.BeautyListFragment;
import com.louis.myzhihudemo.ui.photo.news.PhotoNewsFragment;
import com.louis.myzhihudemo.ui.photo.welfare.WelfareListFragment;
import com.louis.myzhihudemo.utils.AnimateHelper;
import com.louis.myzhihudemo.utils.ToastUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.tv_count)
    TextView mTvCount;


    @Inject
    ViewPagerAdapter mPagerAdapter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private Animator mLovedAnimator = null;

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
    public void onResume() {
        super.onResume();
        if (mLovedAnimator == null) {
            mLovedAnimator = AnimateHelper.doHappyJump(mTvCount, mTvCount.getHeight() * 2 / 3, 3000);
        } else {
            AnimateHelper.startAnimator(mLovedAnimator);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        AnimateHelper.stopAnimator(mLovedAnimator);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        System.out.println("updateViews:" + isRefresh);
        mPresenter.getData(isRefresh);

    }

    @Override
    public void loadData() {
        fragments = new ArrayList<>();
        fragments.add(new BeautyListFragment());
        fragments.add(new PhotoNewsFragment());
        fragments.add(new WelfareListFragment());
        titles = new ArrayList<>();
        titles.add("美图");
        titles.add("福利");
        titles.add("生活");


        mPagerAdapter.setItems(fragments, titles);

    }

    @OnClick(R.id.fl_layout)
    public void onClick() {
        ToastUtils.showMessage("love");
        LoveActivity.launch(mContext, LoveActivity.INDEX_LOVE_PHOTO);

    }


}
