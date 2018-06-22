package com.louis.myzhihudemo.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.louis.myzhihudemo.AndroidApplication;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


/**
 * 创建者     罗夏雨
 * 创建时间   2016/10/9 17:08
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class UIUtils {

    private static final String TAG = "UIUtils";

    public static Context getContext() {
        return AndroidApplication.getContext();
    }

    public static long getMainThreadId() {
        return AndroidApplication.getMainThreadId();
    }

    /**
     * dimen转换成px
     *
     * @param dimenId
     * @return
     */
    public static int dimenToPx(int dimenId) {
        return (int) AndroidApplication.getContext().getResources().getDimension(dimenId);
    }

    /**
     * dip转换px
     */
    public static int dp2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return AndroidApplication.getMainThreadHandler();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    /**
     * 从主线程looper里面移除所有runnable
     */
    public static void removeAllCallbacks() {
        getHandler().removeCallbacksAndMessages(null);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字(附加字符串)
     */
    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取数字数组
     */
    public static int[] getIntArray(int resId) {
        return getResources().getIntArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    //判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }


    /**
     * 如果前一个string是empty的话，返回后一个string
     *
     * @param str
     * @param _default
     * @return
     */
    public static String handleString(String str, String _default) {
        if (TextUtils.isEmpty(str)) {
            return _default;
        }
        return str;
    }

    public static String handleString(String str) {
        return handleString(str, "");
    }

    public static String handleString(String string, @StringRes int _defaultStrRes) {
        return handleString(string, UIUtils.getString(_defaultStrRes));
    }


    /**
     * 统计字节个数，中文类两个，字母类1个
     *
     * @param etstring
     * @return
     */
    public static int calculateLength(String etstring) {
        char[] ch = etstring.toCharArray();

        int varlength = 0;
        for (int i = 0; i < ch.length; i++) {
            // changed by zyf 0825 , bug 6918，加入中文标点范围 ， TODO 标点范围有待具体化
            if ((ch[i] >= 0x2E80 && ch[i] <= 0xFE4F) || (ch[i] >= 0xA13F && ch[i] <= 0xAA40) || ch[i] >= 0x80) { // 中文字符范围0x4e00 0x9fbb
                varlength = varlength + 2;
            } else {
                varlength++;
            }
        }

        return varlength;
    }

    public static String getLimitTextVaule(String etstring, int num) {
        StringBuffer sb = new StringBuffer();
        char[] ch = etstring.toCharArray();
        int varlength = 0;
        for (int i = 0; i < ch.length; i++) {
            // changed by zyf 0825 , bug 6918，加入中文标点范围 ， TODO 标点范围有待具体化
            if ((ch[i] >= 0x2E80 && ch[i] <= 0xFE4F) || (ch[i] >= 0xA13F && ch[i] <= 0xAA40) || ch[i] >= 0x80) { // 中文字符范围0x4e00 0x9fbb
                varlength = varlength + 2;
            } else {
                varlength++;
            }
            if (varlength <= num) {
                sb.append(ch[i]);
            } else
                break;
        }
        return sb.toString();
    }

    public static String getLimitText(String text, int num) {
        if (text == null || text.trim().length() == 0) {
            return text;
        }
        if (text.length() < num) {
            return text;
        }

        String substring = text.substring(0, num);
        substring = substring + "...";
        return substring;
    }

    /**
     * 得到固定长度的 string字符串，如果长度超过 maxLength 则截取 maxLength
     * 小于则直接返回
     * 主要用于截取长度过长的字符串， 如公司名  生成 例子：公司名...
     *
     * @param str          要处理的字符
     * @param maxLength    要截取的长度
     * @param appendString 为后面跟着的字符， 如...
     * @param defalut_     为str为空时额返回值
     * @return
     */
    public static String getShortString(String str, int maxLength, String appendString, String defalut_) {
        if (TextUtils.isEmpty(str)) {
            return defalut_;
        }
        if (str.length() < maxLength) {
            return str;
        } else {
            return str.substring(0, maxLength) + appendString;
        }
    }

    public static String getShortString(String str, int maxLength) {
        return getShortString(str, maxLength, "...", "");
    }

    public static int getWindowWidth() {
        WindowManager windowManager = (WindowManager) AndroidApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        return width;

    }

    public static int getWindowHeight() {
        WindowManager windowManager = (WindowManager) AndroidApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = windowManager.getDefaultDisplay().getHeight();
        return height;
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 给EditText设置数据,并将光标置于末尾
     *
     * @param editText
     * @param text
     */
    public static void setEditText(EditText editText, String text) {
        editText.setText(text);
        editText.requestFocus();
        if (text.length() > 0) {
            editText.setSelection(text.length());
        }
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;
    }

    public static int getDpi(Context context) {

        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getScreenWight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static InputStream getAssertFile(Context context, String filename) {
        try {
            return context.getAssets().open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param context
     * @param resId   数组的id
     * @return
     */
    public static int[] getIntResIds(Context context, @ArrayRes int resId) {
        TypedArray mTypedArray = context.getResources().obtainTypedArray(resId);
        int len = mTypedArray.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = mTypedArray.getResourceId(i, 0);
        }
        mTypedArray.recycle();
        return resIds;
    }
}
