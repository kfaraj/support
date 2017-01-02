package com.kfaraj.support.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.kfaraj.support.appcompat.test.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.kfaraj.support.util.TestUtils.assertAllPixelsOfColor;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public abstract class TintableTextViewTest<T extends TextView> {

    protected T mTextView;

    @Test
    public void testSupportCompoundDrawableTint() {
        assertTrue(mTextView instanceof TintableTextView);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_white_24dp, 0, 0, 0);
        ((TintableTextView) mTextView).setSupportCompoundDrawableTintList(ColorStateList.valueOf(Color.RED));
        ((TintableTextView) mTextView).setSupportCompoundDrawableTintMode(PorterDuff.Mode.MULTIPLY);
        assertAllPixelsOfColor(mTextView.getCompoundDrawables()[0], Color.RED);
    }

}
