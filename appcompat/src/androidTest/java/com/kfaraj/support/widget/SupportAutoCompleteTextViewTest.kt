package com.kfaraj.support.widget

import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.kfaraj.support.view.TintableTextViewTest
import org.junit.Before

@SmallTest
class SupportAutoCompleteTextViewTest :
    TintableTextViewTest<SupportAutoCompleteTextView>() {

    @Before
    fun setUp() {
        // AutoCompleteTextView must be instantiated in the main thread prior to M.
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            textView = SupportAutoCompleteTextView(context)
        }
    }

}
