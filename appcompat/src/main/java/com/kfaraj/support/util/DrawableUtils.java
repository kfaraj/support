package com.kfaraj.support.util;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.DrawableCompat;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * Provides utility methods for {@link Drawable}.
 */
@RestrictTo(LIBRARY_GROUP)
public final class DrawableUtils {

    private static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;

    /**
     * Constructor.
     */
    private DrawableUtils() {
    }

    /**
     * Applies the tint to the drawable.
     *
     * @param drawable the drawable.
     * @param tint the tint to apply to the drawable.
     * @param tintMode the blending mode used to apply the tint to the drawable.
     * @return the tinted drawable.
     */
    @Nullable
    public static Drawable applyTint(@Nullable Drawable drawable,
            @Nullable ColorStateList tint, @Nullable PorterDuff.Mode tintMode) {
        if (drawable != null) {
            drawable = drawable.mutate();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawable, tint);
            DrawableCompat.setTintMode(drawable, tintMode != null ? tintMode : DEFAULT_TINT_MODE);
        }
        return drawable;
    }

}
