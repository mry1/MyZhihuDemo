package com.louis.myzhihudemo.api;

import com.louis.myzhihudemo.api.bean.HomeStory;
import com.louis.myzhihudemo.api.bean.StoryDetail;
import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.api.bean.ThemeInfo;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Louis on 2017/4/22.
 */

public interface INewsApi {

    /**
     * 获取主题
     *
     * @return
     */
    @GET("api/4/themes")
    Observable<ThemeInfo> getTheme();

    /**
     * 获取每个主题的数据
     *
     * @param id
     * @return
     */
    @GET("api/4/theme/{id}")
    Observable<StoryList> getThemeStories(@Path("id") int id);

    /**
     * 获取首页数据
     *
     * @return
     */
    @Headers("Cache-Control: public, max-age=" + 24 * 3600)
    @GET("api/4/news/latest")
    Observable<HomeStory> getHomeStory();

    /**
     * 获取之前的首页数据
     *
     * @param date
     * @return
     */
    @GET("api/4/news/before/{date}")
    Observable<HomeStory> getBeforeHomeStory(@Path("date") String date);

    /**
     * 获取故事详情
     *
     * @param id
     * @return
     */
    @GET("api/4/news/{id}")
    Observable<StoryDetail> getStoryDetail(@Path("id") long id);
}
