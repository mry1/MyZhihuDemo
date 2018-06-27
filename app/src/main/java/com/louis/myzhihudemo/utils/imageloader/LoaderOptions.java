package com.louis.myzhihudemo.utils.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.request.RequestListener;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by JohnsonFan on 2017/7/13.
 * 该类为图片加载框架的通用属性封装，不能耦合任何一方的框架
 */
public class LoaderOptions {
    public Context context;
    public Activity activity;
    public Fragment fragment;
    public int placeholderResId;
    public int errorResId;
    public boolean isCenterCrop;
    public boolean isFitCenter;
    public boolean isCenterInside;
    @DiskCacheStrategy
    public int diskCacheStrategy;
    public boolean skipLocalCache; //设置跳过内存缓存
    public boolean skipNetCache;
    public boolean dontAnimate;
    public RequestListener listener;
    public Bitmap.Config config = Bitmap.Config.RGB_565;
    public int targetWidth;
    public int targetHeight;
    public float bitmapAngle; //圆角角度
    public float degrees; //旋转角度.注意:picasso针对三星等本地图片，默认旋转回0度，即正常位置。此时不需要自己rotate
    public Drawable placeholder;
    public View targetView;//targetView展示图片
    public BitmapCallBack callBack;
    public String url;
    public File file;
    public int drawableResId;
    public Uri uri;

    @IntDef({DiskCacheStrategy.NONE, DiskCacheStrategy.ALL, DiskCacheStrategy.SOURCE, DiskCacheStrategy.RESULT})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DiskCacheStrategy {
        int NONE = 0;
        int ALL = 1;
        int SOURCE = 2;
        int RESULT = 3;
        // DiskCacheStrategy.NONE：不缓存任何图片，即禁用磁盘缓存
        // DiskCacheStrategy.ALL ：缓存原始图片 & 转换后的图片（默认）
        // DiskCacheStrategy.SOURCE：只缓存原始图片（原来的全分辨率的图像，即不缓存转换后的图片）
        // DiskCacheStrategy.RESULT：只缓存转换后的图片（即最终的图像：降低分辨率后 / 或者转换后 ，不缓存原始图片
    }

    public LoaderOptions(Context context) {
        this.context = context;
    }

    public LoaderOptions(Activity activity) {
        this.activity = activity;
    }

    public LoaderOptions(Fragment fragment) {
        this.fragment = fragment;
    }

    public LoaderOptions(String url) {
        this.url = url;
    }

    public LoaderOptions(File file) {
        this.file = file;
    }

    public LoaderOptions(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public LoaderOptions(Uri uri) {
        this.uri = uri;
    }

    public void into(View targetView) {
        this.targetView = targetView;
        ImageLoader2.getInstance().loadOptions(this);
    }

    public void bitmap(BitmapCallBack callBack) {
        this.callBack = callBack;
        ImageLoader2.getInstance().loadOptions(this);
    }

//    public LoaderOptions with(Context context) {
//        this.context = context;
//        return this;
//    }
//
//    public LoaderOptions with(Activity activity) {
//        this.activity = activity;
//        return this;
//    }
//
//    public LoaderOptions with(Fragment fragment) {
//        this.fragment = fragment;
//        return this;
//    }

    public LoaderOptions load(String path) {
        this.url = path;
        return this;
    }

    public LoaderOptions load(int drawable) {
        this.drawableResId = drawable;
        return this;
    }

    public LoaderOptions load(File file) {
        this.file = file;
        return this;
    }

    public LoaderOptions load(Uri uri) {
        this.uri = uri;
        return this;
    }

    public LoaderOptions placeholder(@DrawableRes int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }

    public LoaderOptions placeholder(Drawable placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public LoaderOptions error(@DrawableRes int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public LoaderOptions centerCrop() {
        isCenterCrop = true;
        return this;
    }

    public LoaderOptions isFitCenter() {
        isFitCenter = isFitCenter;
        return this;
    }

    public LoaderOptions centerInside() {
        isCenterInside = true;
        return this;
    }

    public LoaderOptions config(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    public LoaderOptions resize(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    /**
     * 圆角
     *
     * @param bitmapAngle 度数
     * @return
     */
    public LoaderOptions angle(float bitmapAngle) {
        this.bitmapAngle = bitmapAngle;
        return this;
    }

    public LoaderOptions skipLocalCache(boolean skipLocalCache) {
        this.skipLocalCache = skipLocalCache;
        return this;
    }

    public LoaderOptions skipNetCache(boolean skipNetCache) {
        this.skipNetCache = skipNetCache;
        return this;
    }

    public LoaderOptions rotate(float degrees) {
        this.degrees = degrees;
        return this;
    }

    public LoaderOptions diskCacheStrategy(@DiskCacheStrategy int diskCacheStrategy) {
        this.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    public LoaderOptions listener(RequestListener listener) {
        this.listener = listener;
        return this;
    }

    public LoaderOptions dontAnimate(boolean dontAnimate) {
        this.dontAnimate = dontAnimate;
        return this;
    }

}