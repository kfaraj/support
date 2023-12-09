package com.kfaraj.support.recyclerview.sample

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kfaraj.support.recyclerview.sample.util.applyWindowInsetsPadding
import com.kfaraj.support.recyclerview.sample.util.requireStringArrayList
import com.kfaraj.support.widget.SupportRecyclerView
import com.kfaraj.support.widget.SupportRecyclerView.MultiChoiceModeListener
import com.kfaraj.support.widget.SupportRecyclerView.OnItemClickListener
import com.kfaraj.support.widget.SupportRecyclerView.OnItemLongClickListener
import java.util.Collections
import java.util.UUID

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainFragment : Fragment(R.layout.fragment_main),
    OnClickListener,
    OnItemClickListener,
    OnItemLongClickListener,
    MultiChoiceModeListener {

    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: SupportRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MainAdapter()
        if (savedInstanceState != null) {
            val items = savedInstanceState.requireStringArrayList(KEY_ITEMS)
            adapter.items.addAll(items)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = ViewCompat.requireViewById(view, android.R.id.list)
        val emptyView = ViewCompat.requireViewById<TextView>(view, android.R.id.empty)
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.absoluteAdapterPosition
                val toPosition = target.absoluteAdapterPosition
                Collections.swap(adapter.items, fromPosition, toPosition)
                adapter.notifyItemMoved(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.absoluteAdapterPosition
                adapter.items.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.emptyView = emptyView
        recyclerView.onItemClickListener = this
        recyclerView.onItemLongClickListener = this
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL
        recyclerView.multiChoiceModeListener = this
        recyclerView.applyWindowInsetsPadding(
            WindowInsetsCompat.Type.systemBars(),
            applyBottom = true
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            val items = adapter.items
            putStringArrayList(KEY_ITEMS, ArrayList(items))
        }
    }

    override fun onClick(v: View) {
        val item = UUID.randomUUID().toString()
        adapter.items.add(0, item)
        adapter.notifyItemInserted(0)
        recyclerView.scrollToPosition(0)
    }

    override fun onItemClick(
        parent: SupportRecyclerView,
        view: View,
        position: Int,
        id: Long
    ) {
        val item = adapter.items[position]
        Snackbar.make(recyclerView, item, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.fab)
            .show()
    }

    override fun onItemLongClick(
        parent: SupportRecyclerView,
        view: View,
        position: Int,
        id: Long
    ): Boolean {
        val item = adapter.items[position]
        Snackbar.make(recyclerView, item, Snackbar.LENGTH_LONG)
            .setAnchorView(R.id.fab)
            .show()
        return true
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        val menuInflater = mode.menuInflater
        menuInflater.inflate(R.menu.fragment_main_contextual, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        val count = recyclerView.checkedItemCount
        mode.title = count.toString()
        return true
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return if (item.itemId == R.id.delete) {
            for (i in adapter.itemCount - 1 downTo 0) {
                if (recyclerView.isItemChecked(i)) {
                    adapter.items.removeAt(i)
                    adapter.notifyItemRemoved(i)
                }
            }
            true
        } else {
            false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        // Do nothing.
    }

    override fun onItemCheckedStateChanged(
        mode: ActionMode,
        position: Int,
        id: Long,
        checked: Boolean
    ) {
        mode.invalidate()
    }

    companion object {
        private const val KEY_ITEMS = "items"
    }

}
