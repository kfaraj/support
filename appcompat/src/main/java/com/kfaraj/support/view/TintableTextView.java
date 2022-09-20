package com.kfaraj.support.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

import androidx.annotation.Nullable;
import androidx.core.widget.TintableCompoundDrawablesView;

/**
 * Adds support for compound drawables tint.
 *
 * @deprecated Use {@link TintableCompoundDrawablesView} instead.
 */
@Deprecated
public interface TintableTextView {

    /**
     * Sets the tint to apply to the compound drawables.
     *
     * @param tint the tint to apply to the compound drawables.
     * @deprecated Use
     * {@link TintableCompoundDrawablesView#setSupportCompoundDrawablesTintList(ColorStateList)}
     * instead.
     */
    @Deprecated
    void setSupportCompoundDrawableTintList(@Nullable ColorStateList tint);

    /**
     * Returns the tint to apply to the compound drawables.
     *
     * @return the tint to apply to the compound drawables.
     * @deprecated Use
     * {@link TintableCompoundDrawablesView#getSupportCompoundDrawablesTintList()}
     * instead.
     */
    @Deprecated
    @Nullable
    ColorStateList getSupportCompoundDrawableTintList();

    /**
     * Sets the blending mode used to apply the tint to the compound drawables.
     *
     * @param tintMode the blending mode used to apply the tint to the compound drawables.
     * @deprecated Use
     * {@link TintableCompoundDrawablesView#setSupportCompoundDrawablesTintMode(PorterDuff.Mode)}
     * instead.
     */
    @Deprecated
    void setSupportCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode);

    /**
     * Returns the blending mode used to apply the tint to the compound drawables.
     *
     * @return the blending mode used to apply the tint to the compound drawables.
     * @deprecated Use
     * {@link TintableCompoundDrawablesView#getSupportCompoundDrawablesTintMode()}
     * instead.
     */
    @Deprecated
    @Nullable
    PorterDuff.Mode getSupportCompoundDrawableTintMode();

}
