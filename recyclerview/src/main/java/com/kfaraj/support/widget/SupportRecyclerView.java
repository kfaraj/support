package com.kfaraj.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.Checkable;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.ParcelCompat;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.widget.RecyclerView;

import com.kfaraj.support.recyclerview.R;
import com.kfaraj.support.util.SparseLongArray;

/**
 * Adds support for empty view, item click and choice mode.
 * <ul>
 * <li>{@link #setEmptyView(View)}</li>
 * <li>{@link #getEmptyView()}</li>
 * <li>{@link #setOnItemClickListener(OnItemClickListener)}</li>
 * <li>{@link #getOnItemClickListener()}</li>
 * <li>{@link #setOnItemLongClickListener(OnItemLongClickListener)}</li>
 * <li>{@link #getOnItemLongClickListener()}</li>
 * <li>{@link #setChoiceMode(int)}</li>
 * <li>{@link #getChoiceMode()}</li>
 * <li>{@link #setMultiChoiceModeListener(MultiChoiceModeListener)}</li>
 * <li>{@link #getMultiChoiceModeListener()}</li>
 * <li>{@link #clearChoices()}</li>
 * <li>{@link #setItemChecked(int, boolean)}</li>
 * <li>{@link #isItemChecked(int)}</li>
 * <li>{@link #getCheckedItemCount()}</li>
 * <li>{@link #getCheckedItemPosition()}</li>
 * <li>{@link #getCheckedItemPositions()}</li>
 * <li>{@link #getCheckedItemIds()}</li>
 * <li>{@link R.attr#choiceMode}</li>
 * </ul>
 */
