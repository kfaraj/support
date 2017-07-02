package com.kfaraj.support.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kfaraj.support.widget.SupportRecyclerView;
import com.kfaraj.support.widget.SupportRecyclerView.MultiChoiceModeListener;
import com.kfaraj.support.widget.SupportRecyclerView.OnItemClickListener;
import com.kfaraj.support.widget.SupportRecyclerView.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Demonstrates the features of the recyclerview library.
 */
public class RecyclerViewFragment extends Fragment implements OnClickListener, OnItemClickListener, OnItemLongClickListener,
        MultiChoiceModeListener {

    /**
     * The items in the adapter.
     */
    private static final String KEY_ITEMS = "items";

    private Adapter mAdapter;
    private SupportRecyclerView mRecyclerView;
    private View mEmptyView;
    private View mFab;

    /**
     * Creates a new instance of this fragment class.
     *
     * @return a new instance of this fragment class.
     */
    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Adapter(getActivity());
        if (savedInstanceState != null) {
            ArrayList<String> items = savedInstanceState.getStringArrayList(KEY_ITEMS);
            mAdapter.addAll(items);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (SupportRecyclerView) view.findViewById(android.R.id.list);
        mEmptyView = view.findViewById(android.R.id.empty);
        mFab = view.findViewById(R.id.fab);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFab.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(null));
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setOnItemClickListener(this);
        mRecyclerView.setOnItemLongClickListener(this);
        mRecyclerView.setMultiChoiceModeListener(this);
        mRecyclerView.setChoiceMode(SupportRecyclerView.CHOICE_MODE_MULTIPLE_MODAL);
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                mAdapter.move(fromPosition, toPosition);
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mAdapter.remove(position);
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<String> items = mAdapter.getItems();
        outState.putStringArrayList(KEY_ITEMS, items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        if (v == mFab) {
            mAdapter.add(UUID.randomUUID().toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClick(SupportRecyclerView parent, View view, int position, long id) {
        String item = mAdapter.getItems().get(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onItemLongClick(SupportRecyclerView parent, View view, int position, long id) {
        String item = mAdapter.getItems().get(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.recyclerview, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        int itemCount = mRecyclerView.getCheckedItemCount();
        mode.setTitle(String.valueOf(itemCount));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                if (mRecyclerView.isItemChecked(i)) {
                    mAdapter.remove(i--);
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
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        mode.invalidate();
    }

    /**
     * The adapter.
     */
    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private final LayoutInflater mInflater;
        private final ArrayList<String> mItems = new ArrayList<>();

        /**
         * Constructor.
         *
         * @param context the context.
         */
        Adapter(Context context) {
            setHasStableIds(true);
            mInflater = LayoutInflater.from(context);
        }

        /**
         * Adds an item.
         *
         * @param item the item.
         */
        void add(String item) {
            mItems.add(0, item);
            notifyItemInserted(0);
        }

        /**
         * Adds all items.
         *
         * @param items the items.
         */
        void addAll(ArrayList<String> items) {
            mItems.addAll(0, items);
            notifyItemRangeInserted(0, items.size());
        }

        /**
         * Moves an item.
         *
         * @param fromPosition the old position.
         * @param toPosition the new position.
         */
        void move(int fromPosition, int toPosition) {
            mItems.add(toPosition, mItems.remove(fromPosition));
            notifyItemMoved(fromPosition, toPosition);
        }

        /**
         * Removes an item.
         *
         * @param position the item position.
         */
        void remove(int position) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }

        /**
         * Returns the items.
         *
         * @return the items.
         */
        ArrayList<String> getItems() {
            return mItems;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.item_recyclerview, parent, false);
            return new ViewHolder(itemView);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
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
        ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text_view);
        }

    }

}
