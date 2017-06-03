package com.kfaraj.support.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import com.kfaraj.support.appcompat.test.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.kfaraj.support.util.TestUtils.assertAllPixelsOfColor;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public abstract class TintableImageViewTest<T extends ImageView & TintableImageView> {

    protected T mImageView;

    @Test
    public void testSupportImageTint() {
        final ColorStateList tint = ColorStateList.valueOf(Color.BLACK);
        final PorterDuff.Mode tintMode = PorterDuff.Mode.MULTIPLY;
        mImageView.setImageResource(R.drawable.ic_white_24dp);
        mImageView.setSupportImageTintList(tint);
        mImageView.setSupportImageTintMode(tintMode);
        assertEquals(tint, mImageView.getSupportImageTintList());
        assertEquals(tintMode, mImageView.getSupportImageTintMode());
        assertAllPixelsOfColor(mImageView.getDrawable(), tint.getDefaultColor());
    }

}