public class SupportRecyclerView extends RecyclerView
        implements OnClickListener, OnLongClickListener, ActionMode.Callback {

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
        void onItemClick(@NonNull SupportRecyclerView parent,
                @NonNull View view, int position, long id);

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
         * @return {@code true} if the event was consumed, {@code false} otherwise.
         */
        boolean onItemLongClick(@NonNull SupportRecyclerView parent,
                @NonNull View view, int position, long id);

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
         * @param id the item ID.
         * @param checked whether the item is now checked.
         */
        void onItemCheckedStateChanged(@NonNull ActionMode mode,
                int position, long id, boolean checked);

    }

    /**
     * The list does not indicate choices.
     */
    public static final int CHOICE_MODE_NONE = AbsListView.CHOICE_MODE_NONE;

    /**
     * The list allows up to one choice.
     */
    public static final int CHOICE_MODE_SINGLE = AbsListView.CHOICE_MODE_SINGLE;

    /**
     * The list allows multiple choices.
     */
    public static final int CHOICE_MODE_MULTIPLE = AbsListView.CHOICE_MODE_MULTIPLE;

    /**
     * The list allows multiple choices in a modal selection mode.
     */
    public static final int CHOICE_MODE_MULTIPLE_MODAL = AbsListView.CHOICE_MODE_MULTIPLE_MODAL;

    private final AdapterDataObserver mObserver = new RecyclerViewAdapterDataObserver();

    private Adapter<?> mAdapter;
    private int mChildrenCount;
    private View mEmptyView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private int mChoiceMode = CHOICE_MODE_NONE;
    private SparseLongArray mCheckedItems = new SparseLongArray();
    private MultiChoiceModeListener mMultiChoiceModeListener;
    private ActionMode mActionMode;

    /**
     * Constructor.
     *
     * @param context the context.
     */
    public SupportRecyclerView(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * Constructor.
     *
     * @param context the context.
     * @param attrs the attributes.
     */
    public SupportRecyclerView(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * Constructor.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style attribute.
     */
    public SupportRecyclerView(@NonNull Context context,
            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Common code for different constructors.
     *
     * @param context the context.
     * @param attrs the attributes.
     * @param defStyleAttr the default style attribute.
     */
    @SuppressWarnings("resource")
    private void init(@NonNull Context context,
            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SupportRecyclerView, defStyleAttr, 0);
        try {
            if (a.hasValue(R.styleable.SupportRecyclerView_choiceMode)) {
                final int choiceMode = a.getInt(
                        R.styleable.SupportRecyclerView_choiceMode, 0);
                setChoiceMode(choiceMode);
            }
        } finally {
            a.recycle();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        final SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.choiceMode = mChoiceMode;
        savedState.checkedItems = mCheckedItems;
        savedState.actionMode = mActionMode != null;
        return savedState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof final SavedState savedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        super.onRestoreInstanceState(savedState.getSuperState());
        mChoiceMode = savedState.choiceMode;
        mCheckedItems = savedState.checkedItems;
        mActionMode = savedState.actionMode
                && mMultiChoiceModeListener != null ? startActionMode(this) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swapAdapter(@Nullable Adapter adapter, boolean removeAndRecycleExistingViews) {
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
    public void setAdapter(@Nullable Adapter adapter) {
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
    public void onChildAttachedToWindow(@NonNull View child) {
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
    public void onChildDetachedFromWindow(@NonNull View child) {
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
            }
            return true;
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
    public void setEmptyView(@Nullable View emptyView) {
        mEmptyView = emptyView;
        updateEmptyStatus();
    }

    /**
     * Returns the view to show when the adapter is empty.
     *
     * @return the view to show when the adapter is empty.
     */
    @Nullable
    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * Sets the callback to be invoked when an item has been clicked.
     *
     * @param listener the callback to be invoked when an item has been clicked.
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * Returns the callback to be invoked when an item has been clicked.
     *
     * @return the callback to be invoked when an item has been clicked.
     */
    @Nullable
    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    /**
     * Sets the callback to be invoked when an item has been clicked and held.
     *
     * @param listener the callback to be invoked when an item has been clicked and held.
     */
    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    /**
     * Returns the callback to be invoked when an item has been clicked and held.
     *
     * @return the callback to be invoked when an item has been clicked and held.
     */
    @Nullable
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
     * Sets the callback to be invoked when an item has been checked or unchecked.
     *
     * @param listener the callback to be invoked when an item has been checked or unchecked.
     */
    public void setMultiChoiceModeListener(@Nullable MultiChoiceModeListener listener) {
        mMultiChoiceModeListener = listener;
    }

    /**
     * Returns the callback to be invoked when an item has been checked or unchecked.
     *
     * @return the callback to be invoked when an item has been checked or unchecked.
     */
    @Nullable
    public MultiChoiceModeListener getMultiChoiceModeListener() {
        return mMultiChoiceModeListener;
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
                    mMultiChoiceModeListener.onItemCheckedStateChanged(mActionMode,
                            position, id, value);
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
    @NonNull
    public SparseBooleanArray getCheckedItemPositions() {
        final int count = mCheckedItems.size();
        final SparseBooleanArray positions = new SparseBooleanArray(count);
        for (int i = 0; i < count; i++) {
            positions.put(mCheckedItems.keyAt(i), true);
        }
        return positions;
    }

    /**
     * Returns the set of checked items IDs.
     *
     * @return the set of checked items IDs.
     */
    @NonNull
    public long[] getCheckedItemIds() {
        final int count = mCheckedItems.size();
        final long[] ids = new long[count];
        for (int i = 0; i < count; i++) {
            ids[i] = mCheckedItems.valueAt(i);
        }
        return ids;
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
            final View child = getChildAt(i);
            updateChildStatus(child);
        }
    }

    /**
     * Updates the checked state of the child.
     *
     * @param child the child.
     */
    private void updateChildStatus(@NonNull View child) {
        final int position = getChildAdapterPosition(child);
        final boolean checked = isItemChecked(position);
        if (child instanceof Checkable) {
            ((Checkable) child).setChecked(checked);
        } else {
            child.setActivated(checked);
        }
    }

    /**
     * Returns the adapter position of the item represented by this ID.
     *
     * @param id the item ID.
     * @return the adapter position of the item represented by this ID.
     */
    private int getAdapterPosition(long id) {
        if (mAdapter != null && mAdapter.hasStableIds()) {
            final int count = mAdapter.getItemCount();
            for (int i = 0; i < count; i++) {
                if (mAdapter.getItemId(i) == id) {
                    return i;
                }
            }
        }
        return NO_POSITION;
    }

    /**
     * Observer class for watching changes to the adapter.
     */
    private class RecyclerViewAdapterDataObserver extends AdapterDataObserver {

        /**
         * Constructor.
         */
        RecyclerViewAdapterDataObserver() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onChanged() {
            super.onChanged();
            if (mAdapter != null && mAdapter.hasStableIds()) {
                for (int i = 0; i < mCheckedItems.size(); i++) {
                    final int position = mCheckedItems.keyAt(i);
                    final Long id = mCheckedItems.valueAt(i);
                    final int newPosition = getAdapterPosition(id);
                    final Long newId = mCheckedItems.get(newPosition);
                    if (newPosition != position) {
                        if (newPosition != NO_POSITION) {
                            mCheckedItems.put(newPosition, id);
                            i = mCheckedItems.indexOfKey(position);
                            if (newId != null) {
                                mCheckedItems.setValueAt(i--, newId);
                            } else {
                                mCheckedItems.removeAt(i--);
                            }
                        } else {
                            mCheckedItems.removeAt(i--);
                            if (mActionMode != null && mMultiChoiceModeListener != null) {
                                mMultiChoiceModeListener.onItemCheckedStateChanged(mActionMode,
                                        position, id, false);
                                if (mCheckedItems.size() == 0) {
                                    mActionMode.finish();
                                }
                            }
                        }
                    }
                }
            }
        }

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
                        mMultiChoiceModeListener.onItemCheckedStateChanged(mActionMode,
                                position, id, false);
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

    /**
     * Contains the state of this view.
     */
    private static class SavedState extends AbsSavedState {

        /**
         * The creator.
         */
        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<>() {
            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return new SavedState(source, loader);
            }

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source, null);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        int choiceMode;
        SparseLongArray checkedItems;
        boolean actionMode;

        /**
         * Constructor.
         *
         * @param superState the state of the superclass of this view.
         */
        SavedState(@Nullable Parcelable superState) {
            super(superState != null ? superState : EMPTY_STATE);
        }

        /**
         * Constructor.
         *
         * @param source the source.
         * @param loader the class loader.
         */
        private SavedState(@NonNull Parcel source, @Nullable ClassLoader loader) {
            super(source, loader);
            choiceMode = source.readInt();
            checkedItems = SparseLongArray.CREATOR.createFromParcel(source);
            actionMode = ParcelCompat.readBoolean(source);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(choiceMode);
            checkedItems.writeToParcel(dest, flags);
            ParcelCompat.writeBoolean(dest, actionMode);
        }

    }

}
