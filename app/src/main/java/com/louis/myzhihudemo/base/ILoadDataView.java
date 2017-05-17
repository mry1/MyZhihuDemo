package com.louis.myzhihudemo.base;

/**
 * Created by Louis on 2017/5/17.
 */

public interface ILoadDataView<T> {
    /**
     * 加载数据
     * @param data
     */
    void loadData(T data);

    /**
     * 加载更多
     * @param data
     */
    void loadMoreData(T data);

    /**
     * 没有数据
     */
    void loadNoData();
}
