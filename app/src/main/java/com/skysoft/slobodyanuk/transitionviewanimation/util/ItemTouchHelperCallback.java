package com.skysoft.slobodyanuk.transitionviewanimation.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Serhii Slobodyanuk on 17.10.2016.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouch mAdapter;
    private final boolean swiped;

    public ItemTouchHelperCallback(ItemTouch adapter, boolean swiped) {
        mAdapter = adapter;
        this.swiped = swiped;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return swiped;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
        mAdapter.onClockShow(direction);
    }

    public interface ItemTouch {
        void onClockShow(int direction);

        void onItemDismiss(int position);
    }
}
