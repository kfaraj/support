package com.kfaraj.support.testutils

import android.view.View
import com.kfaraj.support.widget.SupportRecyclerView
import com.kfaraj.support.widget.SupportRecyclerView.OnItemClickListener

/**
 * Provides a test double of [OnItemClickListener].
 */
class TestOnItemClickListener : OnItemClickListener {

    /**
     * Whether it has been invoked.
     */
    var invoked = false

    override fun onItemClick(
        parent: SupportRecyclerView,
        view: View,
        position: Int,
        id: Long
    ) {
        invoked = true
    }

}
