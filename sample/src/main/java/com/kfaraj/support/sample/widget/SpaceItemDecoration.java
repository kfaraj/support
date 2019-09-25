package com.kfaraj.support.sample.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.State;

/**
 * Adds space around each item.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mSpace;

    /**
     * Constructor.
     *
     * @param space the amount of space around each item, in pixels.
     */
    public SpaceItemDecoration(@Dimension int space) {
        mSpace = space;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
            @NonNull RecyclerView parent, @NonNull State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mSpace, mSpace, mSpace, mSpace);
    }

}
