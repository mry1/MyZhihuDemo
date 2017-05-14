package com.louis.myzhihudemo.ui.news.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.louis.myzhihudemo.adapter.NewsListAdapter;
import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerNewsListComponent;
import com.louis.myzhihudemo.injector.modules.NewsListModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.RecyclerViewHelper;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Louis on 2017/5/13.
 */

public class NewsListFragment extends BaseFragment<NewsListPresent> implements INewsListView {
    private static final String STORY_TYPE_KEY = "story_type_key";
    private int storyID;
    @BindView(R.id.rv_news_list)
    RecyclerView mRvStoriesList;
    @Inject
    NewsListAdapter mStoriesListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storyID = getArguments().getInt(STORY_TYPE_KEY);

            System.out.println("storyID::" + storyID);
        }
    }

    public static NewsListFragment newInstance(int storyID) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STORY_TYPE_KEY, storyID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initInjector() {
        DaggerNewsListComponent.builder()
                .applicationComponent(getAppComponent())
                .newsListModule(new NewsListModule(this, storyID))
                .build()
                .inject(this);

    }

    @Override
    protected void initViews() {
        RecyclerViewHelper.initRecyclerView(getContext(), mRvStoriesList, true, mStoriesListAdapter);

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);

    }

    @Override
    public void loadData(StoryList stories) {

        // TODO: 2017/5/14 获取完数据，更新adapter

        Logger.d("故事数据：" + stories.toString());
        mStoriesListAdapter.addData(stories.stories);

    }
}
