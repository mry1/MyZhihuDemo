package com.louis.myzhihudemo.utils.imageloader;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * 图片加载类
 * 策略或者静态代理模式，开发者只需要关心ImageLoader + LoaderOptions
 * Created by JohnsonFan on 2018/3/8.
 */

public class ImageLoader2 {
    private static ILoaderStrategy sLoader;
    private static volatile ImageLoader2 sInstance;

    private ImageLoader2() {
    }

    //单例模式
    public static ImageLoader2 getInstance() {
        if (sInstance == null) {
            synchronized (ImageLoader2.class) {
                if (sInstance == null) {
                    //若切换其它图片加载框架，可以实现一键替换
                    sInstance = new ImageLoader2();
                }
            }
        }
        return sInstance;
    }

    //提供实时替换图片加载框架的接口
    public void setImageLoader(ILoaderStrategy loader) {
        if (loader != null) {
            sLoader = loader;
        }
    }

    public LoaderOptions with(Context context) {
        return new LoaderOptions(context);
    }

    public LoaderOptions with(Activity activity) {
        return new LoaderOptions(activity);
    }

    public LoaderOptions with(Fragment fragment) {
        return new LoaderOptions(fragment);
    }

//    public LoaderOptions load(String path) {
//        return new LoaderOptions(path);
//    }
//
//    public LoaderOptions load(int drawable) {
//        return new LoaderOptions(drawable);
//    }
//
//    public LoaderOptions load(File file) {
//        return new LoaderOptions(file);
//    }
//
//    public LoaderOptions load(Uri uri) {
//        return new LoaderOptions(uri);
//    }

    public void loadOptions(LoaderOptions options) {
        sLoader.loadImage(options);
    }

    public void clearMemoryCache() {
        sLoader.clearMemoryCache();
    }

    public void clearDiskCache() {
        sLoader.clearDiskCache();
    }
}