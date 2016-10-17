package com.skysoft.slobodyanuk.transitionviewanimation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements ItemTouchHelperCallback.ItemTouch{


    private int mSize;
    private ItemListener mItemListener;
    private ArrayList<String> mItems = new ArrayList<>();
    public ListAdapter(int mSize, ItemListener mItemListener) {
        this.mSize = mSize;
        for (int i = 0; i < mSize; i++){
            mItems.add("Item :: " + i);
        }
        this.mItemListener = mItemListener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        holder.title.setText(mItems.get(position));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root)
        FrameLayout root;
        @BindView(R.id.title_item)
        TextView title;

        public ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface ItemListener {
    }
}
