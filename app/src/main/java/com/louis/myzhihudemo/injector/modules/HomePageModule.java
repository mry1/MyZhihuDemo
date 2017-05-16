package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.adapter.HomeStoryAdapter;
import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.ui.news.newslist_home.HomePageFragment;
import com.louis.myzhihudemo.ui.news.newslist_home.HomePagePresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Louis on 2017/5/16.
 */
@Module
public class HomePageModule {
    HomePageFragment mView;

    public HomePageModule(HomePageFragment view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public HomePagePresent provideHomePagePresent() {
        return new HomePagePresent(mView);
    }

    @PerFragment
    @Provides
    public HomeStoryAdapter provideHomeStoryAdapter() {
        return new HomeStoryAdapter(mView.getContext(), null);
    }
}
