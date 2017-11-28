package com.louis.myzhihudemo.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.louis.myzhihudemo.ui.R;

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

    public static void showSnackBar(View view, int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view, String text, boolean isLong) {
        Snackbar.make(view, text, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 显示 Snackbar
     *
     * @param activity
     * @param message
     * @param isLong
     */
    public static void showSnackBar(Activity activity, String message, boolean isLong) {
        if (activity == null) {
            return;

        }

        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();


    }
}
