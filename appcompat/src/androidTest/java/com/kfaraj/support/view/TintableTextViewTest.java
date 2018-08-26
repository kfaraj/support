package com.kfaraj.support.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.TextView;

import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;

import com.kfaraj.support.appcompat.test.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.kfaraj.support.testutils.TestUtils.assertAllPixelsOfColor;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public abstract class TintableTextViewTest<T extends TextView & TintableTextView> {

    protected T mTextView;

    @Test
    public void testSupportCompoundDrawableTint() {
        final ColorStateList tint = ColorStateList.valueOf(Color.BLACK);
        final PorterDuff.Mode tintMode = PorterDuff.Mode.MULTIPLY;
        mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_white_24dp, 0, 0, 0);
        mTextView.setSupportCompoundDrawableTintList(tint);
        mTextView.setSupportCompoundDrawableTintMode(tintMode);
        assertEquals(tint, mTextView.getSupportCompoundDrawableTintList());
        assertEquals(tintMode, mTextView.getSupportCompoundDrawableTintMode());
        assertAllPixelsOfColor(mTextView.getCompoundDrawables()[0], tint.getDefaultColor());
    }

}
