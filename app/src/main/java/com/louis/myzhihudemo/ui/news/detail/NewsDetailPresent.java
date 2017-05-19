package com.louis.myzhihudemo.ui.news.detail;

import android.content.res.Configuration;

import com.louis.myzhihudemo.api.RetrofitService;
import com.louis.myzhihudemo.api.bean.StoryDetail;
import com.louis.myzhihudemo.base.BasePresenter;
import com.louis.myzhihudemo.utils.ToastUtils;

import io.reactivex.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/5/18.
 */

public class NewsDetailPresent extends BasePresenter {
    private NewsDetailActivity mView;

    public NewsDetailPresent(NewsDetailActivity view) {
        mView = view;
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

                    }

                    @Override
                    public void onNext(StoryDetail storyDetail) {
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
