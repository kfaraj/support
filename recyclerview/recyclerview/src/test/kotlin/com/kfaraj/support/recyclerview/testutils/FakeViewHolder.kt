package com.kfaraj.support.recyclerview.testutils

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides a fake implementation of [RecyclerView.ViewHolder].
 */
class FakeViewHolder<T : Any>(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    TextView(parent.context)
) {

    private val textView = itemView as TextView

    /**
     * Binds the [item] with the view.
     */
    fun bind(item: T) {
        textView.text = item.toString()
    }

}
