package com.kfaraj.support.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

import androidx.annotation.Nullable;

/**
 * Adds support for compound drawables tint.
 */
public interface TintableTextView {

    /**
     * Sets the tint to apply to the compound drawables.
     *
     * @param tint the tint to apply to the compound drawables.
     */
    void setSupportCompoundDrawableTintList(@Nullable ColorStateList tint);

    /**
     * Returns the tint to apply to the compound drawables.
     *
     * @return the tint to apply to the compound drawables.
     */
    @Nullable
    ColorStateList getSupportCompoundDrawableTintList();

    /**
     * Sets the blending mode used to apply the tint to the compound drawables.
     *
     * @param tintMode the blending mode used to apply the tint to the compound drawables.
     */
    void setSupportCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode);

    /**
     * Returns the blending mode used to apply the tint to the compound drawables.
     *
     * @return the blending mode used to apply the tint to the compound drawables.
     */
    @Nullable
    PorterDuff.Mode getSupportCompoundDrawableTintMode();

}
