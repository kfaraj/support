package com.kfaraj.support.recyclerview.widget;

import android.content.Context;
import android.os.Bundle;
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

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.widget.RecyclerView;

import com.kfaraj.support.recyclerview.R;

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
    public interface OnItemClickListener
            extends RecyclerViewHelper.OnItemClickListener<SupportRecyclerView> {

        /**
         * Called when an item has been clicked.
         *
         * @param parent the parent.
         * @param view the item view.
         * @param position the item position.
         * @param id the item ID.
         */
        @Override
        void onItemClick(@NonNull SupportRecyclerView parent,
                @NonNull View view, int position, long id);

    }

    /**
     * Callback to be invoked when an item has been clicked and held.
     */
    public interface OnItemLongClickListener
            extends RecyclerViewHelper.OnItemLongClickListener<SupportRecyclerView> {

        /**
         * Called when an item has been clicked and held.
         *
         * @param parent the parent.
         * @param view the item view.
         * @param position the item position.
         * @param id the item ID.
         * @return {@code true} if the event was consumed, {@code false} otherwise.
         */
        @Override
        boolean onItemLongClick(@NonNull SupportRecyclerView parent,
                @NonNull View view, int position, long id);

    }

    /**
     * Callback to be invoked when an item has been checked or unchecked.
     */
    public interface MultiChoiceModeListener
            extends RecyclerViewHelper.MultiChoiceModeListener<SupportRecyclerView> {

        /**
         * Called when an item has been checked or unchecked.
         *
         * @param mode the action mode.
         * @param position the item position.
         * @param id the item ID.
         * @param checked whether the item is now checked.
         */
        @Override
        void onItemCheckedStateChanged(@NonNull ActionMode mode,
                int position, long id, boolean checked);

    }

    /**
     * The list does not indicate choices.
     */
    public static final int CHOICE_MODE_NONE = RecyclerViewHelper.CHOICE_MODE_NONE;

    /**
     * The list allows up to one choice.
     */
    public static final int CHOICE_MODE_SINGLE = RecyclerViewHelper.CHOICE_MODE_SINGLE;

    /**
     * The list allows multiple choices.
     */
    public static final int CHOICE_MODE_MULTIPLE = RecyclerViewHelper.CHOICE_MODE_MULTIPLE;

    /**
     * The list allows multiple choices in a modal selection mode.
     */
    public static final int CHOICE_MODE_MULTIPLE_MODAL = RecyclerViewHelper.CHOICE_MODE_MULTIPLE_MODAL;

    private RecyclerViewHelper<SupportRecyclerView> mHelper;

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
    private void init(@NonNull Context context,
            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        mHelper = new RecyclerViewHelper<>(this);
        mHelper.loadFromAttributes(context, attrs, defStyleAttr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        final SavedState savedState = new SavedState(super.onSaveInstanceState());
        mHelper.onSaveInstanceState(savedState.state);
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
        mHelper.onRestoreInstanceState(savedState.state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swapAdapter(@Nullable Adapter adapter, boolean removeAndRecycleExistingViews) {
        super.swapAdapter(adapter, removeAndRecycleExistingViews);
        mHelper.swapAdapter(adapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        mHelper.setAdapter(adapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChildAttachedToWindow(@NonNull View child) {
        super.onChildAttachedToWindow(child);
        mHelper.onChildViewAttachedToWindow(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChildDetachedFromWindow(@NonNull View child) {
        super.onChildDetachedFromWindow(child);
        mHelper.onChildViewDetachedFromWindow(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        mHelper.onClick(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onLongClick(View v) {
        return mHelper.onLongClick(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return mHelper.onCreateActionMode(mode, menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return mHelper.onPrepareActionMode(mode, menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return mHelper.onActionItemClicked(mode, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mHelper.onDestroyActionMode(mode);
    }

    /**
     * Sets the view to show when the adapter is empty.
     *
     * @param emptyView the view to show when the adapter is empty.
     */
    public void setEmptyView(@Nullable View emptyView) {
        mHelper.setEmptyView(emptyView);
    }

    /**
     * Returns the view to show when the adapter is empty.
     *
     * @return the view to show when the adapter is empty.
     */
    @Nullable
    public View getEmptyView() {
        return mHelper.getEmptyView();
    }

    /**
     * Sets the callback to be invoked when an item has been clicked.
     *
     * @param listener the callback to be invoked when an item has been clicked.
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mHelper.setOnItemClickListener(listener);
    }

    /**
     * Returns the callback to be invoked when an item has been clicked.
     *
     * @return the callback to be invoked when an item has been clicked.
     */
    @Nullable
    public OnItemClickListener getOnItemClickListener() {
        return (OnItemClickListener) mHelper.getOnItemClickListener();
    }

    /**
     * Sets the callback to be invoked when an item has been clicked and held.
     *
     * @param listener the callback to be invoked when an item has been clicked and held.
     */
    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener listener) {
        mHelper.setOnItemLongClickListener(listener);
    }

    /**
     * Returns the callback to be invoked when an item has been clicked and held.
     *
     * @return the callback to be invoked when an item has been clicked and held.
     */
    @Nullable
    public OnItemLongClickListener getOnItemLongClickListener() {
        return (OnItemLongClickListener) mHelper.getOnItemLongClickListener();
    }

    /**
     * Defines the choice behavior for the list.
     *
     * @param choiceMode the choice mode.
     */
    public void setChoiceMode(int choiceMode) {
        mHelper.setChoiceMode(choiceMode);
    }

    /**
     * Returns the choice mode.
     *
     * @return the choice mode.
     */
    public int getChoiceMode() {
        return mHelper.getChoiceMode();
    }

    /**
     * Sets the callback to be invoked when an item has been checked or unchecked.
     *
     * @param listener the callback to be invoked when an item has been checked or unchecked.
     */
    public void setMultiChoiceModeListener(@Nullable MultiChoiceModeListener listener) {
        mHelper.setMultiChoiceModeListener(listener);
    }

    /**
     * Returns the callback to be invoked when an item has been checked or unchecked.
     *
     * @return the callback to be invoked when an item has been checked or unchecked.
     */
    @Nullable
    public MultiChoiceModeListener getMultiChoiceModeListener() {
        return (MultiChoiceModeListener) mHelper.getMultiChoiceModeListener();
    }

    /**
     * Clears any choices previously set.
     */
    public void clearChoices() {
        mHelper.clearChoices();
    }

    /**
     * Sets the checked state of the specified position.
     *
     * @param position the position.
     * @param value the state.
     */
    public void setItemChecked(int position, boolean value) {
        mHelper.setItemChecked(position, value);
    }

    /**
     * Returns the checked state of the specified position.
     *
     * @param position the position.
     * @return the checked state of the specified position.
     */
    public boolean isItemChecked(int position) {
        return mHelper.isItemChecked(position);
    }

    /**
     * Returns the number of items currently selected.
     *
     * @return the number of items currently selected.
     */
    public int getCheckedItemCount() {
        return mHelper.getCheckedItemCount();
    }

    /**
     * Returns the currently checked item.
     *
     * @return the currently checked item.
     */
    public int getCheckedItemPosition() {
        return mHelper.getCheckedItemPosition();
    }

    /**
     * Returns the set of checked items in the list.
     *
     * @return the set of checked items in the list.
     */
    @NonNull
    public SparseBooleanArray getCheckedItemPositions() {
        return mHelper.getCheckedItemPositions();
    }

    /**
     * Returns the set of checked items IDs.
     *
     * @return the set of checked items IDs.
     */
    @NonNull
    public long[] getCheckedItemIds() {
        return mHelper.getCheckedItemIds();
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

        final Bundle state;

        /**
         * Constructor.
         *
         * @param superState the state of the superclass of this view.
         */
        SavedState(@Nullable Parcelable superState) {
            super(superState != null ? superState : EMPTY_STATE);
            state = new Bundle();
        }

        /**
         * Constructor.
         *
         * @param source the source.
         * @param loader the class loader.
         */
        private SavedState(@NonNull Parcel source, @Nullable ClassLoader loader) {
            super(source, loader);
            state = source.readBundle(loader);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeBundle(state);
        }

    }

}
