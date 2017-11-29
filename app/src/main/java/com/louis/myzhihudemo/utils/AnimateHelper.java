package com.louis.myzhihudemo.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by louis on 17-11-29.
 */

public class AnimateHelper {
    /**
     * 心跳动画
     *
     * @param view     视图
     * @param duration 时间
     */
    public static void doHeartBeat(View view, int duration) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.4f, 0.9f, 1.0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.4f, 0.9f, 1.0f)
        );
        set.setDuration(duration);
        set.start();
    }
}
