package com.kfaraj.support.widget;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.InstrumentationRegistry;
import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;

import com.kfaraj.support.widget.SupportRecyclerView.MultiChoiceModeListener;
import com.kfaraj.support.widget.SupportRecyclerView.OnItemClickListener;
import com.kfaraj.support.widget.SupportRecyclerView.OnItemLongClickListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

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
    private MockMultiChoiceModeListener mMultiChoiceModeListener;

    @Before
    public void setUp() {
        final Context context = InstrumentationRegistry.getContext();
        mRecyclerView = new SupportRecyclerView(context);
        mEmptyView = new View(context);
        mAdapter = new MockAdapter();
        mOnItemClickListener = new MockOnItemClickListener();
        mOnItemLongClickListener = new MockOnItemLongClickListener();
        mMultiChoiceModeListener = new MockMultiChoiceModeListener();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setOnItemLongClickListener(mOnItemLongClickListener);
        mRecyclerView.setMultiChoiceModeListener(mMultiChoiceModeListener);
    }

    @Test
    public void testEmptyView() {
        assertEquals(mEmptyView, mRecyclerView.getEmptyView());
        populateAdapter(0);
        assertEquals(View.VISIBLE, mEmptyView.getVisibility());
        populateAdapter(1);
        assertEquals(View.GONE, mEmptyView.getVisibility());
    }

    @Test
    public void testOnItemClickListener() {
        assertEquals(mOnItemClickListener, mRecyclerView.getOnItemClickListener());
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performClick();
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testOnItemLongClickListener() {
        assertEquals(mOnItemLongClickListener, mRecyclerView.getOnItemLongClickListener());
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performLongClick();
        assertTrue(mOnItemLongClickListener.called);
    }

    @Test
    public void testChoiceMode() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_NONE);
        assertEquals(SupportRecyclerView.CHOICE_MODE_NONE, mRecyclerView.getChoiceMode());
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_SINGLE);
        assertEquals(SupportRecyclerView.CHOICE_MODE_SINGLE, mRecyclerView.getChoiceMode());
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        assertEquals(SupportRecyclerView.CHOICE_MODE_MULTIPLE, mRecyclerView.getChoiceMode());
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL);
        assertEquals(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL, mRecyclerView.getChoiceMode());
    }

    @Test
    public void testItemClick_choiceModeNone() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_NONE);
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testItemClick_choiceModeSingle() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_SINGLE);
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performClick();
        assertTrue(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testItemClick_choiceModeMultiple() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performClick();
        assertTrue(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testItemClick_choiceModeMultipleModal() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL);
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemClickListener.called);
    }

    @Test
    public void testItemLongClick_choiceModeNone() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_NONE);
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performLongClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemLongClickListener.called);
    }

    @Test
    public void testItemLongClick_choiceModeSingle() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_SINGLE);
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performLongClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemLongClickListener.called);
    }

    @Test
    public void testItemLongClick_choiceModeMultiple() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performLongClick();
        assertFalse(mRecyclerView.isItemChecked(0));
        assertTrue(mOnItemLongClickListener.called);
    }

    @Test
    public void testItemLongClick_choiceModeMultipleModal() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL);
        populateAdapter(1);
        mRecyclerView.getChildAt(0).performLongClick();
        assertTrue(mRecyclerView.isItemChecked(0));
        assertFalse(mOnItemLongClickListener.called);
    }

    @Test
    public void testItemChecked_choiceModeNone() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_NONE);
        populateAdapter(1);
        mRecyclerView.setItemChecked(0, true);
        assertEquals(0, mRecyclerView.getCheckedItemCount());
    }

    @Test
    public void testItemChecked_choiceModeSingle() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_SINGLE);
        populateAdapter(2);
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
        populateAdapter(2);
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
        populateAdapter(2);
        mRecyclerView.setItemChecked(0, true);
        mRecyclerView.setItemChecked(1, true);
        assertEquals(2, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(0));
        assertTrue(mRecyclerView.isItemChecked(1));
        mRecyclerView.clearChoices();
        assertEquals(0, mRecyclerView.getCheckedItemCount());
    }

    @Test
    public void testItemChecked_adapterChange() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        populateAdapter(2);
        mRecyclerView.setItemChecked(0, false);
        mRecyclerView.setItemChecked(1, true);
        mAdapter.getItems().remove(0);
        mAdapter.getItems().add(0, new Object());
        Collections.swap(mAdapter.getItems(), 0, 1);
        mAdapter.notifyDataSetChanged();
        assertEquals(1, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(0));
    }

    @Test
    public void testItemChecked_adapterInsert() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        populateAdapter(1);
        mRecyclerView.setItemChecked(0, true);
        mAdapter.getItems().add(0, new Object());
        mAdapter.notifyItemInserted(0);
        assertEquals(1, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(1));
    }

    @Test
    public void testItemChecked_adapterRemove() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        populateAdapter(2);
        mRecyclerView.setItemChecked(0, false);
        mRecyclerView.setItemChecked(1, true);
        mAdapter.getItems().remove(0);
        mAdapter.notifyItemRemoved(0);
        assertEquals(1, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(0));
    }

    @Test
    public void testItemChecked_adapterMove() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        populateAdapter(2);
        mRecyclerView.setItemChecked(0, false);
        mRecyclerView.setItemChecked(1, true);
        Collections.swap(mAdapter.getItems(), 0, 1);
        mAdapter.notifyItemMoved(0, 1);
        assertEquals(1, mRecyclerView.getCheckedItemCount());
        assertTrue(mRecyclerView.isItemChecked(0));
    }

    @Test
    public void testCheckedItemPositions() {
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE);
        populateAdapter(10);
        for (int i = 0; i < 10; i++) {
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
        populateAdapter(10);
        for (int i = 0; i < 10; i++) {
            mRecyclerView.setItemChecked(i, i % 2 == 0);
        }
        long[] ids = mRecyclerView.getCheckedItemIds();
        assertEquals(mRecyclerView.getCheckedItemCount(), ids.length);
    }

    @Test
    public void testMultiChoiceModeListener() {
        assertEquals(mMultiChoiceModeListener, mRecyclerView.getMultiChoiceModeListener());
    }

    private void populateAdapter(int count) {
        mAdapter.getItems().clear();
        for (int i = 0; i < count; i++) {
            mAdapter.getItems().add(new Object());
        }
        mAdapter.notifyDataSetChanged();
        requestLayout();
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

        ArrayList<Object> getItems() {
            return mItems;
        }

        @NonNull
        @Override
        public MockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = new View(parent.getContext());
            return new MockViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MockViewHolder holder, int position) {
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
        public void onItemClick(SupportRecyclerView parent,
                View view, int position, long id) {
            this.called = true;
        }

    }

    private static class MockOnItemLongClickListener implements OnItemLongClickListener {

        boolean called = false;

        @Override
        public boolean onItemLongClick(SupportRecyclerView parent,
                View view, int position, long id) {
            this.called = true;
            return true;
        }

    }

    private static class MockMultiChoiceModeListener implements MultiChoiceModeListener {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode,
                int position, long id, boolean checked) {
        }

    }

}
