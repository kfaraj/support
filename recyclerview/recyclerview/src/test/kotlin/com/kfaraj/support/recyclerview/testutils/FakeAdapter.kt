package com.kfaraj.support.recyclerview.testutils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides a fake implementation of [RecyclerView.Adapter].
 */
class FakeAdapter<T : Any>(
    val items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<FakeViewHolder<T>>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FakeViewHolder<T> {
        return FakeViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FakeViewHolder<T>, position: Int) {
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
