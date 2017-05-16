package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.NewsListModule;
import com.louis.myzhihudemo.ui.news.newslist.NewsListFragment;

import dagger.Component;

/**
 * Created by Louis on 2017/5/13.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = NewsListModule.class)
public interface NewsListComponent {
    void inject(NewsListFragment newsListFragment);
}
