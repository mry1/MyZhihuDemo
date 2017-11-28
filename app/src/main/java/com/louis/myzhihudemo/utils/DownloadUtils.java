package com.louis.myzhihudemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louis on 17-11-28.
 */

public class DownloadUtils {
    // 记录下载完的图片
    private static SparseBooleanArray sDlPhotos = new SparseBooleanArray();
    // 记录下载中的图片
    private static SparseBooleanArray sDoDlPhotos = new SparseBooleanArray();

    /**
     * 图片下载
     *
     * @param activity
     * @param url
     * @param id
     * @param listener
     */
    public static void downloadOrDeletePhoto(final Activity activity, final String url, final String id, final OnCompletedListener listener) {
        if (sDlPhotos.get(url.hashCode(), false)) {
            DialogHelper.deleteDialog(activity, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FileUtils.deleteFile(PreferencesUtils.getSavePath(activity) + File.separator + id + ".jpg");
                    listener.onDeleted(url);
                    delDownloadPhoto(url);
                }
            });
            return;
        }
        if (sDoDlPhotos.get(url.hashCode(), false)) {
            ToastUtils.showSnackBar(activity, "正在下载...", false);
            return;
        }
        sDoDlPhotos.put(url.hashCode(), true);
        ToastUtils.showSnackBar(activity, "正在下载...", false);

        Observable.just(url)
                .map(new Func1<String, Boolean>() {

                    private File file = null;

                    @Override
                    public Boolean call(String s) {
                        try {
                            file = Glide.with(activity).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        // 复制图片文件到指定路径，并改为 .jpg 后缀名
                        return FileUtils.writeFile(file.getPath(), PreferencesUtils.getSavePath(activity) +
                                File.separator + "picture" + File.separator + id + ".jpg", false);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isCompleted) {
                        if (isCompleted) {
                            // 下载完成
                            ToastUtils.showSnackBar(activity, "下载完成", false);
                            sDlPhotos.put(url.hashCode(), true);
                            if (listener != null) {
                                listener.onCompleted(url);
                            }
                        } else {
                            ToastUtils.showSnackBar(activity, "下载失败", false);
                            sDlPhotos.put(url.hashCode(), false);
                        }
                        sDoDlPhotos.put(url.hashCode(), false);

                    }
                });


    }

    private static void delDownloadPhoto(String url) {
        sDlPhotos.put(url.hashCode(), false);
        sDoDlPhotos.put(url.hashCode(), false);
    }

    public interface OnCompletedListener {
        void onCompleted(String url);

        void onDeleted(String url);

    }

}
