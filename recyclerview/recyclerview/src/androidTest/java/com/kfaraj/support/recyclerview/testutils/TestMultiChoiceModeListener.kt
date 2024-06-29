package com.kfaraj.support.recyclerview.testutils

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.kfaraj.support.recyclerview.widget.SupportRecyclerView.MultiChoiceModeListener

/**
 * Provides a test double of [MultiChoiceModeListener].
 */
class TestMultiChoiceModeListener : MultiChoiceModeListener {

    /**
     * Whether it has been invoked.
     */
    var invoked = false

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        invoked = true
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        invoked = true
        return true
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        invoked = true
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        invoked = true
    }

    override fun onItemCheckedStateChanged(
        mode: ActionMode,
        position: Int,
        id: Long,
        checked: Boolean
    ) {
        invoked = true
    }

}
