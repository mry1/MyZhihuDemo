package com.louis.myzhihudemo.ui.news.detail;

import com.louis.myzhihudemo.api.bean.StoryDetail;

/**
 * Created by Louis on 2017/5/18.
 */

public interface INewsDetailView {
    void loadData(StoryDetail storyDetail);

    /**
     * 设置可折叠ToolBar的标题
     *
     * @param title
     */
    void setCollapsingToolbarLayoutTitle(String title);

    void loadImage(String url);

    void loadUrl(String url);

    void loadDataWithBaseURL(String url);

    void showCopySuccess();

    void openInBrowser(String url);

    void shareAsText(String title, String url);
}
