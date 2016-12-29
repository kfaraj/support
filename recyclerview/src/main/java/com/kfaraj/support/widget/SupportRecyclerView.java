package com.kfaraj.support.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

/**
 * Adds support for {@link RecyclerView}.
 * <ul>
 * <li>{@link #setEmptyView(View)}</li>
 * <li>{@link #getEmptyView()}</li>
 * <li>{@link #setOnItemClickListener(OnItemClickListener)}</li>
 * <li>{@link #getOnItemClickListener()}</li>
 * <li>{@link #setOnItemLongClickListener(OnItemLongClickListener)}</li>
 * <li>{@link #getOnItemLongClickListener()}</li>
 * </ul>
 */
@SuppressWarnings("unused")
public class SupportRecyclerView extends RecyclerView implements OnClickListener, OnLongClickListener {

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

    private View mEmptyView;
    private int mChildrenCount;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * Constructor
     *
     * @param context the context.
     */
    public SupportRecyclerView(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context the context.
     * @param attrs the attributes.
     */
    public SupportRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChildAttachedToWindow(View child) {
        super.onChildAttachedToWindow(child);
        mChildrenCount++;
        updateEmptyStatus();
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
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(this, v, position, id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean onLongClick(View v) {
        final int position = getChildAdapterPosition(v);
        final long id = getChildItemId(v);
        if (position == NO_POSITION) {
            return false;
        }
        if (mOnItemLongClickListener != null) {
            return mOnItemLongClickListener.onItemLongClick(this, v, position, id);
        }
        return false;
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

}
