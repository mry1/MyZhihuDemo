package com.louis.myzhihudemo.api;

import com.louis.myzhihudemo.api.bean.StoryList;
import com.louis.myzhihudemo.api.bean.ThemeInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Louis on 2017/4/22.
 */

public interface INewsApi {

    @GET("api/4/themes")
    Observable<ThemeInfo> getTheme();

    @GET("api/4/theme/{id}")
    Observable<StoryList> getThemeStories(@Path("id") int id);

}
