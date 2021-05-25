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
public class SupportMultiAutoCompleteTextViewTest
        extends TintableTextViewTest<SupportMultiAutoCompleteTextView> {

    @Before
    public void setUp() {
        // AutoCompleteTextView must be instantiated in the main thread prior to M.
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            final Context context = InstrumentationRegistry.getInstrumentation().getContext();
            mTextView = new SupportMultiAutoCompleteTextView(context);
        });
    }

}
