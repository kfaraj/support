package com.kfaraj.support.testutils

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides a test double of [RecyclerView.Adapter].
 */
class TestAdapter(
    val items: ArrayList<Any>
) : RecyclerView.Adapter<TestViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val itemView = TextView(parent.context)
        return TestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.toString()
    }

    override fun getItemId(position: Int): Long {
        val item = items[position]
        return item.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
