package com.kfaraj.support.recyclerview.samples.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainAdapter<T : Any>(
    val items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<MainViewHolder<T>>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder<T> {
        return MainViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder<T>, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemId(position: Int): Long {
        val item = items[position]
        return item.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
