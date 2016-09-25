package com.kfaraj.support.util;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Provides utility methods to use with {@link Drawable}.
 */
public class DrawableUtils {

    /**
     * Applies a tint to the drawable.
     *
     * @param drawable the drawable.
     * @param tint the tint.
     * @param tintMode the tint mode.
     * @return the tinted drawable.
     */
    public static Drawable applyTint(@Nullable Drawable drawable, @Nullable ColorStateList tint, @Nullable PorterDuff.Mode tintMode) {
        if (drawable != null) {
            drawable = drawable.mutate();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawable, tint);
            DrawableCompat.setTintMode(drawable, tintMode);
        }
        return drawable;
    }

}
