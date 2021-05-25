package com.kfaraj.support.widget

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.kfaraj.support.view.TintableTextViewTest
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class SupportMultiAutoCompleteTextViewTest :
    TintableTextViewTest<SupportMultiAutoCompleteTextView>() {

    @Before
    fun setUp() {
        // AutoCompleteTextView must be instantiated in the main thread prior to M.
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val context = InstrumentationRegistry.getInstrumentation().context
            textView = SupportMultiAutoCompleteTextView(context)
        }
    }

}
