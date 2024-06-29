package com.kfaraj.support.recyclerview.testutils

import android.view.View
import com.kfaraj.support.recyclerview.widget.SupportRecyclerView
import com.kfaraj.support.recyclerview.widget.SupportRecyclerView.OnItemLongClickListener

/**
 * Provides a test double of [OnItemLongClickListener].
 */
class TestOnItemLongClickListener : OnItemLongClickListener {

    /**
     * Whether it has been invoked.
     */
    var invoked = false

    override fun onItemLongClick(
        parent: SupportRecyclerView,
        view: View,
        position: Int,
        id: Long
    ): Boolean {
        invoked = true
        return true
    }

}
