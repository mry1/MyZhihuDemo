package com.louis.myzhihudemo.injector.modules;

import com.louis.myzhihudemo.adapter.NewsListAdapter;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.injector.PerFragment;
import com.louis.myzhihudemo.ui.news.newslist.INewsListView;
import com.louis.myzhihudemo.ui.news.newslist.NewsListFragment;
import com.louis.myzhihudemo.ui.news.newslist.NewsListPresent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Louis on 2017/5/13.
 */

@Module
public class NewsListModule {
    private NewsListFragment mView;
    private int mStoryID;

    public NewsListModule(NewsListFragment view, int storyID) {
        mView = view;
        mStoryID = storyID;
    }


    @PerFragment
    @Provides
    public NewsListPresent provideNewsListPresent() {
        return new NewsListPresent(mView, mStoryID);
    }

    @PerFragment
    @Provides
    public NewsListAdapter provideNewsListAdapter() {
        return new NewsListAdapter(mView.getContext(), null);
    }

}
