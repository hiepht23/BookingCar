package com.vnpt.media.bookingcar.utils;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.PathInterpolator;

public class AnimationUnit {

    public final static int COLOR_ANIMATION_DURATION = 1000;
    public final static int DEFAULT_DELAY = 0;
    final static String TAG = "AnimationUnit";

    public static void animateViewColor(View v, int startColor, int endColor) {

        ObjectAnimator animator = ObjectAnimator.ofObject(v, "backgroundColor",
                new ArgbEvaluator(), startColor, endColor);

        if (Build.VERSION.SDK_INT >= 21) {
            animator.setInterpolator(new PathInterpolator(0.4f, 0f, 1f, 1f));
        }
        animator.setDuration(COLOR_ANIMATION_DURATION);
        animator.start();
    }

    public static void animateViewElevation(View v, float start, float end) {
        if (Build.VERSION.SDK_INT >= 21) {
            ObjectAnimator animator = ObjectAnimator.ofObject(v, "elevation", new FloatEvaluator(), start, end);
            animator.setDuration(500);
            animator.start();
        }
    }

    public static ViewPropertyAnimator hideViewByScaleXY(View v) {

        return hideViewByScale(v, DEFAULT_DELAY, 0, 0);
    }

    public static ViewPropertyAnimator hideViewByScaleY(View v) {

        return hideViewByScale(v, DEFAULT_DELAY, 1, 0);
    }


    public static ViewPropertyAnimator hideViewByScalyInX(View v) {

        return hideViewByScale(v, DEFAULT_DELAY, 0, 1);
    }

    private static ViewPropertyAnimator hideViewByScale(View v, int delay, int x, int y) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(delay)
                .scaleX(x).scaleY(y);

        return propertyAnimator;
    }

    public static ViewPropertyAnimator showViewByScale(View v) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(DEFAULT_DELAY)
                .scaleX(1).scaleY(1);

        return propertyAnimator;
    }
}
