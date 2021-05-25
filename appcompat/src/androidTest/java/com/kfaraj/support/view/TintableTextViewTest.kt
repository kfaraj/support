package com.kfaraj.support.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.kfaraj.support.appcompat.test.R
import com.kfaraj.support.testutils.TestUtils.assertAllPixelsOfColor
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
abstract class TintableTextViewTest<T> where T : TextView, T : TintableTextView {

    protected lateinit var textView: T

    @Test
    fun testSupportCompoundDrawableTint() {
        val tint = ColorStateList.valueOf(Color.BLACK)
        val tintMode = PorterDuff.Mode.MULTIPLY
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_white_1px, 0, 0, 0)
        textView.supportCompoundDrawableTintList = tint
        textView.supportCompoundDrawableTintMode = tintMode
        assertEquals(tint, textView.supportCompoundDrawableTintList)
        assertEquals(tintMode, textView.supportCompoundDrawableTintMode)
        assertAllPixelsOfColor(textView.compoundDrawables[0], tint.defaultColor)
    }

}
