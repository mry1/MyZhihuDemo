package com.louis.myzhihudemo.ui.video.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerVideoMainComponent;
import com.louis.myzhihudemo.injector.modules.VideoMainModule;
import com.louis.myzhihudemo.rxbus.RxBusSubscriber;
import com.louis.myzhihudemo.rxbus.event.VideoEvent;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.home.HomeActivity;
import com.louis.myzhihudemo.ui.video.list.VideoListFragment;
import com.louis.myzhihudemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by louis on 17-11-22.
 * 视频主界面
 */

public class VideoMainFragment extends BaseFragment<VideoMainPresent> {

    @BindView(R.id.tb_video_main)
    protected Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    protected TabLayout tab_layout;
    @BindView(R.id.vp_video_content)
    protected ViewPager vp_video_content;
    @Inject
    protected ViewPagerAdapter mPagerAdapter;

    private final String[] VIDEO_ID = new String[]{
            "V9LG4B3A0", "V9LG4E6VR", "V9LG4CHOR", "00850FRB"
    };
    private final String[] VIDEO_TITLE = new String[]{
            "热点", "搞笑", "娱乐", "精品"
    };

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_main;
    }

    @Override
    protected void initInjector() {

        DaggerVideoMainComponent.builder()
                .applicationComponent(getAppComponent())
                .videoMainModule(new VideoMainModule(this))
                .build()
                .inject(this);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_video, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            ((HomeActivity) getActivity()).openDrawer();
            return true;
        } else if (itemId == R.id.action_download) {
            ToastUtils.showMessage("下载");
        } else if (itemId == R.id.action_collect) {
            ToastUtils.showMessage("收藏");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        initToolbar(mToolbar, true, "视频");
        setHasOptionsMenu(true);//设置menu生效
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < VIDEO_TITLE.length; i++) {
            fragments.add(VideoListFragment.newInstance(VIDEO_ID[i]));
        }
        mPagerAdapter.setItems(fragments, Arrays.asList(VIDEO_TITLE));
        vp_video_content.setAdapter(mPagerAdapter);
        tab_layout.setupWithViewPager(vp_video_content);

    }

    @Override
    protected void registerRxBus() {
        addSubscription(VideoEvent.class, new RxBusSubscriber<VideoEvent>() {
            @Override
            protected void onEvent(VideoEvent videoEvent) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        System.out.println("updateViews:" + isRefresh);
        mPresenter.getData(isRefresh);

    }


}
