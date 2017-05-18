package com.louis.myzhihudemo.ui.news.detail;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.louis.myzhihudemo.base.BaseSwipeBackActivity;
import com.louis.myzhihudemo.injector.components.DaggerNewsDetailComponent;
import com.louis.myzhihudemo.injector.modules.NewsDetailModule;
import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.PreferenceUtil;
import com.louis.myzhihudemo.utils.SwipeRefreshHelper;
import com.louis.myzhihudemo.widget.customtabs.CustomFallback;
import com.louis.myzhihudemo.widget.customtabs.CustomTabActivityHelper;

import butterknife.BindView;

/**
 * Created by Louis on 2017/5/18.
 */

public class NewsDetailActivity extends BaseSwipeBackActivity<NewsDetailPresent> {
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private static final String NEWS_ID_KEY = "newsIdKey";
    private long mNewsID;

    @Override
    protected int getResId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initInjector() {
        DaggerNewsDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .newsDetailModule(new NewsDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {
        SwipeRefreshHelper.init(mRefreshLayout, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData(true);
            }
        });

    }

    @Override
    protected void initData() {
        mNewsID = getIntent().getLongExtra(NEWS_ID_KEY, 0);
        mPresenter.getNewsDetail(mNewsID);

        webViewSettings();

    }

    private void webViewSettings() {
        webView.setScrollbarFadingEnabled(true);
        WebSettings webViewSettings = webView.getSettings();
        //能够和js交互
        webViewSettings.setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webViewSettings.setBuiltInZoomControls(false);
        //缓存
        webViewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        webViewSettings.setDomStorageEnabled(true);
        //开启application Cache功能
        webViewSettings.setAppCacheEnabled(false);
        webViewSettings.setBlockNetworkImage(PreferenceUtil.getBoolean(mContext, "no_picture_mode", false));

        //对图片的点击事件用js调用java方法，详见 https://juejin.im/post/58d9aca80ce463005713dba6
        webView.addJavascriptInterface(new JavascriptInterface(mContext), "ImageClickListener");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                openUrl(view, url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这段js函数的功能就是注册监听，遍历所有的img标签，并添加onClick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
                webView.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\"); "
                        + "for(var i=0;i<objs.length;i++)  " + "{"
                        + "    objs[i].onclick=function()  " + "    {  "
                        + "        window.ImageClickListener.onImageClick(this.src);  "
                        + "    }  " + "}" + "})()");
            }
        });
    }

    public void openUrl(WebView webView, String url) {
        if (PreferenceUtil.getBoolean(mContext, "in_app_browser", true)) {
            CustomTabsIntent.Builder customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(getResources().getColor(R.color.colorAccent))
                    .setShowTitle(true);
            CustomTabActivityHelper.openCustomTab(
                    (Activity) mContext,
                    customTabsIntent.build(),
                    Uri.parse(url),
                    new CustomFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            super.openUri(activity, uri);
                        }
                    }
            );
        } else {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
            } catch (ActivityNotFoundException ex) {
                Snackbar.make(imageView, "没有安装浏览器", Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    public static void launch(Context context, long newsID) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NEWS_ID_KEY, newsID);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class JavascriptInterface {
        private Context context;


        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void onImageClick(String img) {
//            UIHelper.showBigImage(mContext, img);
        }
    }
}
