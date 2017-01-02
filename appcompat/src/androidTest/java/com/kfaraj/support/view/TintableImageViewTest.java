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
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public abstract class TintableImageViewTest<T extends ImageView> {

    protected T mImageView;

    @Test
    public void testSupportImageTint() {
        assertTrue(mImageView instanceof TintableImageView);
        mImageView.setImageResource(R.drawable.ic_white_24dp);
        ((TintableImageView) mImageView).setSupportImageTintList(ColorStateList.valueOf(Color.RED));
        ((TintableImageView) mImageView).setSupportImageTintMode(PorterDuff.Mode.MULTIPLY);
        assertAllPixelsOfColor(mImageView.getDrawable(), Color.RED);
    }

}
