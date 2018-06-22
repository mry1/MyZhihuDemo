package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.VideoListModule;
import com.louis.myzhihudemo.ui.video.list.VideoListFragment;

import dagger.Component;

@PerFragment
@Component(modules = VideoListModule.class, dependencies = ApplicationComponent.class)
public interface VideoListComponent {
    void inject(VideoListFragment videoListFragment);

}
