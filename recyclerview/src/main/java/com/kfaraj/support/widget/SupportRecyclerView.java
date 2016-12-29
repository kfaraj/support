package com.kfaraj.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Checkable;

import com.kfaraj.support.recyclerview.R;
import com.kfaraj.support.util.SparseLongArray;

/**
 * Adds support for {@link RecyclerView}.
 * <ul>
 * <li>{@link #setEmptyView(View)}</li>
 * <li>{@link #getEmptyView()}</li>
 * <li>{@link #setOnItemClickListener(OnItemClickListener)}</li>
 * <li>{@link #getOnItemClickListener()}</li>
 * <li>{@link #setOnItemLongClickListener(OnItemLongClickListener)}</li>
 * <li>{@link #getOnItemLongClickListener()}</li>
 * <li>{@link #setChoiceMode(int)}</li>
 * <li>{@link #getChoiceMode()}</li>
 * <li>{@link #clearChoices()}</li>
 * <li>{@link #setItemChecked(int, boolean)}</li>
 * <li>{@link #isItemChecked(int)}</li>
 * <li>{@link #getCheckedItemCount()}</li>
 * <li>{@link #getCheckedItemPosition()}</li>
 * <li>{@link #getCheckedItemPositions()}</li>
 * <li>{@link #getCheckedItemIds()}</li>
 * <li>{@link #setMultiChoiceModeListener(MultiChoiceModeListener)}</li>
 * <li>{@link #getMultiChoiceModeListener()}</li>
 * <li>{@link R.attr#choiceMode}</li>
 * </ul>
 */
