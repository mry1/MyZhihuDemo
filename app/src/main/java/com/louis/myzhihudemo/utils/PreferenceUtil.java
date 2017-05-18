package com.louis.myzhihudemo.utils;

import android.content.Context;

/**
 * Created by Louis on 2017/5/18.
 */

public class PreferenceUtil {


    private static String SETTINGS_OPTION = "settings_option";

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return context.getSharedPreferences(SETTINGS_OPTION, Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

}
