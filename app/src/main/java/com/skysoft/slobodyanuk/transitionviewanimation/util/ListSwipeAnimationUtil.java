package com.skysoft.slobodyanuk.transitionviewanimation.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.skysoft.slobodyanuk.transitionviewanimation.view.adapter.ListAdapter;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.ClockArrowView;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.ClockView;
import com.thefinestartist.utils.ui.DisplayUtil;

/**
 * Created by Serhii Slobodyanuk on 24.10.2016.
 */

public class ListSwipeAnimationUtil extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

    private static final long MAX_CLICK_DURATION = 100;
    private static final long ANIMATION_SET_DURATION = 700;
    private static final float MAX_Y_CLICK_DISTANCE = 15;

    private ObjectAnimator mTranslationAnimation;
    private ObjectAnimator mRotateAnimation;
    private ObjectAnimator mTranslationViewAnimation;
    private AnimatorSet mAnimatorSet;

    private RecyclerManager manager;
    private LinearLayout mItemLayout;
    private RelativeLayout mHideLeftLayout;
    private ClockView mClockView;
    private ClockArrowView mClockArrowView;

    private long pressStartTime;

    private float pressedX;
    private float pressedY;
    private int startTime = 0;
    private int endTime = 0;

    private boolean animated = false;
    private boolean clockDrawn = false;

    private Handler handler;
    private Fragment mItemListener;

    public ListSwipeAnimationUtil(ListAdapter.ItemListener listener, RecyclerManager manager, ListAdapter.ListViewHolder holder) {
        this.manager = manager;
        this.mItemListener = (Fragment) listener;
        this.mItemLayout = holder.item;
        this.mHideLeftLayout = holder.hideView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!animated) {
                    mTranslationAnimation = ObjectAnimator
                            .ofFloat(mItemLayout, "x", 2, mHideLeftLayout.getRight() * 2)
                            .setDuration(ANIMATION_SET_DURATION);
                    mTranslationViewAnimation = ObjectAnimator
                            .ofFloat(mHideLeftLayout, "x", mHideLeftLayout.getLeft(), 0)
                            .setDuration(ANIMATION_SET_DURATION);
                    mRotateAnimation = ObjectAnimator
                            .ofFloat(mHideLeftLayout, "rotationY", -90, 0)
                            .setDuration(ANIMATION_SET_DURATION);

                    mAnimatorSet = new AnimatorSet();
                    mAnimatorSet.playTogether(mTranslationAnimation, mRotateAnimation, mTranslationViewAnimation);

                    pressStartTime = System.currentTimeMillis();
                    pressedX = event.getX();
                    pressedY = event.getX();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                long pressDuration = System.currentTimeMillis() - pressStartTime;
                if (pressDuration > MAX_CLICK_DURATION &&
                        pressedY > event.getY() &&
                        (pressedY - event.getY()) > MAX_Y_CLICK_DISTANCE ||
                        pressedY < event.getY() && (event.getY() - pressedY) > MAX_Y_CLICK_DISTANCE) {
                    if (handler != null) {
                        handler.removeCallbacksAndMessages(null);
                        animated = false;
                    }
                    animated = true;
                    manager.setScrollEnabled(false);
                    for (Animator a : mAnimatorSet.getChildAnimations()) {
                        ((ObjectAnimator) a).setCurrentPlayTime(startTime);
                    }

                    if (pressedX < event.getX()) {
                        startTime = (int) ((1000 * (event.getX() - pressedX)) / DisplayUtil.getWidth());
                    } else if (pressedX > event.getX() && endTime >= mTranslationAnimation.getDuration()) {
                        startTime = (int) ((DisplayUtil.getWidth() * (event.getX())) / ANIMATION_SET_DURATION);
                    }
                } else {
                    if (!animated) {
                        manager.setScrollEnabled(true);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (!clockDrawn) drawClockView();
                if (animated) smoothAnimation();
                break;
        }
        return true;
    }

    private void drawClockView() {
        if (endTime >= ANIMATION_SET_DURATION && !clockDrawn) {
            removeClockView();
            mClockArrowView = new ClockArrowView(mItemListener.getActivity(), mHideLeftLayout);
            mClockView = new ClockView(mItemListener.getActivity(), mHideLeftLayout);
            mHideLeftLayout.addView(mClockArrowView);
            mHideLeftLayout.addView(mClockView);
            clockDrawn = true;
        } else {
            removeClockView();
        }
    }

    private void removeClockView() {
        if (mClockArrowView != null) {
            mClockArrowView.clearAnimation();
        }
        mHideLeftLayout.removeAllViews();
        clockDrawn = false;
    }

    public void startAnimation() {
        if (startTime >= ANIMATION_SET_DURATION && mClockArrowView != null) {
            mClockArrowView.createAnimation();
        }

    }

    private void smoothAnimation() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (startTime <= 0 || startTime >= ANIMATION_SET_DURATION) {
                    animated = false;
                    clockDrawn = false;
                    manager.setScrollEnabled(true);
                    endTime = startTime;
                    if (!clockDrawn) drawClockView();
                    handler.removeCallbacksAndMessages(null);
                } else {
                    for (Animator a : mAnimatorSet.getChildAnimations()) {
                        if (mTranslationAnimation.getCurrentPlayTime() >= mTranslationAnimation.getDuration() * 0.5) {
                            ((ObjectAnimator) a).setCurrentPlayTime(startTime += 8);
                        } else {
                            ((ObjectAnimator) a).setCurrentPlayTime(startTime -= 8);
                        }
                    }
                    handler.postDelayed(this, 15);
                }
            }
        }, 0);
    }
}
