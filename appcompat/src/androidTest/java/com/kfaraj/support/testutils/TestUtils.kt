package com.kfaraj.support.testutils

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import org.junit.Assert.assertEquals

/**
 * Provides utility methods for tests.
 */
object TestUtils {

    /**
     * Asserts that all the pixels in the specified [drawable] are of the same specified [color].
     */
    fun assertAllPixelsOfColor(drawable: Drawable, @ColorInt color: Int) {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val bitmap = createBitmap(width, height).applyCanvas {
            drawable.setBounds(0, 0, width, height)
            drawable.draw(this)
        }
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        try {
            for (pixel in pixels) {
                assertEquals(color, pixel)
            }
        } finally {
            bitmap.recycle()
        }
    }

}
