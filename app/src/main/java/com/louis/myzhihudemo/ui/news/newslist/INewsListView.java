package com.louis.myzhihudemo.ui.news.newslist;

import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.base.IBaseView;

import java.util.List;

/**
 * Created by Louis on 2017/5/13.
 */

public interface INewsListView extends IBaseView {

    void loadData(StoryList stories);

}
