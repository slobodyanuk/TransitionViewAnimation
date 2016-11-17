package com.skysoft.slobodyanuk.transitionviewanimation.view.component;

import static com.skysoft.slobodyanuk.transitionviewanimation.view.adapter.ListAdapter.ListViewHolder;

/**
 * Created by Serhii Slobodyanuk on 27.10.2016.
 */

public interface OnSwipeListener {

    void initAnimations(ListViewHolder holder);

    void onTouchUp(ListViewHolder holder, int position);

    void onTouchDown(ListViewHolder holder);

    void onLeftSwipe(int duration);

//    void onEndLeftSwipe(SwipeState state, ListViewHolder holder);

    void onRightSwipe(int duration);

//    void onEndRightSwipe(SwipeState state, ListViewHolder holder);

}
