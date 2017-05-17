package com.louis.myzhihudemo.ui.news.newslist_home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.louis.myzhihudemo.adapter.HomeStoryAdapter;
import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerHomePageComponent;
import com.louis.myzhihudemo.injector.modules.HomePageModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.RecyclerViewHelper;
import com.louis.myzhihudemo.utils.SliderLayoutHelper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Louis on 2017/5/16.
 */

public class HomePageFragment extends BaseFragment<HomePagePresent> implements IHomePageView {
    @BindView(R.id.rv_news_list)
    RecyclerView mRvStoriesList;
    @Inject
    HomeStoryAdapter mStoryAdapter;
    private SliderLayout mSlider;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initInjector() {
        DaggerHomePageComponent.builder()
                .applicationComponent(getAppComponent())
                .homePageModule(new HomePageModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void initViews() {
        RecyclerViewHelper.initRecyclerView(getContext(), mRvStoriesList, true, mStoryAdapter);
        // 上拉加载更多
        mStoryAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                System.out.println("请求更多数据");
                mPresenter.getMoreData();

            }
        }, mRvStoriesList);

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSlider != null) {
            mSlider.startAutoCycle();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSlider != null) {
            mSlider.stopAutoCycle();
        }
    }

    @Override
    public void loadData(HomeStory homeStory) {

        mStoryAdapter.setNewData(homeStory.stories);
        mStoryAdapter.removeAllHeaderView();

        // 添加头部轮播图
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.head_news_list, null);
        mSlider = (SliderLayout) headerView.findViewById(R.id.slider_ads);
        SliderLayoutHelper.init(mContext, mSlider, homeStory);
        mStoryAdapter.addHeaderView(headerView);

    }

    @Override
    public void loadMoreData(HomeStory data) {
//        System.out.println("=======");
//        System.out.println(data.stories.toString());
//        System.out.println("=======");
        mStoryAdapter.loadMoreComplete();
        mStoryAdapter.addData(data.stories);

    }

    @Override
    public void loadNoData() {


    }
}
