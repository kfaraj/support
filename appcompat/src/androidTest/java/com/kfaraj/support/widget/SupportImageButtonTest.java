package com.kfaraj.support.widget;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.kfaraj.support.view.TintableImageViewTest;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SupportImageButtonTest extends TintableImageViewTest<SupportImageButton> {

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getContext();
        mImageView = new SupportImageButton(context);
    }

}
