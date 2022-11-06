package com.kfaraj.support.recyclerview.sample

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainAdapter(
    val items: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<MainViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
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
