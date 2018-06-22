package com.louis.myzhihudemo.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by louis on 17-11-28.
 */

public class PreferencesUtils {

    /**
     * 获取保存路径
     *
     * @param context
     * @return
     */
    public static String getSavePath(Context context) {
        return getString(context, Constant.SAVE_PATH_KEY, Constant.DEFAULT_SAVE_PATH);
    }

    public static String getString(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }
}
