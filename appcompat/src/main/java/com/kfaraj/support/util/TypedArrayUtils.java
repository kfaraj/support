package com.kfaraj.support.util;

import android.content.res.TypedArray;
import android.graphics.PorterDuff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;

/**
 * Provides utility methods for {@link TypedArray}.
 */
public class TypedArrayUtils {

    /**
     * Retrieves the tint mode for the attribute at index.
     *
     * @param a the typed array.
     * @param index the index.
     * @param defValue the default value.
     * @return the tint mode for the attribute at index.
     */
    public static PorterDuff.Mode getTintMode(@NonNull TypedArray a,
            @StyleableRes int index, @Nullable PorterDuff.Mode defValue) {
        final int value = a.getInt(index, 0);
        switch (value) {
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
