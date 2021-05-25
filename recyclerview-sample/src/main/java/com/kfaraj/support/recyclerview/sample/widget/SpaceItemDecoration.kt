package com.kfaraj.support.recyclerview.sample.widget

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

/**
 * Adds [space] around each item.
 */
class SpaceItemDecoration(
    @Dimension private val space: Int
) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(space, space, space, space)
    }

}
