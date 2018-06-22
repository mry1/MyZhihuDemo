package com.louis.myzhihudemo.ui.news.newslist;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/5/13.
 */

public class NewsListPresent extends BasePresenter {

    private NewsListFragment mView;
    private int mStroryID;

    public NewsListPresent(NewsListFragment view, int storyID) {
        mView = view;
        mStroryID = storyID;

    }

    /**
     *
     * @param isRefresh 新增参数，用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    @Override
    public void getData(final boolean isRefresh) {

        RetrofitService.getInstance().getThemeStories(mStroryID)
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
                .subscribe(new Subscriber<StoryList>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted", isRefresh);
                        if (isRefresh) {
                            mView.finishRefresh();
                        } else {
                            mView.hideLoading();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage() + isRefresh);
                        if (isRefresh){
                            mView.finishRefresh();
                            ToastUtils.showMessage("刷新失败提示什么根据实际情况");

                        }else{
                            mView.showNetError();
                        }

                    }

                    @Override
                    public void onNext(StoryList storyList) {
                        System.out.println("storyList:::" +storyList.toString());
                        mView.loadData(storyList);

                    }
                });


    }

    @Override
    public void getMoreData() {


    }
}
