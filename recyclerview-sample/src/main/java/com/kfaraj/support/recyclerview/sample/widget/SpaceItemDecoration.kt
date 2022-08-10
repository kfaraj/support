package com.kfaraj.support.recyclerview.sample.widget

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

/**
 * Adds [space] around each item.
 */
class SpaceItemDecoration(
    @Dimension private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(space, space, space, space)
    }

}
