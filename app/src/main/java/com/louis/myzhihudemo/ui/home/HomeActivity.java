package com.louis.myzhihudemo.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.base.BaseActivity;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.manage.video.LoveVideoFragment;
import com.louis.myzhihudemo.ui.news.main.NewsMainFragment;
import com.louis.myzhihudemo.ui.photo.main.PhotoMainFragment;
import com.louis.myzhihudemo.ui.video.main.VideoMainFragment;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private SparseArray<String> mSparseTags = new SparseArray<>();

    private int mItemId;
    private long mExitTime = 0;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case R.id.nav_news:
                    // 点击“新闻”按钮
                    replaceFragment(R.id.fl_container, new NewsMainFragment(), mSparseTags.get(R.id.nav_news));

                    break;
                case R.id.nav_photos:
                    // 点击“图片”按钮
                    replaceFragment(R.id.fl_container, new PhotoMainFragment(), mSparseTags.get(R.id.nav_photos));

                    break;
                case R.id.nav_videos:
                    replaceFragment(R.id.fl_container, new VideoMainFragment(), mSparseTags.get(R.id.nav_videos));

                    break;
                case R.id.nav_settings:

                    break;
            }
            mItemId = -1;

            return true;
        }
    });


    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        initDrawerLayout(mDrawerLayout, mNavView);

    }

    @Override
    protected void initData() {
        mSparseTags.put(R.id.nav_news, "News");
        mSparseTags.put(R.id.nav_photos, "Photos");
        mSparseTags.put(R.id.nav_videos, "Videos");

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mNavView.setCheckedItem(R.id.nav_news);
        replaceFragment(R.id.fl_container, new NewsMainFragment(), mSparseTags.get(R.id.nav_news));

    }

    private void initDrawerLayout(DrawerLayout drawerLayout, NavigationView navView) {
        //将侧边栏顶部延伸至状态栏
        drawerLayout.setFitsSystemWindows(true);
        //将主页面延伸至状态栏
        drawerLayout.setClipToPadding(false);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                mHandler.sendEmptyMessage(mItemId);
            }
        });
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (item.isChecked()) {
            return true;
        }
        mItemId = item.getItemId();
        return true;
    }

    @Override
    public void onBackPressed() {
        // 获取堆栈里有几个
        final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (stackEntryCount == 1) {
            // 如果剩一个说明在主页，提示按两次退出app
            _exit();
        } else {
            // 获取上一个堆栈中保存的是哪个页面，根据name来设置导航项的选中状态
            final String tagName = getSupportFragmentManager().getBackStackEntryAt(stackEntryCount - 2).getName();
            int index = mSparseTags.indexOfValue(tagName);
            mNavView.setCheckedItem(mSparseTags.keyAt(index));
            super.onBackPressed();
        }
    }

    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }
}
