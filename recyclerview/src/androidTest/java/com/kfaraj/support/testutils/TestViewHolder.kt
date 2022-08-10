package com.kfaraj.support.testutils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides a test double of [RecyclerView.ViewHolder].
 */
class TestViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    val textView = itemView as TextView

}
