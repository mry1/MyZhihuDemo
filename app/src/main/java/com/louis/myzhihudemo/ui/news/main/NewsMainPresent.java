package com.louis.myzhihudemo.ui.news.main;

import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.local.table.NewsTypeInfo;
import com.louis.myzhihudemo.local.table.NewsTypeInfoDao;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
        mDao.queryBuilder().rx().list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<NewsTypeInfo>>() {
                    @Override
                    public void call(List<NewsTypeInfo> newsTypeInfos) {
                        System.out.println(newsTypeInfos.toString());
                        mView.loadData(newsTypeInfos);
                    }
                });

    }

    @Override
    public void getMoreData() {

    }
}
