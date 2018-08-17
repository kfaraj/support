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
public class SupportAutoCompleteTextViewTest
        extends TintableTextViewTest<SupportAutoCompleteTextView> {

    @Before
    public void setUp() {
        // AutoCompleteTextView must be instantiated in the main thread prior to M.
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final Context context = InstrumentationRegistry.getContext();
                mTextView = new SupportAutoCompleteTextView(context);
            }
        });
    }

}
