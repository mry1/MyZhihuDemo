package com.louis.myzhihudemo.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.louis.myzhihudemo.AndroidApplication;
import com.louis.myzhihudemo.ui.R;

/**
 * Created by louis on 17-4-12.
 */

public class ToastUtils {

    private static Toast toast = null;

    private ToastUtils() {
        throw new RuntimeException("ToastUtils cannot be initialized!");
    }

    public static void init(Context context) {
//        sContext = context;
    }

    /**
     * 专属提示网络有问题
     */
    public static void showNetExpError() {
        showMessage(UIUtils.getString(R.string.server_error));
    }

    /**
     * 直接通过 resId来showToast
     *
     * @param strResId
     */
    public static void showMessage(@StringRes final int strResId) {
        showMessage(UIUtils.getString(strResId));
    }

    /**
     * @param msg
     */
    public static void showMessage(String msg) {
        showMessage(AndroidApplication.getContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showMessageLong(final String msg) {
        showMessage(AndroidApplication.getContext(), msg, Toast.LENGTH_LONG);
    }

    /**
     * Toast发送消息
     *
     * @param act
     * @param msg
     * @param len
     */
    public static void showMessage(final Context act, final String msg,
                                   final int len) {
        if (!UIUtils.isRunInMainThread()) {
            UIUtils.post(new Runnable() {
                @Override
                public void run() {
                    showToast(act, msg, len);
                }
            });
        } else {
            showToast(act, msg, len);
        }
    }

    private static void showToast(Context act, String msg, int len) {
        if (toast == null) {
            toast = Toast.makeText(act, msg, len);
        } else {
            toast.setText(msg);
        }
        toast.show();
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
