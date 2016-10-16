package org.odddev.gameofthrones.core.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class BindingUtils {

    @BindingConversion
    public static int convertConditionToVisibility(final boolean condition) {
        return condition ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter("app:onClick")
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(v -> runnable.run());
    }

    @BindingAdapter("android:src")
    public static void bindImageResource(ImageView imageView, @DrawableRes int drawableRes) {
        imageView.setImageResource(drawableRes);
    }
}
