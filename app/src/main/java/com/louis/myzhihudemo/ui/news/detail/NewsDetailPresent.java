package com.louis.myzhihudemo.ui.news.detail;

import android.content.res.Configuration;
import android.text.Html;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.StoryDetail;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.local.table.StoryColumns;
import com.louis.myzhihudemo.local.table.StoryColumnsDao;
import com.louis.myzhihudemo.utils.CopyTextToClipboardUtil;
import com.louis.myzhihudemo.utils.ToastUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/5/18.
 */

public class NewsDetailPresent extends BasePresenter {
    private NewsDetailActivity mView;
    private StoryDetail mStoryDetail;
    private StoryColumnsDao mDao;
    private StoryColumns mStoryColumns;

    public NewsDetailPresent(NewsDetailActivity view, StoryColumnsDao dao) {
        mView = view;
        mDao = dao;
    }

    /**
     * 获取详情
     *
     * @param storyID
     */
    public void getNewsDetail(long storyID) {
        RetrofitService.getInstance().getStoryDetail(storyID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .subscribe(new Subscriber<StoryDetail>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                        mView.finishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast("网络有问题" + e.getMessage());
                        mView.showNetError();
                        mView.finishRefresh();

                    }

                    @Override
                    public void onNext(StoryDetail storyDetail) {
                        mStoryDetail = storyDetail;
                        mStoryColumns = new StoryColumns(mStoryDetail.id, 0);

                        mView.setCollapsingToolbarLayoutTitle(storyDetail.title);
                        mView.loadImage(storyDetail.image);

                        if (storyDetail.body == null) {
                            mView.loadUrl(storyDetail.share_url);
                        } else {
                            mView.loadDataWithBaseURL(convertBody(storyDetail.body));
                        }

                    }
                });

    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }

    public void showBottomSheetDialog() {
        if (mStoryDetail == null) {
            mView.showNetError();
        } else {
            mView.showBottomSheetDialog();
        }

    }

    public boolean ifBookMarked(long storyID) {
        StoryColumns load = mDao.load(storyID);
        return load == null ? false : load.getBOOKMARK() == 1;
    }

    public void unbookmarkStory() {
        mStoryColumns.setBOOKMARK(0);
        mDao.update(mStoryColumns);
    }

    public void bookmarkStory() {
        mStoryColumns.setBOOKMARK(1);

        mDao.update(mStoryColumns);

    }

    public void copyLink() {
        CopyTextToClipboardUtil.copyTextToClipboard(mView, Html.fromHtml(mStoryDetail.share_url));
        mView.showCopySuccess();
    }

    public void openInBrowser() {
        mView.openInBrowser(mStoryDetail.share_url);

    }

    public void copyText() {
        CopyTextToClipboardUtil.copyTextToClipboard(mView, Html.fromHtml(mStoryDetail.body));
        mView.showCopySuccess();
    }

    public void shareAsText() {
        mView.shareAsText(mStoryDetail.title, mStoryDetail.share_url);
    }

    private String convertBody(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // in fact,in api,css addresses are given as an array
        // api中还有js的部分，这里不再解析js
        // javascript is included,but here I don't use it
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        // use the css file from local assets folder,not from network
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        // load content judging by different theme
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((mView.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES) {
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }
}
