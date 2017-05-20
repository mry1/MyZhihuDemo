package com.louis.myzhihudemo.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Louis on 2017/5/20.
 */

public class CopyTextToClipboardUtil {
    private CopyTextToClipboardUtil() {
    }

    /**
     * 复制文字到粘贴板上
     * @param context
     * @param text
     */
    public static void copyTextToClipboard(Context context, CharSequence text) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", text);
        manager.setPrimaryClip(clipData);
    }

}
