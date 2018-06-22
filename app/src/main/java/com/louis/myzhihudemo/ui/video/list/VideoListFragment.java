package com.louis.myzhihudemo.ui.video.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerVideoListComponent;
import com.louis.myzhihudemo.injector.modules.VideoListModule;
import com.louis.myzhihudemo.ui.R;

import butterknife.BindView;

public class VideoListFragment extends BaseFragment<VideoListPresent> {
    private static final String VIDEO_ID_KEY = "VideoIdKey";

    @BindView(R.id.rv_photo_list)
    protected RecyclerView rv_photo_list;
    private String mVideoId;

    public static VideoListFragment newInstance(String VideoId) {
        VideoListFragment videoListFragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_ID_KEY, VideoId);
        videoListFragment.setArguments(bundle);
        return videoListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mVideoId = bundle.getString(VIDEO_ID_KEY);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initInjector() {
        DaggerVideoListComponent.builder()
                .applicationComponent(getAppComponent())
                .videoListModule(new VideoListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
}
