package com.kfaraj.support.sample;

import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.kfaraj.support.sample.widget.SpaceItemDecoration;
import com.kfaraj.support.widget.SupportRecyclerView;
import com.kfaraj.support.widget.SupportRecyclerView.MultiChoiceModeListener;
import com.kfaraj.support.widget.SupportRecyclerView.OnItemClickListener;
import com.kfaraj.support.widget.SupportRecyclerView.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Demonstrates the features of the recyclerview library.
 */
public class RecyclerViewFragment extends Fragment
        implements OnClickListener, OnItemClickListener,
        OnItemLongClickListener, MultiChoiceModeListener {

    private static final String KEY_ITEMS = "items";

    private Adapter mAdapter;
    private SupportRecyclerView mRecyclerView;
    private View mEmptyView;
    private View mFab;

    /**
     * Constructor.
     */
    public RecyclerViewFragment() {
        super(R.layout.fragment_recyclerview);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Adapter(requireContext());
        if (savedInstanceState != null) {
            final ArrayList<String> items = savedInstanceState.getStringArrayList(KEY_ITEMS);
            if (items != null) {
                mAdapter.getItems().addAll(items);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = ViewCompat.requireViewById(view, android.R.id.list);
        mEmptyView = ViewCompat.requireViewById(view, android.R.id.empty);
        mFab = ViewCompat.requireViewById(view, R.id.fab);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                final int fromPosition = viewHolder.getAdapterPosition();
                final int toPosition = target.getAdapterPosition();
                Collections.swap(mAdapter.getItems(), fromPosition, toPosition);
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                    int direction) {
                final int position = viewHolder.getAdapterPosition();
                mAdapter.getItems().remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        };
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
        final int space = Math.round(getResources().getDimension(R.dimen.card_margin) / 2.0f);
        mRecyclerView.setPadding(space, space, space, space);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setOnItemClickListener(this);
        mRecyclerView.setOnItemLongClickListener(this);
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL);
        mRecyclerView.setMultiChoiceModeListener(this);
        mFab.setOnClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        final ArrayList<String> items = mAdapter.getItems();
        outState.putStringArrayList(KEY_ITEMS, items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        if (v == mFab) {
            final String item = UUID.randomUUID().toString();
            mAdapter.getItems().add(0, item);
            mAdapter.notifyItemInserted(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClick(@NonNull SupportRecyclerView parent,
            @NonNull View view, int position, long id) {
        final String item = mAdapter.getItems().get(position);
        Snackbar.make(mRecyclerView, item, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onItemLongClick(@NonNull SupportRecyclerView parent,
            @NonNull View view, int position, long id) {
        final String item = mAdapter.getItems().get(position);
        Snackbar.make(mRecyclerView, item, Snackbar.LENGTH_LONG).show();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        final MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.recyclerview, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        final int count = mRecyclerView.getCheckedItemCount();
        mode.setTitle(Integer.toString(count));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.delete) {
            for (int i = mAdapter.getItemCount() - 1; i >= 0; i--) {
                if (mRecyclerView.isItemChecked(i)) {
                    mAdapter.getItems().remove(i);
                    mAdapter.notifyItemRemoved(i);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemCheckedStateChanged(@NonNull ActionMode mode,
            int position, long id, boolean checked) {
        mode.invalidate();
    }

    /**
     * The adapter.
     */
    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private final ArrayList<String> mItems = new ArrayList<>();
        private final LayoutInflater mInflater;

        /**
         * Constructor.
         *
         * @param context the context.
         */
        Adapter(@NonNull Context context) {
            setHasStableIds(true);
            mInflater = LayoutInflater.from(context);
        }

        /**
         * Returns the items.
         *
         * @return the items.
         */
        @NonNull
        ArrayList<String> getItems() {
            return mItems;
        }

        /**
         * {@inheritDoc}
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View itemView = mInflater.inflate(R.layout.item_card, parent, false);
            return new ViewHolder(itemView);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(mItems.get(position));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getItemId(int position) {
            return mItems.get(position).hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getItemCount() {
            return mItems.size();
        }

    }

    /**
     * The view holder.
     */
    private static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;

        /**
         * Constructor.
         *
         * @param itemView the item view.
         */
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = ViewCompat.requireViewById(itemView, android.R.id.text1);
        }

    }

}
