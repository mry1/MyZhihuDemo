package com.louis.myzhihudemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by louis on 17-4-12.
 */

public class ToastUtils {

    private static Context sContext;

    private ToastUtils() {
        throw new RuntimeException("ToastUtils cannot be initialized!");
    }

    public static void init(Context context) {
        sContext = context;
    }

    /**
     * @param msg
     */
    public static void showToast(String msg) {
        Toast.makeText(sContext, msg, Toast.LENGTH_SHORT).show();
    }


}
