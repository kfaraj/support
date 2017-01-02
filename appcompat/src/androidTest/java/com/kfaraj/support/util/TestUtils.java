package com.kfaraj.support.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import static org.junit.Assert.assertEquals;

/**
 * Provides utility methods for tests.
 */
public class TestUtils {

    /**
     * Asserts that all the pixels in the specified drawable are of the same specified color.
     *
     * @param drawable the drawable.
     * @param color the color.
     */
    public static void assertAllPixelsOfColor(@NonNull Drawable drawable, @ColorInt int color) {
        final int width = drawable.getIntrinsicWidth();
        final int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        try {
            for (int pixel : pixels) {
                assertEquals(color, pixel);
            }
        } finally {
            bitmap.recycle();
        }
    }

}
