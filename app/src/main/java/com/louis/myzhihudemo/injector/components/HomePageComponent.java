package com.louis.myzhihudemo.injector.components;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.injector.modules.HomePageModule;
import com.louis.myzhihudemo.ui.news.newslist_home.HomePageFragment;

import dagger.Component;

/**
 * Created by Louis on 2017/5/16.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = HomePageModule.class)
public interface HomePageComponent {
    void inject(HomePageFragment homePageFragment);
}
