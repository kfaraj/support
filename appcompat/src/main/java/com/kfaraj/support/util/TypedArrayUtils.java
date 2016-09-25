package com.kfaraj.support.util;

import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;

/**
 * Provides utility methods to use with {@link TypedArray}.
 */
public class TypedArrayUtils {

    /**
     * Retrieve the tint mode for the attribute at index.
     *
     * @param a the typed array.
     * @param index the index.
     * @param defValue the default value.
     * @return the tint mode for the attribute at index.
     */
    public static PorterDuff.Mode getTintMode(@NonNull TypedArray a, @StyleableRes int index, @Nullable PorterDuff.Mode defValue) {
        switch (a.getInt(index, 0)) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return defValue;
        }
    }

}