@SuppressWarnings("unused")
public class SupportRecyclerView extends RecyclerView implements OnClickListener, OnLongClickListener, ActionMode.Callback {

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
         * @param id the item id.
         */
        void onItemClick(SupportRecyclerView parent, View view, int position, long id);

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
         * @param id the item id.
         * @return true if the event was consumed, false otherwise.
         */
        boolean onItemLongClick(SupportRecyclerView parent, View view, int position, long id);

    }

    /**
     * Callback to be invoked when an item has been checked or unchecked.
     */
    public interface MultiChoiceModeListener extends ActionMode.Callback {

        /**
         * Called when an item has been checked or unchecked.
         *
         * @param mode the action mode.
         * @param position the item position.
         * @param id the item id.
         * @param checked whether the item is now checked.
         */
        void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked);

    }

    /**
     * The list does not indicate choices.
     */
    public static final int CHOICE_MODE_NONE = 0;

    /**
     * The list allows up to one choice.
     */
    public static final int CHOICE_MODE_SINGLE = 1;

    /**
     * The list allows multiple choices.
     */
    public static final int CHOICE_MODE_MULTIPLE = 2;

    /**
     * The list allows multiple choices in a modal selection mode.
     */
    public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;

    private static final String KEY_SUPER_STATE = "super_state";
    private static final String KEY_CHOICE_MODE = "choice_mode";
    private static final String KEY_CHECKED_ITEMS = "checked_items";
    private static final String KEY_ACTION_MODE = "action_mode";

    private Adapter mAdapter;
    private View mEmptyView;
    private int mChildrenCount;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private int mChoiceMode = CHOICE_MODE_NONE;
    private SparseLongArray mCheckedItems = new SparseLongArray();
    private MultiChoiceModeListener mMultiChoiceModeListener;
    private ActionMode mActionMode;

    private final AdapterDataObserver mObserver = new RecyclerViewAdapterDataObserver();

    /**
     * Constructor
     *
     * @param context the context.
     */
    public SupportRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * Constructor
     *
     * @param context the context.
     * @param attrs the attributes.
     */
    public SupportRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * Constructor
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyle the default style.
     */
    public SupportRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    /**
     * Common code for different constructors.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style.
     */
    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SupportRecyclerView, defStyleAttr, 0);
        if (a.hasValue(R.styleable.SupportRecyclerView_choiceMode)) {
            int choiceMode = a.getInt(R.styleable.SupportRecyclerView_choiceMode, CHOICE_MODE_NONE);
            setChoiceMode(choiceMode);
        }
        a.recycle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle savedState = new Bundle();
        savedState.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        savedState.putInt(KEY_CHOICE_MODE, mChoiceMode);
        savedState.putParcelable(KEY_CHECKED_ITEMS, mCheckedItems);
        savedState.putBoolean(KEY_ACTION_MODE, mActionMode != null);
        return savedState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedState = (Bundle) state;
        super.onRestoreInstanceState(savedState.getParcelable(KEY_SUPER_STATE));
        mChoiceMode = savedState.getInt(KEY_CHOICE_MODE);
        mCheckedItems = savedState.getParcelable(KEY_CHECKED_ITEMS);
        mActionMode = savedState.getBoolean(KEY_ACTION_MODE) && mMultiChoiceModeListener != null ? startActionMode(this) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mObserver);
        }
        super.swapAdapter(adapter, removeAndRecycleExistingViews);
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerAdapterDataObserver(mObserver);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAdapter(Adapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mObserver);
        }
        super.setAdapter(adapter);
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerAdapterDataObserver(mObserver);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChildAttachedToWindow(View child) {
        super.onChildAttachedToWindow(child);
        mChildrenCount++;
        updateEmptyStatus();
        updateChildStatus(child);
        child.setOnClickListener(this);
        child.setOnLongClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChildDetachedFromWindow(View child) {
        super.onChildDetachedFromWindow(child);
        mChildrenCount--;
        updateEmptyStatus();
        updateChildStatus(child);
        child.setOnClickListener(null);
        child.setOnLongClickListener(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        final int position = getChildAdapterPosition(v);
        final long id = getChildItemId(v);
        if (position == NO_POSITION) {
            return;
        }
        if (mChoiceMode == CHOICE_MODE_NONE) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(this, v, position, id);
            }
        } else if (mChoiceMode == CHOICE_MODE_SINGLE) {
            setItemChecked(position, true);
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(this, v, position, id);
            }
        } else if (mChoiceMode == CHOICE_MODE_MULTIPLE) {
            setItemChecked(position, !isItemChecked(position));
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(this, v, position, id);
            }
        } else if (mChoiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
            if (getCheckedItemCount() == 0) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(this, v, position, id);
                }
            } else {
                setItemChecked(position, !isItemChecked(position));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onLongClick(View v) {
        final int position = getChildAdapterPosition(v);
        final long id = getChildItemId(v);
        if (position == NO_POSITION) {
            return false;
        }
        if (mChoiceMode == CHOICE_MODE_NONE) {
            if (mOnItemLongClickListener != null) {
                return mOnItemLongClickListener.onItemLongClick(this, v, position, id);
            }
        } else if (mChoiceMode == CHOICE_MODE_SINGLE) {
            if (mOnItemLongClickListener != null) {
                return mOnItemLongClickListener.onItemLongClick(this, v, position, id);
            }
        } else if (mChoiceMode == CHOICE_MODE_MULTIPLE) {
            if (mOnItemLongClickListener != null) {
                return mOnItemLongClickListener.onItemLongClick(this, v, position, id);
            }
        } else if (mChoiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
            if (getCheckedItemCount() == 0) {
                setItemChecked(position, true);
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return mMultiChoiceModeListener.onCreateActionMode(mode, menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return mMultiChoiceModeListener.onPrepareActionMode(mode, menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return mMultiChoiceModeListener.onActionItemClicked(mode, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mMultiChoiceModeListener.onDestroyActionMode(mode);
        mActionMode = null;
        clearChoices();
    }

    /**
     * Sets the view to show when the adapter is empty.
     *
     * @param emptyView the view to show when the adapter is empty.
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        updateEmptyStatus();
    }

    /**
     * Returns the view to show when the adapter is empty.
     *
     * @return the view to show when the adapter is empty.
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * Sets the callback to be invoked when an item has been clicked.
     *
     * @param listener the callback to be invoked when an item has been clicked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * Returns the callback to be invoked when an item has been clicked.
     *
     * @return the callback to be invoked when an item has been clicked.
     */
    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    /**
     * Sets the callback to be invoked when an item has been clicked and held.
     *
     * @param listener the callback to be invoked when an item has been clicked and held.
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    /**
     * Returns the callback to be invoked when an item has been clicked and held.
     *
     * @return the callback to be invoked when an item has been clicked and held.
     */
    public OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    /**
     * Defines the choice behavior for the list.
     *
     * @param choiceMode the choice mode.
     */
    public void setChoiceMode(int choiceMode) {
        mChoiceMode = choiceMode;
        clearChoices();
    }

    /**
     * Returns the choice mode.
     *
     * @return the choice mode.
     */
    public int getChoiceMode() {
        return mChoiceMode;
    }

    /**
     * Clears any choices previously set.
     */
    public void clearChoices() {
        mCheckedItems.clear();
        if (mActionMode != null && mMultiChoiceModeListener != null) {
            mActionMode.finish();
        }
        updateChildrenStatus();
    }

    /**
     * Sets the checked state of the specified position.
     *
     * @param position the position.
     * @param value the state.
     */
    public void setItemChecked(int position, boolean value) {
        final long id = mAdapter != null ? mAdapter.getItemId(position) : NO_ID;
        if (mChoiceMode == CHOICE_MODE_SINGLE) {
            if (value) {
                mCheckedItems.clear();
                mCheckedItems.put(position, id);
            } else {
                mCheckedItems.remove(position);
            }
        } else if (mChoiceMode == CHOICE_MODE_MULTIPLE) {
            if (value) {
                mCheckedItems.put(position, id);
            } else {
                mCheckedItems.remove(position);
            }
        } else if (mChoiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
            if (value) {
                mCheckedItems.put(position, id);
            } else {
                mCheckedItems.remove(position);
            }
            if (mMultiChoiceModeListener != null) {
                if (mActionMode == null && mCheckedItems.size() > 0) {
                    mActionMode = startActionMode(this);
                }
                if (mActionMode != null) {
                    mMultiChoiceModeListener.onItemCheckedStateChanged(mActionMode, position, id, value);
                }
                if (mActionMode != null && mCheckedItems.size() == 0) {
                    mActionMode.finish();
                }
            }
        }
        updateChildrenStatus();
    }

    /**
     * Returns the checked state of the specified position.
     *
     * @param position the position.
     * @return the checked state of the specified position.
     */
    public boolean isItemChecked(int position) {
        return mCheckedItems.indexOfKey(position) >= 0;
    }

    /**
     * Returns the number of items currently selected.
     *
     * @return the number of items currently selected.
     */
    public int getCheckedItemCount() {
        return mCheckedItems.size();
    }

    /**
     * Returns the currently checked item.
     *
     * @return the currently checked item.
     */
    public int getCheckedItemPosition() {
        if (mCheckedItems.size() == 1) {
            return mCheckedItems.keyAt(0);
        }
        return NO_POSITION;
    }

    /**
     * Returns the set of checked items in the list.
     *
     * @return the set of checked items in the list.
     */
    public SparseBooleanArray getCheckedItemPositions() {
        SparseBooleanArray positions = new SparseBooleanArray(mCheckedItems.size());
        for (int i = 0; i < mCheckedItems.size(); i++) {
            positions.put(mCheckedItems.keyAt(i), true);
        }
        return positions;
    }

    /**
     * Returns the set of checked items ids.
     *
     * @return the set of checked items ids.
     */
    public long[] getCheckedItemIds() {
        long[] ids = new long[mCheckedItems.size()];
        for (int i = 0; i < mCheckedItems.size(); i++) {
            ids[i] = mCheckedItems.valueAt(i);
        }
        return ids;
    }

    /**
     * Sets the callback to be invoked when an item has been checked or unchecked.
     *
     * @param listener the callback to be invoked when an item has been checked or unchecked.
     */
    public void setMultiChoiceModeListener(MultiChoiceModeListener listener) {
        mMultiChoiceModeListener = listener;
    }

    /**
     * Returns the callback to be invoked when an item has been checked or unchecked.
     *
     * @return the callback to be invoked when an item has been checked or unchecked.
     */
    public MultiChoiceModeListener getMultiChoiceModeListener() {
        return mMultiChoiceModeListener;
    }

    /**
     * Updates the visibility of the empty view.
     */
    private void updateEmptyStatus() {
        if (mEmptyView != null) {
            if (mChildrenCount > 0) {
                mEmptyView.setVisibility(View.GONE);
            } else {
                mEmptyView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Updates the checked state of the children.
     */
    private void updateChildrenStatus() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            updateChildStatus(child);
        }
    }

    /**
     * Updates the checked state of the child.
     *
     * @param child the child.
     */
    private void updateChildStatus(View child) {
        final int position = getChildAdapterPosition(child);
        final boolean checked = isItemChecked(position);
        if (child instanceof Checkable) {
            ((Checkable) child).setChecked(checked);
        } else {
            child.setActivated(checked);
        }
    }

    /**
     * Observer class for watching changes to the adapter.
     */
    private class RecyclerViewAdapterDataObserver extends AdapterDataObserver {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            for (int i = mCheckedItems.size() - 1; i >= 0; i--) {
                final int position = mCheckedItems.keyAt(i);
                final Long id = mCheckedItems.valueAt(i);
                if (position >= positionStart) {
                    mCheckedItems.removeAt(i);
                    mCheckedItems.put(position + itemCount, id);
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            for (int i = 0; i < mCheckedItems.size(); i++) {
                final int position = mCheckedItems.keyAt(i);
                final Long id = mCheckedItems.valueAt(i);
                if (position >= positionStart + itemCount) {
                    mCheckedItems.removeAt(i);
                    mCheckedItems.put(position - itemCount, id);
                } else if (position >= positionStart) {
                    mCheckedItems.removeAt(i--);
                    if (mActionMode != null && mMultiChoiceModeListener != null) {
                        mMultiChoiceModeListener.onItemCheckedStateChanged(mActionMode, position, id, false);
                        if (mCheckedItems.size() == 0) {
                            mActionMode.finish();
                        }
                    }
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            for (int i = 0; i < itemCount; i++) {
                final Long fromId = mCheckedItems.get(fromPosition + i);
                final Long toId = mCheckedItems.get(toPosition + i);
                if (fromId != null) {
                    mCheckedItems.put(toPosition + i, fromId);
                } else {
                    mCheckedItems.remove(toPosition + i);
                }
                if (toId != null) {
                    mCheckedItems.put(fromPosition + i, toId);
                } else {
                    mCheckedItems.remove(fromPosition + i);
                }
            }
        }

    }

}
