package com.skysoft.slobodyanuk.transitionviewanimation.view.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skysoft.slobodyanuk.transitionviewanimation.R;
import com.skysoft.slobodyanuk.transitionviewanimation.util.ItemTouchHelperCallback;
import com.skysoft.slobodyanuk.transitionviewanimation.util.ListSwipeAnimationUtil;
import com.skysoft.slobodyanuk.transitionviewanimation.util.RecyclerManager;
import com.skysoft.slobodyanuk.transitionviewanimation.util.ValueInterpolator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements ItemTouchHelperCallback.ItemTouch {

    private int mSize;
    private int[] colors;

    private ArrayList<String> mItems = new ArrayList<>();
    private static RecyclerManager manager;
    private static ItemListener mItemListener;


    public ListAdapter(RecyclerManager manager, int mSize, ItemListener mItemListener) {
        this.mSize = mSize;
        ListAdapter.manager = manager;
        for (int i = 0; i < mSize; i++) {
            mItems.add("Item :: " + i);
        }
        ListAdapter.mItemListener = mItemListener;
        initItemGradient();
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

        LayerDrawable bgDrawable = (LayerDrawable) holder.item.getBackground();
        GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.background_shape);
        shape.setColor(colors[position]);
    }


    @Override
    public int getItemCount() {
        return mSize;
    }

    @Override
    public void onClockShow(int direction) {

    }

    @Override
    public void onItemDismiss(int position) {

    }

    private void initItemGradient() {
        colors = new int[mSize];

        int startColor = ContextCompat.getColor(((Fragment) mItemListener).getContext(), R.color.colorListItem);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endColor = ContextCompat.getColor(((Fragment) mItemListener).getContext(), R.color.colorPrimaryDark);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        ValueInterpolator interpolatorR = new ValueInterpolator(0, mSize - 1, startR, endR);
        ValueInterpolator interpolatorG = new ValueInterpolator(0, mSize - 1, startG, endG);
        ValueInterpolator interpolatorB = new ValueInterpolator(0, mSize - 1, startB, endB);

        for (int i = 0; i < mSize; ++i) {
            colors[i] = Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i));
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root)
        public FrameLayout root;
        @BindView(R.id.item_container)
        public LinearLayout item;
        @BindView(R.id.hide_container)
        public RelativeLayout hideView;
        @BindView(R.id.title_item)
        public TextView title;

        public ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnTouchListener(new ListSwipeAnimationUtil(mItemListener, manager, this));
        }
    }

    public interface ItemListener {
    }
}
