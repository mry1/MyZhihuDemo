package com.louis.myzhihudemo.ui.news.main;

import android.util.Log;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.ThemeInfo;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.local.table.NewsTypeInfo;
import com.louis.myzhihudemo.local.table.NewsTypeInfoDao;

import java.util.List;

import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louis on 17-4-19.
 */

public class NewsMainPresent extends BasePresenter {
    private final INewsMainView mView;
    private final NewsTypeInfoDao mDao;

    public NewsMainPresent(INewsMainView view, NewsTypeInfoDao dao) {
        mView = view;
        mDao = dao;
    }


    @Override
    public void getData(boolean isRefresh) {
//        mDao.queryBuilder().rx().list()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<NewsTypeInfo>>() {
//                    @Override
//                    public void call(List<NewsTypeInfo> newsTypeInfos) {
//                        System.out.println(newsTypeInfos.toString());
//                        mView.loadData(newsTypeInfos);
//                    }
//                });
        RetrofitService.getInstance().getTheme()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ThemeInfo, List<ThemeInfo.ThemeBean>>() {
                    @Override
                    public List<ThemeInfo.ThemeBean> call(ThemeInfo themeInfo) {
                        return themeInfo.others;
                    }
                })
                .subscribe(new Action1<List<ThemeInfo.ThemeBean>>() {
                    @Override
                    public void call(List<ThemeInfo.ThemeBean> themeBeen) {
                        System.out.println("themeBean...." + themeBeen.toString());
                        mView.loadData(themeBeen);
                    }
                });

    }

    @Override
    public void getMoreData() {

    }
}
