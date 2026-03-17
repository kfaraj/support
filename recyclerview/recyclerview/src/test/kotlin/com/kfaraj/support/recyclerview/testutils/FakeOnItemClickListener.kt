package com.kfaraj.support.recyclerview.testutils

import android.view.View
import com.kfaraj.support.recyclerview.widget.SupportRecyclerView
import com.kfaraj.support.recyclerview.widget.SupportRecyclerView.OnItemClickListener

/**
 * Provides a fake implementation of [OnItemClickListener].
 */
class FakeOnItemClickListener : OnItemClickListener {

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
