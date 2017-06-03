package com.kfaraj.support.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;

/**
 * Adds support for image drawable tint.
 */
public interface TintableImageView {

    /**
     * Sets the tint to apply to the image drawable.
     *
     * @param tint the tint to apply to the image drawable.
     */
    void setSupportImageTintList(@Nullable ColorStateList tint);

    /**
     * Returns the tint to apply to the image drawable.
     *
     * @return the tint to apply to the image drawable.
     */
    @Nullable
    ColorStateList getSupportImageTintList();

    /**
     * Sets the tint mode to apply to the image drawable.
     *
     * @param tintMode the tint mode to apply to the image drawable.
     */
    void setSupportImageTintMode(@Nullable PorterDuff.Mode tintMode);

    /**
     * Returns the tint mode to apply to the image drawable.
     *
     * @return the tint mode to apply to the image drawable.
     */
    @Nullable
    PorterDuff.Mode getSupportImageTintMode();

}
