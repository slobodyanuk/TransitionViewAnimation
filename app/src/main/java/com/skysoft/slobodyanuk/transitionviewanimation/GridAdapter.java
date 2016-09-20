package com.skysoft.slobodyanuk.transitionviewanimation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    private int mSize;
    private ItemListener mItemListener;

    public GridAdapter(int mSize, ItemListener mItemListener) {
        this.mSize = mSize;
        this.mItemListener = mItemListener;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GridViewHolder holder, int position) {
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(holder, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root)
        LinearLayout root;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle)
        TextView subtitle;

        public GridViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface ItemListener{
        void onItemClick(GridViewHolder holder, int position);
    }
}
