package com.kfaraj.support.recyclerview.widget

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.SparseBooleanArray
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.Checkable
import androidx.annotation.AttrRes
import androidx.core.os.BundleCompat
import androidx.recyclerview.widget.RecyclerView
import com.kfaraj.support.recyclerview.R
import com.kfaraj.support.recyclerview.util.SparseLongArray

/**
 * Adds support for empty view, item click and choice mode.
 * * [emptyView]
 * * [onItemClickListener]
 * * [onItemLongClickListener]
 * * [choiceMode]
 * * [multiChoiceModeListener]
 * * [clearChoices]
 * * [setItemChecked]
 * * [isItemChecked]
 * * [checkedItemCount]
 * * [checkedItemPosition]
 * * [checkedItemPositions]
 * * [checkedItemIds]
 * * [R.attr.choiceMode]
 */
internal class RecyclerViewHelper<T : RecyclerView>(
    private val recyclerView: T
) : RecyclerView.OnChildAttachStateChangeListener,
    View.OnClickListener,
    View.OnLongClickListener,
    ActionMode.Callback {

    private val observer: RecyclerView.AdapterDataObserver = RecyclerViewAdapterDataObserver()

    private var adapter: RecyclerView.Adapter<*>? = null
    private var childrenCount = 0
    private var checkedItems: SparseLongArray? = SparseLongArray()
    private var actionMode: ActionMode? = null

    /**
     * Loads the attributes from XML.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style attribute.
     */
    fun loadFromAttributes(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.RecyclerViewHelper,
            defStyleAttr,
            0
        )
        try {
            if (a.hasValue(R.styleable.RecyclerViewHelper_choiceMode)) {
                choiceMode = a.getInt(R.styleable.RecyclerViewHelper_choiceMode, 0)
            }
        } finally {
            a.recycle()
        }
    }

    /**
     * TODO
     */
    fun onSaveInstanceState(state: Bundle) {
        state.putInt(KEY_CHOICE_MODE, choiceMode)
        state.putParcelable(KEY_CHECKED_ITEMS, checkedItems)
        state.putBoolean(KEY_ACTION_MODE, actionMode != null)
    }

    /**
     * TODO
     */
    fun onRestoreInstanceState(state: Bundle?) {
        if (state == null) {
            return
        }
        choiceMode = state.getInt(KEY_CHOICE_MODE)
        checkedItems = BundleCompat.getParcelable(
            state,
            KEY_CHECKED_ITEMS,
            SparseLongArray::class.java
        )
        actionMode = if (state.getBoolean(KEY_ACTION_MODE) && multiChoiceModeListener != null) {
            recyclerView.startActionMode(this)
        } else {
            null
        }
    }

    /**
     * TODO
     */
    fun swapAdapter(adapter: RecyclerView.Adapter<*>?) {
        if (this.adapter != null) {
            this.adapter!!.unregisterAdapterDataObserver(observer)
        }
        this.adapter = adapter
        if (this.adapter != null) {
            this.adapter!!.registerAdapterDataObserver(observer)
        }
    }

    /**
     * TODO
     */
    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        if (this.adapter != null) {
            this.adapter!!.unregisterAdapterDataObserver(observer)
        }
        this.adapter = adapter
        if (this.adapter != null) {
            this.adapter!!.registerAdapterDataObserver(observer)
        }
    }

    override fun onChildViewAttachedToWindow(view: View) {
        childrenCount++
        updateEmptyStatus()
        updateChildStatus(view)
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
    }

    override fun onChildViewDetachedFromWindow(view: View) {
        childrenCount--
        updateEmptyStatus()
        updateChildStatus(view)
        view.setOnClickListener(null)
        view.setOnLongClickListener(null)
    }

    override fun onClick(v: View) {
        val position = recyclerView.getChildAdapterPosition(v)
        val id = recyclerView.getChildItemId(v)
        if (position == RecyclerView.NO_POSITION) {
            return
        }
        if (choiceMode == CHOICE_MODE_NONE) {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(recyclerView, v, position, id)
            }
        } else if (choiceMode == CHOICE_MODE_SINGLE) {
            setItemChecked(position, true)
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(recyclerView, v, position, id)
            }
        } else if (choiceMode == CHOICE_MODE_MULTIPLE) {
            setItemChecked(position, !isItemChecked(position))
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(recyclerView, v, position, id)
            }
        } else if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
            if (checkedItemCount == 0) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(recyclerView, v, position, id)
                }
            } else {
                setItemChecked(position, !isItemChecked(position))
            }
        }
    }

    override fun onLongClick(v: View): Boolean {
        val position = recyclerView.getChildAdapterPosition(v)
        val id = recyclerView.getChildItemId(v)
        if (position == RecyclerView.NO_POSITION) {
            return false
        }
        if (choiceMode == CHOICE_MODE_NONE) {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener!!.onItemLongClick(recyclerView, v, position, id)
            }
        } else if (choiceMode == CHOICE_MODE_SINGLE) {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener!!.onItemLongClick(recyclerView, v, position, id)
            }
        } else if (choiceMode == CHOICE_MODE_MULTIPLE) {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener!!.onItemLongClick(recyclerView, v, position, id)
            }
        } else if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
            if (checkedItemCount == 0) {
                setItemChecked(position, true)
            }
            return true
        }
        return false
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        return multiChoiceModeListener!!.onCreateActionMode(mode, menu)
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return multiChoiceModeListener!!.onPrepareActionMode(mode, menu)
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return multiChoiceModeListener!!.onActionItemClicked(mode, item)
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        multiChoiceModeListener!!.onDestroyActionMode(mode)
        actionMode = null
        clearChoices()
    }

    /**
     * The view to show when the adapter is empty.
     */
    var emptyView: View? = null
        set(value) {
            field = value
            updateEmptyStatus()
        }

    /**
     * The callback to be invoked when an item has been clicked.
     */
    var onItemClickListener: OnItemClickListener<T>? = null

    /**
     * The callback to be invoked when an item has been clicked and held.
     */
    var onItemLongClickListener: OnItemLongClickListener<T>? = null

    /**
     * The choice behavior for the list.
     */
    var choiceMode: Int = CHOICE_MODE_NONE
        set(value) {
            field = value
            clearChoices()
        }

    /**
     * The callback to be invoked when an item has been checked or unchecked.
     */
    var multiChoiceModeListener: MultiChoiceModeListener<T>? = null

    /**
     * Clears any choices previously set.
     */
    fun clearChoices() {
        checkedItems!!.clear()
        if (actionMode != null && multiChoiceModeListener != null) {
            actionMode!!.finish()
        }
        updateChildrenStatus()
    }

    /**
     * Sets the checked state of the specified position.
     *
     * @param position the position.
     * @param value the state.
     */
    fun setItemChecked(position: Int, value: Boolean) {
        val id = if (adapter != null) adapter!!.getItemId(position) else RecyclerView.NO_ID
        if (choiceMode == CHOICE_MODE_SINGLE) {
            if (value) {
                checkedItems!!.clear()
                checkedItems!!.put(position, id)
            } else {
                checkedItems!!.remove(position)
            }
        } else if (choiceMode == CHOICE_MODE_MULTIPLE) {
            if (value) {
                checkedItems!!.put(position, id)
            } else {
                checkedItems!!.remove(position)
            }
        } else if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
            if (value) {
                checkedItems!!.put(position, id)
            } else {
                checkedItems!!.remove(position)
            }
            if (multiChoiceModeListener != null) {
                if (actionMode == null && checkedItems!!.size() > 0) {
                    actionMode = recyclerView.startActionMode(this)
                }
                if (actionMode != null) {
                    multiChoiceModeListener!!.onItemCheckedStateChanged(
                        actionMode!!,
                        position, id, value
                    )
                }
                if (actionMode != null && checkedItems!!.size() == 0) {
                    actionMode!!.finish()
                }
            }
        }
        updateChildrenStatus()
    }

    /**
     * Returns the checked state of the specified position.
     *
     * @param position the position.
     * @return the checked state of the specified position.
     */
    fun isItemChecked(position: Int): Boolean {
        return checkedItems!!.indexOfKey(position) >= 0
    }

    /**
     * The number of items currently selected.
     */
    val checkedItemCount: Int
        get() = checkedItems!!.size()

    /**
     * The currently checked item.
     */
    val checkedItemPosition: Int
        get() = if (checkedItems!!.size() == 1) {
            checkedItems!!.keyAt(0)
        } else {
            RecyclerView.NO_POSITION
        }

    /**
     * The set of checked items in the list.
     */
    val checkedItemPositions: SparseBooleanArray
        get() {
            val count = checkedItems!!.size()
            val positions = SparseBooleanArray(count)
            for (i in 0 until count) {
                positions.put(checkedItems!!.keyAt(i), true)
            }
            return positions
        }

    /**
     * The set of checked items IDs.
     */
    val checkedItemIds: LongArray
        get() {
            val count = checkedItems!!.size()
            val ids = LongArray(count)
            for (i in 0 until count) {
                ids[i] = checkedItems!!.valueAt(i)
            }
            return ids
        }

    /**
     * Updates the visibility of the empty view.
     */
    private fun updateEmptyStatus() {
        val emptyView = emptyView
        if (emptyView != null) {
            if (childrenCount > 0) {
                emptyView.visibility = View.GONE
            } else {
                emptyView.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Updates the checked state of the children.
     */
    private fun updateChildrenStatus() {
        val count = recyclerView.childCount
        for (i in 0 until count) {
            val child = recyclerView.getChildAt(i)
            updateChildStatus(child)
        }
    }

    /**
     * Updates the checked state of the child.
     *
     * @param child the child.
     */
    private fun updateChildStatus(child: View) {
        val position = recyclerView.getChildAdapterPosition(child)
        val checked = isItemChecked(position)
        if (child is Checkable) {
            (child as Checkable).isChecked = checked
        } else {
            child.isActivated = checked
        }
    }

    /**
     * Returns the adapter position of the item represented by this ID.
     *
     * @param id the item ID.
     * @return the adapter position of the item represented by this ID.
     */
    private fun getAdapterPosition(id: Long): Int {
        if (adapter != null && adapter!!.hasStableIds()) {
            val count = adapter!!.itemCount
            for (i in 0 until count) {
                if (adapter!!.getItemId(i) == id) {
                    return i
                }
            }
        }
        return RecyclerView.NO_POSITION
    }

    /**
     * Observer class for watching changes to the adapter.
     */
    private inner class RecyclerViewAdapterDataObserver : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            super.onChanged()
            if (adapter != null && adapter!!.hasStableIds()) {
                var i = 0
                while (i < checkedItems!!.size()) {
                    val position = checkedItems!!.keyAt(i)
                    val id = checkedItems!!.valueAt(i)
                    val newPosition = getAdapterPosition(id)
                    val newId = checkedItems!![newPosition]
                    if (newPosition != position) {
                        if (newPosition != RecyclerView.NO_POSITION) {
                            checkedItems!!.put(newPosition, id)
                            i = checkedItems!!.indexOfKey(position)
                            if (newId != null) {
                                checkedItems!!.setValueAt(i--, newId)
                            } else {
                                checkedItems!!.removeAt(i--)
                            }
                        } else {
                            checkedItems!!.removeAt(i--)
                            if (actionMode != null && multiChoiceModeListener != null) {
                                multiChoiceModeListener!!.onItemCheckedStateChanged(
                                    actionMode!!,
                                    position, id, false
                                )
                                if (checkedItems!!.size() == 0) {
                                    actionMode!!.finish()
                                }
                            }
                        }
                    }
                    i++
                }
            }
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            for (i in checkedItems!!.size() - 1 downTo 0) {
                val position = checkedItems!!.keyAt(i)
                val id = checkedItems!!.valueAt(i)
                if (position >= positionStart) {
                    checkedItems!!.removeAt(i)
                    checkedItems!!.put(position + itemCount, id)
                }
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            var i = 0
            while (i < checkedItems!!.size()) {
                val position = checkedItems!!.keyAt(i)
                val id = checkedItems!!.valueAt(i)
                if (position >= positionStart + itemCount) {
                    checkedItems!!.removeAt(i)
                    checkedItems!!.put(position - itemCount, id)
                } else if (position >= positionStart) {
                    checkedItems!!.removeAt(i--)
                    if (actionMode != null && multiChoiceModeListener != null) {
                        multiChoiceModeListener!!.onItemCheckedStateChanged(
                            actionMode!!,
                            position, id, false
                        )
                        if (checkedItems!!.size() == 0) {
                            actionMode!!.finish()
                        }
                    }
                }
                i++
            }
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            for (i in 0 until itemCount) {
                val fromId = checkedItems!![fromPosition + i]
                val toId = checkedItems!![toPosition + i]
                if (fromId != null) {
                    checkedItems!!.put(toPosition + i, fromId)
                } else {
                    checkedItems!!.remove(toPosition + i)
                }
                if (toId != null) {
                    checkedItems!!.put(fromPosition + i, toId)
                } else {
                    checkedItems!!.remove(fromPosition + i)
                }
            }
        }

    }

    companion object {

        /**
         * The list does not indicate choices.
         */
        const val CHOICE_MODE_NONE = AbsListView.CHOICE_MODE_NONE

        /**
         * The list allows up to one choice.
         */
        const val CHOICE_MODE_SINGLE = AbsListView.CHOICE_MODE_SINGLE

        /**
         * The list allows multiple choices.
         */
        const val CHOICE_MODE_MULTIPLE = AbsListView.CHOICE_MODE_MULTIPLE

        /**
         * The list allows multiple choices in a modal selection mode.
         */
        const val CHOICE_MODE_MULTIPLE_MODAL = AbsListView.CHOICE_MODE_MULTIPLE_MODAL

        private const val KEY_CHOICE_MODE = "com.kfaraj.support.recyclerview.choice_mode"
        private const val KEY_CHECKED_ITEMS = "com.kfaraj.support.recyclerview.checked_items"
        private const val KEY_ACTION_MODE = "com.kfaraj.support.recyclerview.action_mode"

    }

    /**
     * Callback to be invoked when an item has been clicked.
     */
    fun interface OnItemClickListener<T> {

        /**
         * Called when an item has been clicked.
         *
         * @param parent the parent.
         * @param view the item view.
         * @param position the item position.
         * @param id the item ID.
         */
        fun onItemClick(
            parent: T,
            view: View,
            position: Int,
            id: Long
        )

    }

    /**
     * Callback to be invoked when an item has been clicked and held.
     */
    fun interface OnItemLongClickListener<T> {

        /**
         * Called when an item has been clicked and held.
         *
         * @param parent the parent.
         * @param view the item view.
         * @param position the item position.
         * @param id the item ID.
         * @return `true` if the event was consumed, `false` otherwise.
         */
        fun onItemLongClick(
            parent: T,
            view: View,
            position: Int,
            id: Long
        ): Boolean

    }

    /**
     * Callback to be invoked when an item has been checked or unchecked.
     */
    interface MultiChoiceModeListener<T> : ActionMode.Callback {

        /**
         * Called when an item has been checked or unchecked.
         *
         * @param mode the action mode.
         * @param position the item position.
         * @param id the item ID.
         * @param checked whether the item is now checked.
         */
        fun onItemCheckedStateChanged(
            mode: ActionMode,
            position: Int,
            id: Long,
            checked: Boolean
        )

    }

}
