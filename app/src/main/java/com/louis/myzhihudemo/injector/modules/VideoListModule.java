package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.adapter.VideoListAdapter;
import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.local.table.DaoSession;
import com.louis.myzhihudemo.ui.video.list.VideoListFragment;
import com.louis.myzhihudemo.ui.video.list.VideoListPresent;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoListModule {
    private VideoListFragment mView;
    private String videoId;

    public VideoListModule(VideoListFragment view, String videoId) {
        mView = view;
        this.videoId = videoId;
    }

    @PerFragment
    @Provides
    public VideoListPresent provideVideoListPresent(DaoSession daoSession) {
        return new VideoListPresent(mView, daoSession, videoId);
    }

    @PerFragment
    @Provides
    public VideoListAdapter provideVideoListAdapter() {
        return new VideoListAdapter(mView.getContext(), null);
    }

}
