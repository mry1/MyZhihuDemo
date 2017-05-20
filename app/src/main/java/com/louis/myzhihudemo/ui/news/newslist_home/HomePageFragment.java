package com.louis.myzhihudemo.ui.news.newslist_home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.louis.myzhihudemo.adapter.HomeStoryAdapter;
import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.base.BaseFragment;
import com.louis.myzhihudemo.injector.components.DaggerHomePageComponent;
import com.louis.myzhihudemo.injector.modules.HomePageModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.ui.news.detail.NewsDetailActivity;
import com.louis.myzhihudemo.utils.RecyclerViewHelper;
import com.louis.myzhihudemo.utils.SliderLayoutHelper;
import com.louis.myzhihudemo.utils.ToastUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Louis on 2017/5/16.
 */

public class HomePageFragment extends BaseFragment<HomePagePresent> implements IHomePageView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rv_news_list)
    RecyclerView mRvStoriesList;
    @Inject
    HomeStoryAdapter mStoryAdapter;
    private SliderLayout mSlider;
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

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
        mFab.setRippleColor(getResources().getColor(R.color.colorPrimary));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickDateDialog();
            }
        });
        RecyclerViewHelper.initRecyclerView(getContext(), mRvStoriesList, true, mStoryAdapter);
        // 上拉加载更多
        mStoryAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Calendar c = Calendar.getInstance();
                c.set(mYear, mMonth, mDay);
                mPresenter.getMoreData1(false, c.getTimeInMillis());
//                mPresenter.getMoreData();

            }
        }, mRvStoriesList);

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);

    }

    @Override
    public void loadData(HomeStory homeStory) {

        mStoryAdapter.setNewData(homeStory.stories);
        mStoryAdapter.removeAllHeaderView();

        // 添加头部轮播图
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.head_news_list, null);
        mSlider = (SliderLayout) headerView.findViewById(R.id.slider_ads);
        SliderLayoutHelper.init(mContext, mSlider, homeStory, new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Bundle bundle = slider.getBundle();
                NewsDetailActivity.launch(mContext, bundle.getLong(SliderLayoutHelper.NEWS_ID_KEY));
            }
        });
        mStoryAdapter.addHeaderView(headerView);

        // 点击进入故事详情页
        mStoryAdapter.setOnItemClickListener(this);
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
    public void loadMoreData(HomeStory data) {
        mStoryAdapter.loadMoreComplete();
        mStoryAdapter.addData(data.stories);

    }

    @Override
    public void loadMoreDataByTag(boolean clear, HomeStory data) {
        if (clear) {
            mStoryAdapter.setNewData(data.stories);

        } else {
            mStoryAdapter.loadMoreComplete();
            mStoryAdapter.addData(data.stories);
        }
    }

    @Override
    public void loadNoData() {


    }


    /**
     * 点击进入故事详情页
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<HomeStory.HomeStories> data = adapter.getData();
        ToastUtils.showToast("点击了新闻：" + data.get(position).id);
        NewsDetailActivity.launch(mContext, data.get(position).id);

    }

    /**
     * 显示选择日期dialog
     */
    private void showPickDateDialog() {
        Calendar now = Calendar.getInstance();
        now.set(mYear, mMonth, mDay);
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                Calendar temp = Calendar.getInstance();
                temp.clear();
                temp.set(mYear, mMonth, mDay);
                mPresenter.getMoreData1(true, temp.getTimeInMillis());

            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        // 2013.5.20是知乎日报api首次上线
        minDate.set(2013, 5, 20);
        dialog.setMinDate(minDate);
        dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

    }

    public void removeHeaderView() {
        mStoryAdapter.removeAllHeaderView();
    }
}
