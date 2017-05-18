package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.ui.news.detail.NewsDetailActivity;
import com.louis.myzhihudemo.ui.news.detail.NewsDetailPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Louis on 2017/5/18.
 */

@Module
public class NewsDetailModule {

    private NewsDetailActivity mView;

    public NewsDetailModule(NewsDetailActivity view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public NewsDetailPresent provideNewsDetailPresent() {
        return new NewsDetailPresent(mView);
    }

}
