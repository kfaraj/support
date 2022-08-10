package com.kfaraj.support.recyclerview.sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainAdapter(
    val items: ArrayList<String>
) : RecyclerView.Adapter<MainViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_card, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item
    }

    override fun getItemId(position: Int): Long {
        val item = items[position]
        return item.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
