package com.louis.myzhihudemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by louis on 17-11-28.
 */

public class PreferencesUtils {

    private static SharedPreferences mSharedPreferences;

    public static void init(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 获取保存路径
     *
     * @return
     */
    public static String getSavePath() {
        return getString(Constant.SAVE_PATH_KEY, Constant.DEFAULT_SAVE_PATH);
    }

    public static String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 是否非WiFi下不加载图片
     *
     * @return
     */
    public static boolean isShowImageAlways() {
        return getBoolean(Constant.NO_IMAGE_KEY, true);
    }

}
