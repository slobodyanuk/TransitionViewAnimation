package com.skysoft.slobodyanuk.transitionviewanimation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    //images
    private String[] urls = {"http://www.gettyimages.pt/gi-resources/images/Homepage/Hero/PT/PT_hero_42_153645159.jpg",
            "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRGb8uEb-LmFbTe4zq7EMANL5rWOy5DUZX7cGSUYLQ6MR-Nku2R",
            "http://static.ddmcdn.com/gif/storymaker-best-hubble-space-telescope-images-20092-514x268.jpg",
            "http://static.ddmcdn.com/gif/storymaker-best-hubble-space-telescope-images-20092-514x268.jpg"};

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
        final String url = urls[position % 4];

        Glide.with((GridFragment) mItemListener)
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.image);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(holder.image, holder.getAdapterPosition(), url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root)
        FrameLayout root;
        @BindView(R.id.image)
        SquareView image;

        public GridViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface ItemListener {
        void onItemClick(SquareView image, int position, String url);
    }
}
