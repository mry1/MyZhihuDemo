package com.louis.myzhihudemo.ui.manage.love;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewConfiguration;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.base.BaseActivity;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.base.BaseSwipeBackActivity;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.manage.photo.LovePhotoFragment;
import com.louis.myzhihudemo.ui.manage.video.LoveVideoFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by louis on 17-11-29.
 */

public class LoveActivity extends BaseSwipeBackActivity {
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    public static String INDEX_KEY = "index_key";
    public static final int INDEX_LOVE_PHOTO = 0;
    public static final int INDEX_LOVE_VIDEO = 1;
    private int mIndexKey;
    private ViewPagerAdapter mPagerAdapter;

    @IntDef({INDEX_LOVE_PHOTO, INDEX_LOVE_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Index {
    }

    public static void launch(Context context, @Index int index) {
        Intent intent = new Intent(context, LoveActivity.class);
        intent.putExtra(INDEX_KEY, index);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.zoom_out_entry, R.anim.hold);

    }

    @Override
    protected int getResId() {
        return R.layout.activity_love;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        mIndexKey = getIntent().getIntExtra(INDEX_KEY, 0);
        initToolbar(mToolbar, true, "收藏");
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoveActivity.this.finish();
            }
        });

    }

    @Override
    protected void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LovePhotoFragment());
        fragments.add(new LoveVideoFragment());
        mPagerAdapter.setItems(fragments, Arrays.asList(new String[]{"图片", "视频"}));
        mViewPager.setCurrentItem(mIndexKey);

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.zoom_out_exit);
    }
}
