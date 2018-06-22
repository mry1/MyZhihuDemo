package com.louis.myzhihudemo.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.louis.myzhihudemo.ui.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * 这个类是用于打印调试信息Log
 * Created by liuyonghong on 2016/6/16.
 */
public class LogUtils {

    private static final String TAG = "LogUtils";

    /**
     * 打印调试信息.
     */
    public static void d(String tag, String msg) {
        if (Configuration.ISDEBUG) {
            if (msg == null) {
                Log.d(tag, "msg is null,cant print");
                return;
            }
            int length = msg.length();
            int times = (int) Math.ceil(length / 1000.0);
            for (int i = 0; i < times; i++) {
                Log.i(tag, msg.substring(i * 1000, (i + 1) * 1000 < length ? (i + 1) * 1000 : length));
            }
        }
    }

    public static void d(String msg) {
        LogUtils.d(TAG, msg);
    }

    public static void d(String tag, String format, Object... args) {
        if (args.length > 0) {
            String s = String.format(format, args);
            LogUtils.d(tag, s);
        }
    }

    /**
     * 打印错误信息.
     */
    public static void e(String tag, String msg) {
        if (Configuration.ISDEBUG) {
            int length = msg.length();
            int times = (int) Math.ceil(length / 1000.0);
            for (int i = 0; i < times; i++) {
                Log.e(tag, msg.substring(i * 1000, (i + 1) * 1000 < length ? (i + 1) * 1000 : length));
            }
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (args.length > 0) {
            String s = String.format(format, args);
            LogUtils.e(tag, s);
        }
    }

    public static void e(String msg) {
        LogUtils.e(TAG, msg);
    }

    /**
     * 打印提示信息.
     */
    public static void i(String tag, String msg) {
        if (Configuration.ISDEBUG) {
            int length = msg.length();
            int times = (int) Math.ceil(length / 1000.0);
            for (int i = 0; i < times; i++) {
                Log.i(tag, msg.substring(i * 1000, (i + 1) * 1000 < length ? (i + 1) * 1000 : length));
            }
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (args.length > 0) {
            String s = String.format(format, args);
            LogUtils.i(tag, s);
        }
    }

    public static void i(String msg) {
        LogUtils.i(TAG, msg);
    }

    /**
     * 打印提示信息.
     */
    public static void v(String tag, String msg) {
        if (Configuration.ISDEBUG) {
            int length = msg.length();
            int times = (int) Math.ceil(length / 1000.0);
            for (int i = 0; i < times; i++) {
                Log.v(tag, msg.substring(i * 1000, (i + 1) * 1000 < length ? (i + 1) * 1000 : length));
            }
        }
    }

    public static void v(String tag, String format, Object... args) {
        if (args.length > 0) {
            String s = String.format(format, args);
            LogUtils.v(tag, s);
        }
    }

    public static void v(String msg) {
        LogUtils.v(TAG, msg);
    }


}
