package com.skysoft.slobodyanuk.transitionviewanimation.util;

import android.support.v4.view.VelocityTrackerCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.skysoft.slobodyanuk.transitionviewanimation.view.adapter.ListAdapter;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.OnSwipeListener;

import static com.skysoft.slobodyanuk.transitionviewanimation.view.adapter.ListAdapter.ListViewHolder;

/**
 * Created by Serhii Slobodyanuk on 24.10.2016.
 */

public class OnSwipeTouchListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

    private static final int SWIPE_MIN_DISTANCE = 35;
    private static final long MAX_CLICK_DURATION = 100;
    private static final float MAX_Y_CLICK_DISTANCE = 15;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    public static final int ANIMATION_SET_DURATION = 700;

    private final RecyclerManager manager;
    private ListViewHolder holder = null;

    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private int diff;
    private int maxDiff;
    private String tag;
    private View parent;

    private boolean wasScrolling;
    private OnSwipeListener mSwipeListener;
    public int pos;
    private boolean initSwipe;
    private VelocityTracker mVelocityTracker;

    public OnSwipeTouchListener(OnSwipeListener listener, RecyclerManager manager, ListViewHolder holder, int position) {
        this.mSwipeListener = listener;
        this.pos = position;
        this.manager = manager;
        this.holder = holder;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int index = event.getActionIndex();
        int pointerId = event.getPointerId(index);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                maxDiff = (maxDiff > ANIMATION_SET_DURATION) ? (int) event.getX() : ANIMATION_SET_DURATION;

                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
                wasScrolling = false;
                if (!((ListAdapter) mSwipeListener).isAnimated()) {
                    mSwipeListener.onTouchDown(holder);
                    pressStartTime = System.currentTimeMillis();
                    pressedX = event.getX();
                    pressedY = event.getX();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                long pressDuration = System.currentTimeMillis() - pressStartTime;
                if (pressDuration > MAX_CLICK_DURATION && pressedY > event.getY() &&
                        (pressedY - event.getY()) > MAX_Y_CLICK_DISTANCE ||
                        pressedY < event.getY() && (event.getY() - pressedY) > MAX_Y_CLICK_DISTANCE) {

                    diff = (int) (pressedX - event.getX());

                    mVelocityTracker.addMovement(event);
                    mVelocityTracker.computeCurrentVelocity(ANIMATION_SET_DURATION);

                    if (!initSwipe) {
                        mSwipeListener.initAnimations(holder);
                        initSwipe = true;
                    }

                    if (diff > SWIPE_MIN_DISTANCE
                            && Math.abs(VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                            pointerId)) > SWIPE_THRESHOLD_VELOCITY) {
                        if (diff > 0) {
                            if ((Math.abs(maxDiff) - Math.abs(diff)) > 0) {
                                mSwipeListener.onLeftSwipe(((Math.abs(ANIMATION_SET_DURATION * maxDiff) -
                                        Math.abs(ANIMATION_SET_DURATION * diff))) / ANIMATION_SET_DURATION);
                            }
                        }
                    } else if (-diff > SWIPE_MIN_DISTANCE
                            && Math.abs(VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                            pointerId)) > SWIPE_THRESHOLD_VELOCITY) {
                        mSwipeListener.onRightSwipe(Math.abs(diff));
                    }

                    wasScrolling = true;
                } else {
                    if (!((ListAdapter) mSwipeListener).isAnimated()) {
                        initSwipe = false;
                        manager.setScrollEnabled(true);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                initSwipe = false;
                maxDiff = (maxDiff > ANIMATION_SET_DURATION) ? (int) event.getX() : ANIMATION_SET_DURATION;
                mSwipeListener.onTouchUp(holder, pos);
                break;

            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;
        }
        return wasScrolling;
    }

}
