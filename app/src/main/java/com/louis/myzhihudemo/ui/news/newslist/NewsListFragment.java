package com.louis.myzhihudemo.ui.news.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.louis.myzhihudemo.adapter.NewsListAdapter;
import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerNewsListComponent;
import com.louis.myzhihudemo.injector.modules.NewsListModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.news.detail.NewsDetailActivity;
import com.louis.myzhihudemo.utils.RecyclerViewHelper;
import com.louis.myzhihudemo.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Louis on 2017/5/13.
 */

public class NewsListFragment extends BaseFragment<NewsListPresent> implements INewsListView, BaseQuickAdapter.OnItemClickListener {
    private static final String STORY_TYPE_KEY = "story_type_key";
    private int storyID;
    @BindView(R.id.rv_news_list)
    RecyclerView mRvStoriesList;
    @Inject
    NewsListAdapter mStoriesListAdapter;
    ImageView mThemeThumbnail;
    private View mView;

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
        mStoriesListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getMoreData();

            }
        }, mRvStoriesList);
        mStoriesListAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);

    }


    @Override
    public void loadData(StoryList stories) {
        Logger.d("故事数据：" + stories.toString());
        mStoriesListAdapter.setNewData(stories.stories);
        mStoriesListAdapter.removeAllHeaderView();
        if (stories.background != null) {
            mView = LayoutInflater.from(getContext()).inflate(R.layout.item_header_story, null, false);
            mThemeThumbnail = (ImageView) mView.findViewById(R.id.iv_theme_thumbnail);
            Picasso.with(getContext()).load(stories.background).into(mThemeThumbnail);
            mStoriesListAdapter.addHeaderView(mView);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<StoryList.Story> data = adapter.getData();
        NewsDetailActivity.launch(mContext, data.get(position).id);


    }
}
