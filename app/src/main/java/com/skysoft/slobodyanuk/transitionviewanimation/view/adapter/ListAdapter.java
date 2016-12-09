package com.skysoft.slobodyanuk.transitionviewanimation.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skysoft.slobodyanuk.transitionviewanimation.R;
import com.skysoft.slobodyanuk.transitionviewanimation.util.OnSwipeTouchListener;
import com.skysoft.slobodyanuk.transitionviewanimation.util.RecyclerManager;
import com.skysoft.slobodyanuk.transitionviewanimation.util.ValueInterpolator;
import com.skysoft.slobodyanuk.transitionviewanimation.view.adapter.models.FoldItem;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.clock.ClockArrowView;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.clock.ClockView;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.FoldingLayout;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.OnFoldListener;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.OnSwipeListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>
        implements OnFoldListener, OnSwipeListener {

    public static final int ANIMATION_SET_DURATION = 700;

    private int[] colors;

    private static ArrayList<FoldItem> mItems = new ArrayList<>();
    private static RecyclerManager manager;
    private RecyclerView recyclerView;

    private static Fragment mContext;

    private AnimatorSet mAnimatorSet;

    private int mAnimationEndTime = 0;
    private int mAnimationStartTime = 0;
    private boolean animated = false;
    private boolean clockDrawn = false;
    private ClockArrowView mClockArrowView;
    private ClockView mClockView;
    private Handler handler;
    private GestureDetector gestureDetector;
    private View.OnTouchListener gestureListener;
    private boolean isLeftViewOpen;
    private ListViewHolder tmpHolder;
    private int animatedPosition;
    private static OnSwipeListener listener;


    public ListAdapter(RecyclerManager manager, Fragment mContext, RecyclerView recyclerView) {
        ListAdapter.manager = manager;
        this.recyclerView = recyclerView;
        mItems.clear();
        for (int i = 0; i < 20; i++) {
            mItems.add(new FoldItem("Item :: " + i));
        }
        ListAdapter.mContext = mContext;
        initItemGradient();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        final FoldItem fi = mItems.get(position);
        Animation a = animate(holder);
        if (!holder.item.isShown()){
            a.cancel();
        }
        holder.root.setTag(fi);
        holder.title.setText(fi.getName());
        holder.item.setOnTouchListener(new OnSwipeTouchListener(this, manager, holder, position));

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldLayout.setFoldListener(ListAdapter.this, fi, holder);
                animateFold(holder.foldLayout);
            }
        });

        LayerDrawable bgDrawable = (LayerDrawable) holder.item.getBackground();
        GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.background_shape);
        shape.setColor(colors[position]);
    }

    public void animateFold(FoldingLayout foldingLayout) {
        float foldFactor = foldingLayout.getFoldFactor();
        ObjectAnimator animator = ObjectAnimator.ofFloat(foldingLayout, "foldFactor", foldFactor, 1);
        animator.setRepeatCount(0);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void initItemGradient() {
        colors = new int[getItemCount()];

        int startColor = ContextCompat.getColor(mContext.getActivity(), R.color.colorListItem);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endColor = ContextCompat.getColor(mContext.getActivity(), R.color.colorPrimaryDark);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        ValueInterpolator interpolatorR = new ValueInterpolator(0, getItemCount(), startR, endR);
        ValueInterpolator interpolatorG = new ValueInterpolator(0, getItemCount(), startG, endG);
        ValueInterpolator interpolatorB = new ValueInterpolator(0, getItemCount(), startB, endB);

        for (int i = 0; i < getItemCount(); ++i) {
            colors[i] = Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i));
        }
    }

    private void remove(final FoldItem fi, RecyclerView.ViewHolder holder) {
        final int pos = mItems.indexOf(fi);
        if (pos >= 0) {
            mItems.remove(pos);
            initItemGradient();
            AnimatorSet set = (AnimatorSet) ((ListViewHolder) holder).item.getTag();
            if (set != null) {
                for (Animator a : set.getChildAnimations()) {
                    ((ObjectAnimator) a).setCurrentPlayTime(0);
                }
            }
            notifyItemRemoved(pos);
            /*Update recycler gradient background*/
            for (int i = 0; i < mItems.size(); i++) {
                notifyItemChanged(i);
            }
        }
    }

    public Animation animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext.getActivity(), R.anim.interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
        return animAnticipateOvershoot;
    }


    @Override
    public void onStartFold(final FoldItem item, RecyclerView.ViewHolder holder) {
    }

    @Override
    public void onEndFold(final FoldItem item, RecyclerView.ViewHolder holder) {
        remove(item, holder);
    }

    @Override
    public void initAnimations(ListViewHolder holder) {
        ObjectAnimator mTranslationAnimation = ObjectAnimator
                .ofFloat(holder.item, "x", 2, holder.hideView.getRight() * 2)
                .setDuration(ANIMATION_SET_DURATION);
        ObjectAnimator mTranslationViewAnimation = ObjectAnimator
                .ofFloat(holder.hideView, "x", holder.hideView.getLeft(), 0)
                .setDuration(ANIMATION_SET_DURATION);
        ObjectAnimator mRotateAnimation = ObjectAnimator
                .ofFloat(holder.hideView, "rotationY", -90, 0)
                .setDuration(ANIMATION_SET_DURATION);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(mTranslationAnimation, mRotateAnimation, mTranslationViewAnimation);
        holder.item.setTag(mAnimatorSet);
    }

    @Override
    public void onTouchUp(ListViewHolder holder, int pos) {
        if (animated) smoothAnimation(holder, pos);
    }

    @Override
    public void onTouchDown(ListViewHolder holder) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            animated = false;
        }
    }

    @Override
    public void onLeftSwipe(int duration) {
        animated = true;
        manager.setScrollEnabled(false);
        for (Animator a : mAnimatorSet.getChildAnimations()) {
            ((ObjectAnimator) a).setCurrentPlayTime(duration);

        }
        mAnimationStartTime = duration;
    }

    @Override
    public void onRightSwipe(int duration) {

        animated = true;
        manager.setScrollEnabled(false);
        for (Animator a : mAnimatorSet.getChildAnimations()) {
            ((ObjectAnimator) a).setCurrentPlayTime(duration);
        }
        mAnimationStartTime = duration;
    }

    private void drawClockView(ListViewHolder holder) {
        if (mAnimationEndTime >= ANIMATION_SET_DURATION && !clockDrawn) {
            Log.e("tag", "drawClockView: " + holder.item.getTag());
            removeClockView(holder);
            mClockArrowView = new ClockArrowView(mContext.getActivity(), holder.hideView);
            mClockView = new ClockView(mContext.getActivity(), holder.hideView);
            holder.hideView.addView(mClockArrowView);
            holder.hideView.addView(mClockView);
            holder.hideView.setTag(R.string.clock_arrow_tag, mClockArrowView);
            holder.hideView.setTag(R.string.clock_tag, mClockView);
            clockDrawn = true;
        } else {
            removeClockView(holder);
            Log.e("tag", "remove: " + holder.item.getTag());
            ((FoldItem) holder.root.getTag()).setSwiped(false);
        }
    }

    public boolean isAnimated() {
        return animated;
    }

    public int getAnimationEndTime() {
        return mAnimationEndTime;
    }

    public void removeClockView(ListViewHolder holder) {
        if (mClockArrowView != null) {
            mClockArrowView.clearAnimation();
        }
        ClockArrowView clockArrowView = (ClockArrowView) holder.hideView.getTag(R.string.clock_arrow_tag);
        ClockView clockView = (ClockView) holder.hideView.getTag(R.string.clock_tag);
        if (clockArrowView != null && clockView != null) {
            holder.hideView.removeView(clockArrowView);
            holder.hideView.removeView(clockView);
        }
        holder.hideView.removeAllViews();
        clockDrawn = false;
    }

    private void smoothAnimation(final ListViewHolder holder, final int pos) {
        handler = new Handler();
        animatedPosition = pos;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAnimationStartTime <= 0 || mAnimationStartTime >= ANIMATION_SET_DURATION) {
                    animated = false;
                    clockDrawn = false;
                    isLeftViewOpen = (mAnimationStartTime >= ANIMATION_SET_DURATION);
                    manager.setScrollEnabled(true);
                    mAnimationEndTime = mAnimationStartTime;
                    if (!clockDrawn) drawClockView(holder);
                    handler.removeCallbacksAndMessages(null);
                } else {
                    for (Animator a : mAnimatorSet.getChildAnimations()) {
                        if (mAnimationStartTime >= ANIMATION_SET_DURATION * 0.3 && mAnimationEndTime <= 0) {
                            ((ObjectAnimator) a).setCurrentPlayTime(mAnimationStartTime += 8);
                        } else {
                            ((ObjectAnimator) a).setCurrentPlayTime(mAnimationStartTime -= 8);
                        }
                    }
                    handler.postDelayed(this, 15);
                }
            }
        }, 0);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root)
        public FrameLayout root;
        @BindView(R.id.flip_container)
        public FoldingLayout foldLayout;
        @BindView(R.id.item_container)
        public LinearLayout item;
        @BindView(R.id.hide_container)
        public RelativeLayout hideView;
        @BindView(R.id.title_item)
        public TextView title;

        public ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}
