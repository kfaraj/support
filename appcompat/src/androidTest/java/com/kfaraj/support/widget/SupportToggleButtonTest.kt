package com.kfaraj.support.widget

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.kfaraj.support.view.TintableTextViewTest
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class SupportToggleButtonTest :
    TintableTextViewTest<SupportToggleButton>() {

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        textView = SupportToggleButton(context)
    }

}
