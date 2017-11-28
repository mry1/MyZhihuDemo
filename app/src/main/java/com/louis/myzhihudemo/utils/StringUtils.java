package com.louis.myzhihudemo.utils;

import com.orhanobut.logger.Logger;

/**
 * Created by louis on 17-11-23.
 */

public class StringUtils {

    private StringUtils() {
        throw new RuntimeException("StringUtils cannot be initialized!");
    }

    /**
     * 计算图片要显示的高度
     *
     * @param pixel 原始分辨率
     * @param width 要显示的宽度
     * @return
     */
    public static int calcPhotoHeight(String pixel, int width) {
        int height = -1;
        int index = pixel.indexOf("*");
        if (index != -1) {
            try {
                int widthPixel = Integer.parseInt(pixel.substring(0, index));
                int heightPixel = Integer.parseInt(pixel.substring(index + 1));
                height = (int) (heightPixel * (width * 1.0f / widthPixel));
            } catch (NumberFormatException e) {
                Logger.e(e.toString());
                return -1;
            }
        }

        return height;
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }
}
