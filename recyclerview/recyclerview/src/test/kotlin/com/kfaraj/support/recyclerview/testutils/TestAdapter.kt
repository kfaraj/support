package com.kfaraj.support.recyclerview.testutils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides a test double of [RecyclerView.Adapter].
 */
class TestAdapter<T : Any>(
    val items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<TestViewHolder<T>>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder<T> {
        return TestViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TestViewHolder<T>, position: Int) {
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
