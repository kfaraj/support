package com.kfaraj.support.recyclerview.sample

import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    val textView = ViewCompat.requireViewById<TextView>(itemView, R.id.text)

}
