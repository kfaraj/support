package com.kfaraj.support.widget;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.kfaraj.support.view.TintableTextViewTest;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SupportMultiAutoCompleteTextViewTest extends TintableTextViewTest<SupportMultiAutoCompleteTextView> {

    @Before
    public void setUp() {
        // AutoCompleteTextView must be instantiated in the main thread.
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Context context = InstrumentationRegistry.getContext();
                mTextView = new SupportMultiAutoCompleteTextView(context);
            }
        });
    }

}
