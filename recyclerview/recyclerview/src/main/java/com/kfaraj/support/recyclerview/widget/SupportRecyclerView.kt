package com.kfaraj.support.recyclerview.widget

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseBooleanArray
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.widget.AbsListView
import android.widget.Checkable
import androidx.annotation.AttrRes
import androidx.core.os.ParcelCompat
import androidx.customview.view.AbsSavedState
import androidx.recyclerview.widget.RecyclerView
import com.kfaraj.support.recyclerview.R
import com.kfaraj.support.recyclerview.util.SparseLongArray

/**
 * Adds support for empty view, item click and choice mode.
 * - [emptyView]
 * - [onItemClickListener]
 * - [onItemLongClickListener]
 * - [choiceMode]
 * - [multiChoiceModeListener]
 * - [clearChoices]
 * - [setItemChecked]
 * - [isItemChecked]
 * - [checkedItemCount]
 * - [checkedItemPosition]
 * - [checkedItemPositions]
 * - [checkedItemIds]
 * - [R.attr.choiceMode]
 */
public class SupportRecyclerView : RecyclerView,
    OnClickListener,
    OnLongClickListener,
    ActionMode.Callback {

    private val observer = RecyclerViewAdapterDataObserver()

    private var checkedItems = SparseLongArray()
    private var actionMode: ActionMode? = null

    /**
     * Constructor.
     *
     * @param context the context.
     */
    public constructor(
        context: Context
    ) : super(context) {
        init(context, null, 0)
    }

    /**
     * Constructor.
     *
     * @param context the context.
     * @param attrs the attributes.
     */
    public constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, 0)
    }

    /**
     * Constructor.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style attribute.
     */
    public constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    /**
     * Common code for different constructors.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style attribute.
     */
    private fun init(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.SupportRecyclerView,
            defStyleAttr,
            0
        )
        try {
            if (a.hasValue(R.styleable.SupportRecyclerView_choiceMode)) {
                choiceMode = a.getInt(
                    R.styleable.SupportRecyclerView_choiceMode,
                    0
                )
            }
        } finally {
            a.recycle()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.choiceMode = choiceMode
        savedState.checkedItems = checkedItems
        savedState.actionMode = actionMode != null
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        choiceMode = state.choiceMode
        checkedItems = state.checkedItems
        actionMode = if (state.actionMode) startActionMode(this) else null
    }

    override fun swapAdapter(adapter: Adapter<*>?, removeAndRecycleExistingViews: Boolean) {
        this.adapter?.unregisterAdapterDataObserver(observer)
        super.swapAdapter(adapter, removeAndRecycleExistingViews)
        this.adapter?.registerAdapterDataObserver(observer)
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        this.adapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        this.adapter?.registerAdapterDataObserver(observer)
    }

    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)
        updateEmptyStatus()
        updateChildStatus(child)
        child.setOnClickListener(this)
        child.setOnLongClickListener(this)
    }

    override fun onChildDetachedFromWindow(child: View) {
        super.onChildDetachedFromWindow(child)
        updateEmptyStatus()
        updateChildStatus(child)
        child.setOnClickListener(null)
        child.setOnLongClickListener(null)
    }

    override fun onClick(v: View) {
        val position = getChildAdapterPosition(v)
        val id = getChildItemId(v)
        if (position == NO_POSITION) {
            return
        }
        when (choiceMode) {
            CHOICE_MODE_NONE -> {
                onItemClickListener?.onItemClick(this, v, position, id)
            }
            CHOICE_MODE_SINGLE -> {
                setItemChecked(position, true)
                onItemClickListener?.onItemClick(this, v, position, id)
            }
            CHOICE_MODE_MULTIPLE -> {
                setItemChecked(position, !isItemChecked(position))
                onItemClickListener?.onItemClick(this, v, position, id)
            }
            CHOICE_MODE_MULTIPLE_MODAL -> {
                if (checkedItemCount == 0) {
                    onItemClickListener?.onItemClick(this, v, position, id)
                } else {
                    setItemChecked(position, !isItemChecked(position))
                }
            }
        }
    }

    override fun onLongClick(v: View): Boolean {
        val position = getChildAdapterPosition(v)
        val id = getChildItemId(v)
        if (position == NO_POSITION) {
            return false
        }
        return when (choiceMode) {
            CHOICE_MODE_NONE -> {
                onItemLongClickListener?.onItemLongClick(this, v, position, id) ?: false
            }
            CHOICE_MODE_SINGLE -> {
                onItemLongClickListener?.onItemLongClick(this, v, position, id) ?: false
            }
            CHOICE_MODE_MULTIPLE -> {
                onItemLongClickListener?.onItemLongClick(this, v, position, id) ?: false
            }
            CHOICE_MODE_MULTIPLE_MODAL -> {
                if (checkedItemCount == 0) {
                    setItemChecked(position, true)
                }
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        return multiChoiceModeListener?.onCreateActionMode(mode, menu) ?: false
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return multiChoiceModeListener?.onPrepareActionMode(mode, menu) ?: false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return multiChoiceModeListener?.onActionItemClicked(mode, item) ?: false
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        multiChoiceModeListener?.onDestroyActionMode(mode)
        actionMode = null
        clearChoices()
    }

    /**
     * The view to show when the adapter is empty.
     */
    public var emptyView: View? = null
        set(value) {
            field = value
            updateEmptyStatus()
        }

    /**
     * The callback to be invoked when an item has been clicked.
     */
    public var onItemClickListener: OnItemClickListener? = null

    /**
     * The callback to be invoked when an item has been clicked and held.
     */
    public var onItemLongClickListener: OnItemLongClickListener? = null

    /**
     * The choice behavior for the list.
     */
    public var choiceMode: Int = CHOICE_MODE_NONE
        set(value) {
            field = value
            clearChoices()
        }

    /**
     * The callback to be invoked when an item has been checked or unchecked.
     */
    public var multiChoiceModeListener: MultiChoiceModeListener? = null

    /**
     * Clears any choices previously set.
     */
    public fun clearChoices() {
        checkedItems.clear()
        actionMode?.finish()
        updateChildrenStatus()
    }

    /**
     * Sets the checked state of the specified position.
     *
     * @param position the position.
     * @param value the state.
     */
    public fun setItemChecked(position: Int, value: Boolean) {
        val id = adapter?.getItemId(position) ?: NO_ID
        when (choiceMode) {
            CHOICE_MODE_SINGLE -> {
                if (value) {
                    checkedItems.clear()
                    checkedItems.put(position, id)
                } else {
                    checkedItems.remove(position)
                }
            }
            CHOICE_MODE_MULTIPLE -> {
                if (value) {
                    checkedItems.put(position, id)
                } else {
                    checkedItems.remove(position)
                }
            }
            CHOICE_MODE_MULTIPLE_MODAL -> {
                if (value) {
                    checkedItems.put(position, id)
                } else {
                    checkedItems.remove(position)
                }
                if (actionMode == null && !checkedItems.isEmpty) {
                    actionMode = startActionMode(this)
                }
                actionMode?.let { mode ->
                    multiChoiceModeListener?.onItemCheckedStateChanged(
                        mode,
                        position,
                        id,
                        value
                    )
                }
                if (actionMode != null && checkedItems.isEmpty) {
                    actionMode?.finish()
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
    public fun isItemChecked(position: Int): Boolean {
        return checkedItems.indexOfKey(position) >= 0
    }

    /**
     * The number of items currently selected.
     */
    public val checkedItemCount: Int
        get() {
            return checkedItems.size()
        }

    /**
     * The currently checked item.
     */
    public val checkedItemPosition: Int
        get() {
            return if (checkedItems.size() == 1) {
                checkedItems.keyAt(0)
            } else {
                NO_POSITION
            }
        }

    /**
     * The set of checked items in the list.
     */
    public val checkedItemPositions: SparseBooleanArray
        get() {
            val count = checkedItems.size()
            val positions = SparseBooleanArray(count)
            for (i in 0..<count) {
                positions.put(checkedItems.keyAt(i), true)
            }
            return positions
        }

    /**
     * The set of checked items IDs.
     */
    public val checkedItemIds: LongArray
        get() {
            val count = checkedItems.size()
            val ids = LongArray(count)
            for (i in 0..<count) {
                ids[i] = checkedItems.valueAt(i)
            }
            return ids
        }

    /**
     * Updates the visibility of the empty view.
     */
    private fun updateEmptyStatus() {
        val itemCount = adapter?.itemCount ?: 0
        emptyView?.visibility = if (itemCount > 0) GONE else VISIBLE
    }

    /**
     * Updates the checked state of the children.
     */
    private fun updateChildrenStatus() {
        for (i in 0..<childCount) {
            val child = getChildAt(i)
            updateChildStatus(child)
        }
    }

    /**
     * Updates the checked state of the child.
     *
     * @param child the child.
     */
    private fun updateChildStatus(child: View) {
        val position = getChildAdapterPosition(child)
        val checked = isItemChecked(position)
        if (child is Checkable) {
            child.isChecked = checked
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
        if (adapter?.hasStableIds() == true) {
            val itemCount = adapter?.itemCount ?: 0
            for (i in 0..<itemCount) {
                if (adapter?.getItemId(i) == id) {
                    return i
                }
            }
        }
        return NO_POSITION
    }

    /**
     * Observer class for watching changes to the adapter.
     */
    private inner class RecyclerViewAdapterDataObserver : AdapterDataObserver() {

        override fun onChanged() {
            super.onChanged()
            if (adapter?.hasStableIds() == true) {
                var i = 0
                while (i < checkedItems.size()) {
                    val position = checkedItems.keyAt(i)
                    val id = checkedItems.valueAt(i)
                    val newPosition = getAdapterPosition(id)
                    val newId = checkedItems[newPosition]
                    if (newPosition != position) {
                        if (newPosition != NO_POSITION) {
                            checkedItems.put(newPosition, id)
                            i = checkedItems.indexOfKey(position)
                            if (newId != null) {
                                checkedItems.setValueAt(i--, newId)
                            } else {
                                checkedItems.removeAt(i--)
                            }
                        } else {
                            checkedItems.removeAt(i--)
                            actionMode?.let { mode ->
                                multiChoiceModeListener?.onItemCheckedStateChanged(
                                    mode,
                                    position,
                                    id,
                                    false
                                )
                            }
                            if (actionMode != null && checkedItems.isEmpty) {
                                actionMode?.finish()
                            }
                        }
                    }
                    i++
                }
            }
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            var i = checkedItems.size() - 1
            while (i >= 0) {
                val position = checkedItems.keyAt(i)
                val id = checkedItems.valueAt(i)
                if (position >= positionStart) {
                    checkedItems.removeAt(i)
                    checkedItems.put(position + itemCount, id)
                }
                i--
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            var i = 0
            while (i < checkedItems.size()) {
                val position = checkedItems.keyAt(i)
                val id = checkedItems.valueAt(i)
                if (position >= positionStart + itemCount) {
                    checkedItems.removeAt(i)
                    checkedItems.put(position - itemCount, id)
                } else if (position >= positionStart) {
                    checkedItems.removeAt(i--)
                    actionMode?.let { mode ->
                        multiChoiceModeListener?.onItemCheckedStateChanged(
                            mode,
                            position,
                            id,
                            false
                        )
                    }
                    if (actionMode != null && checkedItems.isEmpty) {
                        actionMode?.finish()
                    }
                }
                i++
            }
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            var i = 0
            while (i < itemCount) {
                val fromId = checkedItems[fromPosition + i]
                val toId = checkedItems[toPosition + i]
                if (fromId != null) {
                    checkedItems.put(toPosition + i, fromId)
                } else {
                    checkedItems.remove(toPosition + i)
                }
                if (toId != null) {
                    checkedItems.put(fromPosition + i, toId)
                } else {
                    checkedItems.remove(fromPosition + i)
                }
                i++
            }
        }
    }

    /**
     * Contains the state of this view.
     */
    private class SavedState : AbsSavedState {

        var choiceMode: Int
        var checkedItems: SparseLongArray
        var actionMode: Boolean

        /**
         * Constructor.
         *
         * @param superState the state of the superclass of this view.
         */
        constructor(superState: Parcelable?) : super(superState ?: EMPTY_STATE) {
            choiceMode = 0
            checkedItems = SparseLongArray()
            actionMode = false
        }

        /**
         * Constructor.
         *
         * @param source the source.
         * @param loader the class loader.
         */
        private constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            choiceMode = source.readInt()
            checkedItems = SparseLongArray.createFromParcel(source)
            actionMode = ParcelCompat.readBoolean(source)
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(choiceMode)
            checkedItems.writeToParcel(dest, flags)
            ParcelCompat.writeBoolean(dest, actionMode)
        }

        /**
         * The creator.
         */
        companion object CREATOR : Parcelable.ClassLoaderCreator<SavedState> {
            override fun createFromParcel(source: Parcel, loader: ClassLoader?): SavedState {
                return SavedState(source, loader)
            }

            override fun createFromParcel(source: Parcel): SavedState {
                return SavedState(source, null)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }

    }

    /**
     * Callback to be invoked when an item has been clicked.
     */
    public interface OnItemClickListener {

        /**
         * Called when an item has been clicked.
         *
         * @param parent the parent.
         * @param view the item view.
         * @param position the item position.
         * @param id the item ID.
         */
        public fun onItemClick(
            parent: SupportRecyclerView,
            view: View,
            position: Int,
            id: Long
        )

    }

    /**
     * Callback to be invoked when an item has been clicked and held.
     */
    public interface OnItemLongClickListener {

        /**
         * Called when an item has been clicked and held.
         *
         * @param parent the parent.
         * @param view the item view.
         * @param position the item position.
         * @param id the item ID.
         * @return `true` if the event was consumed, `false` otherwise.
         */
        public fun onItemLongClick(
            parent: SupportRecyclerView,
            view: View,
            position: Int,
            id: Long
        ): Boolean

    }

    /**
     * Callback to be invoked when an item has been checked or unchecked.
     */
    public interface MultiChoiceModeListener : ActionMode.Callback {

        /**
         * Called when an item has been checked or unchecked.
         *
         * @param mode the action mode.
         * @param position the item position.
         * @param id the item ID.
         * @param checked whether the item is now checked.
         */
        public fun onItemCheckedStateChanged(
            mode: ActionMode,
            position: Int,
            id: Long,
            checked: Boolean
        )

    }

    public companion object {

        /**
         * The list does not indicate choices.
         */
        public const val CHOICE_MODE_NONE: Int = AbsListView.CHOICE_MODE_NONE

        /**
         * The list allows up to one choice.
         */
        public const val CHOICE_MODE_SINGLE: Int = AbsListView.CHOICE_MODE_SINGLE

        /**
         * The list allows multiple choices.
         */
        public const val CHOICE_MODE_MULTIPLE: Int = AbsListView.CHOICE_MODE_MULTIPLE

        /**
         * The list allows multiple choices in a modal selection mode.
         */
        public const val CHOICE_MODE_MULTIPLE_MODAL: Int = AbsListView.CHOICE_MODE_MULTIPLE_MODAL

    }

}
