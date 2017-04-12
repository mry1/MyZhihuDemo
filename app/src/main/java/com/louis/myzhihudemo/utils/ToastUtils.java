package com.louis.myzhihudemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by louis on 17-4-12.
 */

public class ToastUtils {

    /**
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT);
    }
}
