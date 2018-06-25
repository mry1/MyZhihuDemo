package com.louis.myzhihudemo.api;

import android.support.annotation.NonNull;

import com.louis.myzhihudemo.AndroidApplication;
import com.louis.myzhihudemo.adapter.item.BeautyPhotoList;
import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.api.bean.StoryDetail;
import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.api.bean.ThemeInfo;
import com.louis.myzhihudemo.api.bean.VideoInfo;
import com.louis.myzhihudemo.local.table.BeautyPhotoInfo;
import com.louis.myzhihudemo.utils.NetUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Louis on 2017/4/22.
 */

public class RetrofitService {

    private static final String BASE_URL = "http://news-at.zhihu.com/";
    private static final String BEAUTY_HOST = "http://gank.io/";

    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private static final String NEWS_HOST = "http://c.3g.163.com/";
    private static final String WELFARE_HOST = "http://gank.io/";
    // 递增页码
    private static final int INCREASE_PAGE = 20;

    private static INewsApi sNewsService;
    private static RetrofitService mInstance;
    private static IPhotoApi sBeautyService;
    private static IVideoApi sVideoService;

    private RetrofitService() {
    }

    public static RetrofitService getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitService();
        }
        return mInstance;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isNetworkAvailable(AndroidApplication.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtils.isNetworkAvailable(AndroidApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    public static void init() {
        // 指定缓存路径，缓存大小100M
        Cache cache = new Cache(new File(AndroidApplication.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(sLoggingInterceptor)
//                .addInterceptor(sRewriteCacheControlInterceptor)
//                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        sNewsService = retrofit.create(INewsApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BEAUTY_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        sBeautyService = retrofit.create(IPhotoApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(NEWS_HOST)
                .build();
        sVideoService = retrofit.create(IVideoApi.class);
    }

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /**
     * 获取主题列表
     *
     * @return
     */
    public Observable<ThemeInfo> getTheme() {
        return sNewsService.getTheme();
    }

    public Observable<StoryList> getThemeStories(int id) {
        return sNewsService.getThemeStories(id);
    }

    public Observable<HomeStory> getHomeStory() {
        return sNewsService.getHomeStory();
    }

    public Observable<HomeStory> getBeforeHomeStory(long date) {
        String sDate;
        Date d = new Date(date + 24 * 60 * 60 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        sDate = format.format(d);
        return sNewsService.getBeforeHomeStory(sDate);
    }

    public Observable<StoryDetail> getStoryDetail(long id) {
        return sNewsService.getStoryDetail(id);
    }

    public Observable<List<VideoInfo>> getVideoList(final String videoId, int page) {
        return sVideoService.getVideoList(videoId, page * INCREASE_PAGE / 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapVideo(videoId));
    }

    private Func1<Map<String, List<VideoInfo>>, Observable<List<VideoInfo>>> _flatMapVideo(final String videoId) {
        return new Func1<Map<String, List<VideoInfo>>, Observable<List<VideoInfo>>>() {
            @Override
            public Observable<List<VideoInfo>> call(Map<String, List<VideoInfo>> stringListMap) {
                return Observable.just(stringListMap.get(videoId));
            }
        };

    }

    /**
     * 获取美图页面数据
     *
     * @param page
     * @return
     */
    public Observable<BeautyPhotoInfo> getBeautyPhotoList(int page) {
        return sBeautyService.getBeautyPhoto(page)
                .flatMap(flatmapBeautyPhoto())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 类型转换
     *
     * @return
     */
    @NonNull
    private Func1<BeautyPhotoList, Observable<BeautyPhotoInfo>> flatmapBeautyPhoto() {
        return new Func1<BeautyPhotoList, Observable<BeautyPhotoInfo>>() {
            @Override
            public Observable<BeautyPhotoInfo> call(BeautyPhotoList beautyPhotoList) {
                if (beautyPhotoList.getResults() == null) {
                    return Observable.empty();
                }
                return Observable.from(beautyPhotoList.getResults());
            }
        };
    }


}
