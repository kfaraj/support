package com.kfaraj.support.widget;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;

import com.kfaraj.support.view.TintableTextViewTest;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SupportCheckedTextViewTest
        extends TintableTextViewTest<SupportCheckedTextView> {

    @Before
    public void setUp() {
        final Context context = InstrumentationRegistry.getContext();
        mTextView = new SupportCheckedTextView(context);
    }

}
