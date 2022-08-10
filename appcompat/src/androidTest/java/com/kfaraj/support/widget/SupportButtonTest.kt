package com.kfaraj.support.widget

import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.kfaraj.support.view.TintableTextViewTest
import org.junit.Before

@SmallTest
class SupportButtonTest :
    TintableTextViewTest<SupportButton>() {

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        textView = SupportButton(context)
    }

}
