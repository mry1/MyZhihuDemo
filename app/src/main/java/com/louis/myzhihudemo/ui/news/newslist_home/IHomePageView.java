package com.louis.myzhihudemo.ui.news.newslist_home;

import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.base.ILoadDataView;

/**
 * Created by Louis on 2017/5/16.
 */

public interface IHomePageView extends ILoadDataView<HomeStory> {
    void loadMoreDataByTag(boolean clear, HomeStory homeStory);
}
