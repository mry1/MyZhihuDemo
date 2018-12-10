package com.louis.myzhihudemo.api;

import com.louis.myzhihudemo.adapter.item.BeautyPhotoList;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by louis on 17-11-24.
 */

public interface IPhotoApi {
    @Headers("Cache-Control: public, max-age=" + 24 * 3600)
    @GET("/api/data/福利/10/{page}")
    Observable<BeautyPhotoList> getBeautyPhoto(@Path("page") int page);


}
