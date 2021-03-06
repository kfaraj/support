package com.kfaraj.support.widget;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.kfaraj.support.view.TintableTextViewTest;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SupportButtonTest
        extends TintableTextViewTest<SupportButton> {

    @Before
    public void setUp() {
        final Context context = InstrumentationRegistry.getInstrumentation().getContext();
        mTextView = new SupportButton(context);
    }

}
