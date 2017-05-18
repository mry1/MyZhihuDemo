package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.NewsDetailModule;
import com.louis.myzhihudemo.ui.news.detail.NewsDetailActivity;

import dagger.Component;

/**
 * Created by Louis on 2017/5/18.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = NewsDetailModule.class)
public interface NewsDetailComponent {
    void inject(NewsDetailActivity newsDetailActivity);
}
