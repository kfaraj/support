package com.kfaraj.support.widget;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import com.kfaraj.support.widget.SupportRecyclerView.OnItemClickListener;
import com.kfaraj.support.widget.SupportRecyclerView.OnItemLongClickListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SupportRecyclerViewTest {

    private SupportRecyclerView mRecyclerView;
    private View mEmptyView;
    private MockAdapter mAdapter;
    private MockOnItemClickListener mOnItemClickListener;
    private MockOnItemLongClickListener mOnItemLongClickListener;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getContext();
        mRecyclerView = new SupportRecyclerView(context);
        mEmptyView = new View(context);
        mAdapter = new MockAdapter();
        mOnItemClickListener = new MockOnItemClickListener();
        mOnItemLongClickListener = new MockOnItemLongClickListener();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setOnItemLongClickListener(mOnItemLongClickListener);
    }

    @Test
    public void testEmptyView() {
        requestLayout();
        assertEquals(View.VISIBLE, mEmptyView.getVisibility());
        mAdapter.insert(0);
        requestLayout();
        assertEquals(View.GONE, mEmptyView.getVisibility());
    }

    @Test
    public void testItemClick_choiceModeNone() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_NONE);
        mAdapter.insert(0);
        requestLayout();
        mRecyclerView.getChildAt(0).performClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testItemClick_choiceModeSingle() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_SINGLE);
        mAdapter.insert(0);
        requestLayout();
        mRecyclerView.getChildAt(0).performClick();
        assertTrue(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testItemClick_choiceModeMultiple() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        mAdapter.insert(0);
        requestLayout();
        mRecyclerView.getChildAt(0).performClick();
        assertTrue(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testItemClick_choiceModeMultipleModal() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL);
        mAdapter.insert(0);
        requestLayout();
        mRecyclerView.getChildAt(0).performClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testItemLongClick_choiceModeNone() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_NONE);
        mAdapter.insert(0);
        requestLayout();
        mRecyclerView.getChildAt(0).performLongClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemLongClickListener.called);
    }

    @Test
    public void testItemLongClick_choiceModeSingle() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_SINGLE);
        mAdapter.insert(0);
        requestLayout();
        mRecyclerView.getChildAt(0).performLongClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemLongClickListener.called);
    }

    @Test
    public void testItemLongClick_choiceModeMultiple() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        mAdapter.insert(0);
        requestLayout();
        mRecyclerView.getChildAt(0).performLongClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemLongClickListener.called);
    }

    @Test
    public void testItemLongClick_choiceModeMultipleModal() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL);
        mAdapter.insert(0);
        requestLayout();
        mRecyclerView.getChildAt(0).performLongClick();
        assertTrue(mRecyclerView.isItemChecked(0));
        assertFalse(mOnItemLongClickListener.called);
    }

    @Test
    public void testItemChecked_choiceModeNone() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_NONE);
        mAdapter.insert(0);
        mRecyclerView.setItemChecked(0, true);
        assertEquals(0, mRecyclerView.getCheckedItemCount());
    }

    @Test
    public void testItemChecked_choiceModeSingle() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_SINGLE);
        mAdapter.insert(0);
        mAdapter.insert(1);
        mRecyclerView.setItemChecked(0, true);
        mRecyclerView.setItemChecked(1, true);
        assertEquals(1, mRecyclerView.getCheckedItemCount());
        assertEquals(1, mRecyclerView.getCheckedItemPosition());
        mRecyclerView.clearChoices();
        assertEquals(0, mRecyclerView.getCheckedItemCount());
    }

    @Test
    public void testItemChecked_choiceModeMultiple() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        mAdapter.insert(0);
        mAdapter.insert(1);
        mRecyclerView.setItemChecked(0, true);
        mRecyclerView.setItemChecked(1, true);
        assertEquals(2, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(0));
        assertTrue(mRecyclerView.isItemChecked(1));
        mRecyclerView.clearChoices();
        assertEquals(0, mRecyclerView.getCheckedItemCount());
    }

    @Test
    public void testItemChecked_choiceModeMultipleModal() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL);
        mAdapter.insert(0);
        mAdapter.insert(1);
        mRecyclerView.setItemChecked(0, true);
        mRecyclerView.setItemChecked(1, true);
        assertEquals(2, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(0));
        assertTrue(mRecyclerView.isItemChecked(1));
        mRecyclerView.clearChoices();
        assertEquals(0, mRecyclerView.getCheckedItemCount());
    }

    @Test
    public void testItemChecked_adapterInsert() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        mAdapter.insert(0);
        mRecyclerView.setItemChecked(0, true);
        mAdapter.insert(0);
        assertEquals(1, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(1));
    }

    @Test
    public void testItemChecked_adapterRemove() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        mAdapter.insert(0);
        mAdapter.insert(1);
        mRecyclerView.setItemChecked(0, false);
        mRecyclerView.setItemChecked(1, true);
        mAdapter.remove(0);
        assertEquals(1, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(0));
    }

    @Test
    public void testItemChecked_adapterMove() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        mAdapter.insert(0);
        mAdapter.insert(1);
        mRecyclerView.setItemChecked(0, false);
        mRecyclerView.setItemChecked(1, true);
        mAdapter.move(0, 1);
        assertEquals(1, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(0));
    }

    @Test
    public void testCheckedItemPositions() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        for (int i = 0; i < 10; i++) {
            mAdapter.insert(i);
            mRecyclerView.setItemChecked(i, i % 2 == 0);
        }
        SparseBooleanArray positions = mRecyclerView.getCheckedItemPositions();
        for (int i = 0; i < positions.size(); i++) {
            int position = positions.keyAt(i);
            boolean checked = positions.valueAt(i);
            assertEquals(mRecyclerView.isItemChecked(position), checked);
        }
    }

    @Test
    public void testCheckedItemIds() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        for (int i = 0; i < 10; i++) {
            mAdapter.insert(i);
            mRecyclerView.setItemChecked(i, i % 2 == 0);
        }
        long[] ids = mRecyclerView.getCheckedItemIds();
        assertEquals(mRecyclerView.getCheckedItemCount(), ids.length);
    }

    private void requestLayout() {
        final int widthMeasureSpec = MeasureSpec.makeMeasureSpec(320, MeasureSpec.EXACTLY);
        final int heightMeasureSpec = MeasureSpec.makeMeasureSpec(320, MeasureSpec.EXACTLY);
        mRecyclerView.measure(widthMeasureSpec, heightMeasureSpec);
        mRecyclerView.layout(0, 0, 320, 320);
    }

    private static class MockAdapter extends RecyclerView.Adapter<MockViewHolder> {

        private final ArrayList<Object> mItems = new ArrayList<>();

        MockAdapter() {
            setHasStableIds(true);
        }

        void insert(int position) {
            mItems.add(position, new Object());
            notifyItemInserted(position);
        }

        void remove(int position) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }

        void move(int fromPosition, int toPosition) {
            mItems.add(toPosition, mItems.remove(fromPosition));
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public MockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = new View(parent.getContext());
            return new MockViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MockViewHolder holder, int position) {
        }

        @Override
        public long getItemId(int position) {
            return mItems.get(position).hashCode();
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

    }

    private static class MockViewHolder extends RecyclerView.ViewHolder {

        MockViewHolder(View itemView) {
            super(itemView);
        }

    }

    private static class MockOnItemClickListener implements OnItemClickListener {

        boolean called = false;

        @Override
        public void onItemClick(SupportRecyclerView parent, View view, int position, long id) {
            this.called = true;
        }

    }

    private static class MockOnItemLongClickListener implements OnItemLongClickListener {

        boolean called = false;

        @Override
        public boolean onItemLongClick(SupportRecyclerView parent, View view, int position, long id) {
            this.called = true;
            return true;
        }

    }

}
