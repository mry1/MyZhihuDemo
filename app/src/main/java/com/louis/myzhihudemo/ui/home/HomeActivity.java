package com.louis.myzhihudemo.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.louis.myzhihudemo.base.BaseActivity;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.news.main.NewsMainFragment;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private SparseArray<String> mSparseTags = new SparseArray<>();

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case R.id.nav_news:
                    replaceFragment(R.id.fl_container, new NewsMainFragment(), mSparseTags.get(R.id.nav_news));

                    break;
                case R.id.nav_photos:

                    break;
                case R.id.nav_videos:

                    break;
                case R.id.nav_settings:

                    break;

            }


            return true;
        }
    });


    private int mItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
    protected int getResId() {
        return R.layout.activity_main;
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
}
