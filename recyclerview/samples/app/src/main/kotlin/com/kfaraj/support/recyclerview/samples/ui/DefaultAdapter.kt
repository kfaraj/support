package com.kfaraj.support.recyclerview.samples.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides a default implementation of [RecyclerView.Adapter].
 */
class DefaultAdapter<T : Any>(
    val items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<DefaultViewHolder<T>>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder<T> {
        return DefaultViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DefaultViewHolder<T>, position: Int) {
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
