package com.louis.myzhihudemo.ui.news.newslist_home;

import android.util.Log;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.utils.ToastUtils;

import java.util.Calendar;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/5/16.
 */

public class HomePagePresent extends BasePresenter {
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    HomePageFragment mView;
    private String TAG = getClass().getName();

    public HomePagePresent(HomePageFragment view) {
        mView = view;
    }

    @Override
    public void getData(final boolean isRefresh) {
        RetrofitService.getInstance().getHomeStory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .subscribe(new Subscriber<HomeStory>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted" + isRefresh);
                        if (!isRefresh) {
                            mView.hideLoading();
                        } else {
                            mView.finishRefresh();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isRefresh) {
                            mView.finishRefresh();
                            ToastUtils.showMessage("刷新失败");
                        } else {
                            mView.showNetError();
                        }

                    }

                    @Override
                    public void onNext(HomeStory homeStory) {
                        mView.loadData(homeStory);

                    }
                });


    }

    /**
     *
     * @param clear 是否将之前的list清空
     * @param date
     */
    public void getMoreData1(final boolean clear, long date) {
        RetrofitService.getInstance().getBeforeHomeStory(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeStory>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showMessage("加载失败，" + e.getMessage());

                    }

                    @Override
                    public void onNext(HomeStory story) {
//                        System.out.println("story::" + story.toString());
                        if (clear){
                            mView.removeHeaderView();
                        }
                        mView.loadMoreDataByTag(clear, story);
                    }
                });
    }

    @Override
    public void getMoreData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, --mDay);
        RetrofitService.getInstance().getBeforeHomeStory(calendar.getTimeInMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeStory>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showMessage("加载失败，" + e.getMessage());

                    }

                    @Override
                    public void onNext(HomeStory story) {
//                        System.out.println("story::" + story.toString());
                        mView.loadMoreData(story);
                    }
                });


    }
}
