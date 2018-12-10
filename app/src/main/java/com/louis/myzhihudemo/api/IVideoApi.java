package com.louis.myzhihudemo.api;

import com.louis.myzhihudemo.api.bean.VideoInfo;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

import static com.louis.myzhihudemo.api.RetrofitService.AVOID_HTTP403_FORBIDDEN;

public interface IVideoApi {

    @Headers("Cache-Control: public, max-age=" + 24 * 3600 + "\r\n" + AVOID_HTTP403_FORBIDDEN)
    @GET("nc/video/list/{id}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoInfo>>> getVideoList(@Path("id") String id, @Path("startPage") int startPage);

}
