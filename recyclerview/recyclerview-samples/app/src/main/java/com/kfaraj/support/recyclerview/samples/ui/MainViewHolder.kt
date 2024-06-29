package com.kfaraj.support.recyclerview.samples.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.kfaraj.support.recyclerview.samples.R

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
) {

    private val textView = ViewCompat.requireViewById<TextView>(itemView, R.id.text)

    /**
     * Binds the [item] with the view.
     */
    fun bind(item: String) {
        textView.text = item
    }

}
