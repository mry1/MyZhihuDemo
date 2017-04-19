package com.louis.myzhihudemo.ui.news.main;

import com.louis.myzhihudemo.local.table.NewsTypeInfo;

import java.util.List;

/**
 * Created by louis on 17-4-19.
 */

public interface INewsMainView {

    /**
     * 显示数据
     * @param checkList 选中栏目
     */
    void loadData(List<NewsTypeInfo> checkList);

}
