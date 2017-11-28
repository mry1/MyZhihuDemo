package com.louis.myzhihudemo.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by louis on 17-11-28.
 */

public class DialogHelper {

    /**
     * 弹出删除对话框
     * @param context
     * @param listener
     */
    public static void deleteDialog(Context context, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("是否删除")
                .setPositiveButton("确定", listener)
                .setNegativeButton("取消", null);
        builder.create().show();

    }

}
