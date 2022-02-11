package com.kfaraj.support.widget

import android.content.Context
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.kfaraj.support.widget.SupportRecyclerView.MultiChoiceModeListener
import com.kfaraj.support.widget.SupportRecyclerView.OnItemClickListener
import com.kfaraj.support.widget.SupportRecyclerView.OnItemLongClickListener
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@SmallTest
class SupportRecyclerViewTest {

    private lateinit var recyclerView: SupportRecyclerView
    private lateinit var emptyView: View
    private lateinit var adapter: MockAdapter
    private lateinit var onItemClickListener: MockOnItemClickListener
    private lateinit var onItemLongClickListener: MockOnItemLongClickListener
    private lateinit var multiChoiceModeListener: MockMultiChoiceModeListener

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        recyclerView = SupportRecyclerView(context)
        emptyView = View(context)
        adapter = MockAdapter(context)
        onItemClickListener = MockOnItemClickListener()
        onItemLongClickListener = MockOnItemLongClickListener()
        multiChoiceModeListener = MockMultiChoiceModeListener()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.emptyView = emptyView
        recyclerView.onItemClickListener = onItemClickListener
        recyclerView.onItemLongClickListener = onItemLongClickListener
        recyclerView.multiChoiceModeListener = multiChoiceModeListener
    }

    @Test
    fun testEmptyView() {
        assertEquals(emptyView, recyclerView.emptyView)
        populateAdapter(0)
        assertEquals(View.VISIBLE, emptyView.visibility)
        populateAdapter(1)
        assertEquals(View.GONE, emptyView.visibility)
    }

    @Test
    fun testOnItemClickListener() {
        assertEquals(onItemClickListener, recyclerView.onItemClickListener)
        populateAdapter(1)
        recyclerView.getChildAt(0).performClick()
        assertTrue(onItemClickListener.called)
    }

    @Test
    fun testOnItemLongClickListener() {
        assertEquals(onItemLongClickListener, recyclerView.onItemLongClickListener)
        populateAdapter(1)
        recyclerView.getChildAt(0).performLongClick()
        assertTrue(onItemLongClickListener.called)
    }

    @Test
    fun testChoiceMode() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_NONE
        assertEquals(SupportRecyclerView.CHOICE_MODE_NONE, recyclerView.choiceMode)
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_SINGLE
        assertEquals(SupportRecyclerView.CHOICE_MODE_SINGLE, recyclerView.choiceMode)
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        assertEquals(SupportRecyclerView.CHOICE_MODE_MULTIPLE, recyclerView.choiceMode)
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL
        assertEquals(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL, recyclerView.choiceMode)
    }

    @Test
    fun testMultiChoiceModeListener() {
        assertEquals(multiChoiceModeListener, recyclerView.multiChoiceModeListener)
    }

    @Test
    fun testItemClick_choiceModeNone() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_NONE
        populateAdapter(1)
        recyclerView.getChildAt(0).performClick()
        assertFalse(recyclerView.isItemChecked(0))
        assertTrue(onItemClickListener.called)
    }

    @Test
    fun testItemClick_choiceModeSingle() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_SINGLE
        populateAdapter(1)
        recyclerView.getChildAt(0).performClick()
        assertTrue(recyclerView.isItemChecked(0))
        assertTrue(onItemClickListener.called)
    }

    @Test
    fun testItemClick_choiceModeMultiple() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(1)
        recyclerView.getChildAt(0).performClick()
        assertTrue(recyclerView.isItemChecked(0))
        assertTrue(onItemClickListener.called)
    }

    @Test
    fun testItemClick_choiceModeMultipleModal() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL
        populateAdapter(1)
        recyclerView.getChildAt(0).performClick()
        assertFalse(recyclerView.isItemChecked(0))
        assertTrue(onItemClickListener.called)
    }

    @Test
    fun testItemLongClick_choiceModeNone() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_NONE
        populateAdapter(1)
        recyclerView.getChildAt(0).performLongClick()
        assertFalse(recyclerView.isItemChecked(0))
        assertTrue(onItemLongClickListener.called)
    }

    @Test
    fun testItemLongClick_choiceModeSingle() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_SINGLE
        populateAdapter(1)
        recyclerView.getChildAt(0).performLongClick()
        assertFalse(recyclerView.isItemChecked(0))
        assertTrue(onItemLongClickListener.called)
    }

    @Test
    fun testItemLongClick_choiceModeMultiple() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(1)
        recyclerView.getChildAt(0).performLongClick()
        assertFalse(recyclerView.isItemChecked(0))
        assertTrue(onItemLongClickListener.called)
    }

    @Test
    fun testItemLongClick_choiceModeMultipleModal() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL
        populateAdapter(1)
        recyclerView.getChildAt(0).performLongClick()
        assertTrue(recyclerView.isItemChecked(0))
        assertFalse(onItemLongClickListener.called)
    }

    @Test
    fun testItemChecked_choiceModeNone() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_NONE
        populateAdapter(1)
        recyclerView.setItemChecked(0, true)
        assertEquals(0, recyclerView.checkedItemCount)
    }

    @Test
    fun testItemChecked_choiceModeSingle() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_SINGLE
        populateAdapter(2)
        recyclerView.setItemChecked(0, true)
        recyclerView.setItemChecked(1, true)
        assertEquals(1, recyclerView.checkedItemCount)
        assertEquals(1, recyclerView.checkedItemPosition)
        recyclerView.clearChoices()
        assertEquals(0, recyclerView.checkedItemCount)
    }

    @Test
    fun testItemChecked_choiceModeMultiple() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(2)
        recyclerView.setItemChecked(0, true)
        recyclerView.setItemChecked(1, true)
        assertEquals(2, recyclerView.checkedItemCount)
        assertTrue(recyclerView.isItemChecked(0))
        assertTrue(recyclerView.isItemChecked(1))
        recyclerView.clearChoices()
        assertEquals(0, recyclerView.checkedItemCount)
    }

    @Test
    fun testItemChecked_choiceModeMultipleModal() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL
        populateAdapter(2)
        recyclerView.setItemChecked(0, true)
        recyclerView.setItemChecked(1, true)
        assertEquals(2, recyclerView.checkedItemCount)
        assertTrue(recyclerView.isItemChecked(0))
        assertTrue(recyclerView.isItemChecked(1))
        recyclerView.clearChoices()
        assertEquals(0, recyclerView.checkedItemCount)
    }

    @Test
    fun testItemChecked_adapterChange() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(2)
        recyclerView.setItemChecked(0, false)
        recyclerView.setItemChecked(1, true)
        adapter.items.removeAt(0)
        adapter.items.add(0, Any())
        Collections.swap(adapter.items, 0, 1)
        adapter.notifyDataSetChanged()
        assertEquals(1, recyclerView.checkedItemCount)
        assertTrue(recyclerView.isItemChecked(0))
    }

    @Test
    fun testItemChecked_adapterInsert() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(1)
        recyclerView.setItemChecked(0, true)
        adapter.items.add(0, Any())
        adapter.notifyItemInserted(0)
        assertEquals(1, recyclerView.checkedItemCount)
        assertTrue(recyclerView.isItemChecked(1))
    }

    @Test
    fun testItemChecked_adapterRemove() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(2)
        recyclerView.setItemChecked(0, false)
        recyclerView.setItemChecked(1, true)
        adapter.items.removeAt(0)
        adapter.notifyItemRemoved(0)
        assertEquals(1, recyclerView.checkedItemCount)
        assertTrue(recyclerView.isItemChecked(0))
    }

    @Test
    fun testItemChecked_adapterMove() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(2)
        recyclerView.setItemChecked(0, false)
        recyclerView.setItemChecked(1, true)
        Collections.swap(adapter.items, 0, 1)
        adapter.notifyItemMoved(0, 1)
        assertEquals(1, recyclerView.checkedItemCount)
        assertTrue(recyclerView.isItemChecked(0))
    }

    @Test
    fun testCheckedItemPositions() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(10)
        for (i in 0..9) {
            recyclerView.setItemChecked(i, i % 2 == 0)
        }
        val positions = recyclerView.checkedItemPositions
        assertEquals(recyclerView.checkedItemCount, positions.size())
    }

    @Test
    fun testCheckedItemIds() {
        recyclerView.choiceMode = SupportRecyclerView.CHOICE_MODE_MULTIPLE
        populateAdapter(10)
        for (i in 0..9) {
            recyclerView.setItemChecked(i, i % 2 == 0)
        }
        val ids = recyclerView.checkedItemIds
        assertEquals(recyclerView.checkedItemCount, ids.size)
    }

    private fun populateAdapter(count: Int) {
        adapter.items.clear()
        for (i in 0 until count) {
            adapter.items.add(Any())
        }
        adapter.notifyDataSetChanged()
        requestLayout()
    }

    private fun requestLayout() {
        val widthMeasureSpec = MeasureSpec.makeMeasureSpec(320, MeasureSpec.EXACTLY)
        val heightMeasureSpec = MeasureSpec.makeMeasureSpec(320, MeasureSpec.EXACTLY)
        recyclerView.measure(widthMeasureSpec, heightMeasureSpec)
        recyclerView.layout(0, 0, 320, 320)
    }

    private class MockAdapter(
        private val context: Context
    ) : Adapter<MockViewHolder>() {

        val items = arrayListOf<Any>()

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockViewHolder {
            val itemView = View(context)
            return MockViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MockViewHolder, position: Int) {
        }

        override fun getItemId(position: Int): Long {
            val item = items[position]
            return item.hashCode().toLong()
        }

        override fun getItemCount(): Int {
            return items.size
        }

    }

    private class MockViewHolder(
        itemView: View
    ) : ViewHolder(itemView)

    private class MockOnItemClickListener : OnItemClickListener {

        var called = false

        override fun onItemClick(
            parent: SupportRecyclerView,
            view: View,
            position: Int,
            id: Long
        ) {
            called = true
        }

    }

    private class MockOnItemLongClickListener : OnItemLongClickListener {

        var called = false

        override fun onItemLongClick(
            parent: SupportRecyclerView,
            view: View,
            position: Int,
            id: Long
        ): Boolean {
            called = true
            return true
        }

    }

    private class MockMultiChoiceModeListener : MultiChoiceModeListener {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return true
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode) {
        }

        override fun onItemCheckedStateChanged(
            mode: ActionMode,
            position: Int,
            id: Long,
            checked: Boolean
        ) {
        }

    }

}