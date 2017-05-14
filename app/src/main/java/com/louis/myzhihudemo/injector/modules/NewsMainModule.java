package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.adapter.ViewPagerAdapter;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.local.table.DaoSession;
import com.louis.myzhihudemo.ui.news.main.NewsMainFragment;
import com.louis.myzhihudemo.ui.news.main.NewsMainPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by louis on 17-4-19.
 */

@Module
public class NewsMainModule {
    NewsMainFragment mView;

    public NewsMainModule(NewsMainFragment view) {
        mView = view;
    }

    @PerFragment
    @Provides
    public NewsMainPresent provideNewsMainPresent(DaoSession daoSession) {
        return new NewsMainPresent(mView, daoSession.getNewsTypeInfoDao());
    }

    @PerFragment
    @Provides
    public ViewPagerAdapter provideViewPagerAdapter() {
        return new ViewPagerAdapter(mView.getChildFragmentManager());
    }

}
