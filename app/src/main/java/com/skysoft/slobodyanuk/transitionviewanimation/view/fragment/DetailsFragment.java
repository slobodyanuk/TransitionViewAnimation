package com.skysoft.slobodyanuk.transitionviewanimation.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.skysoft.slobodyanuk.transitionviewanimation.R;
import com.skysoft.slobodyanuk.transitionviewanimation.util.TouchViewUtil;
import com.thefinestartist.utils.ui.DisplayUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class DetailsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.root)
    LinearLayout mRoot;
    @BindView(R.id.image_container)
    RelativeLayout mImageContainer;
    @BindView(R.id.image)
    ImageView mImage;

    private String url;

    private ObjectAnimator mRotationAnimation;
    private ObjectAnimator mMoveAnimation;
    private ObjectAnimator mAlphaAnimation;
    private AnimatorSet animationSet;

    private static final int MAX_CLICK_DURATION = 150;
    private static final int MAX_CLICK_DISTANCE = 5;

    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private float prevY = 0;
    private float nextY = 0;
    private int startTime = 0;

    private boolean firstClick = true;
    private int containerY;
    private Handler handler;


    public static DetailsFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActivityCompat.postponeEnterTransition(getActivity());
        ActivityCompat.startPostponedEnterTransition(getActivity());
        url = getArguments().getString("url");
        animationSet = new AnimatorSet();

        Glide.with(this)
                .load(url)
                .asBitmap()
                .fitCenter()
                .dontTransform()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mImage.setImageBitmap(resource);
                    }
                });

        mImageContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            public void onGlobalLayout() {
                containerY = mImageContainer.getTop();
                mMoveAnimation = ObjectAnimator.ofFloat(mImageContainer, "y", containerY, DisplayUtil.getHeight() * 0.55f).setDuration(1000);
                mRotationAnimation = ObjectAnimator.ofFloat(mImageContainer, "rotationX", 0, -25).setDuration(1000);
                mAlphaAnimation = ObjectAnimator.ofFloat(mImageContainer, "alpha", 1f, 0.5f).setDuration(1000);
                animationSet.playTogether(mRotationAnimation, mMoveAnimation, mAlphaAnimation);
                mImageContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mMoveAnimation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (handler != null){
                            handler.removeCallbacksAndMessages(null);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });

        mRoot.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressStartTime = System.currentTimeMillis();
                        pressedX = event.getX();
                        pressedY = event.getY();
                        firstClick = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        nextY = event.getY();
                        long pressDuration = System.currentTimeMillis() - pressStartTime;
                        if (firstClick) {
                            if (nextY < pressedY) {
                                return true;
                            }
                        }
                        if (pressDuration > MAX_CLICK_DURATION &&
                                TouchViewUtil.distanceY(pressedY, event.getY()) > MAX_CLICK_DISTANCE) {
                            firstClick = false;
                            if (handler != null){
                                handler.removeCallbacksAndMessages(null);
                            }
                            for (Animator a : animationSet.getChildAnimations()) {
                                ((ObjectAnimator) a).setCurrentPlayTime(startTime);
                            }
                        }
                        if (pressedY > containerY) {
                            mMoveAnimation.setDuration(700);
                            startTime = (int) ((1000 * (event.getY() - pressedY)) / DisplayUtil.getHeight());
                        } else {
                            startTime = (int) (((1000) * event.getY()) / DisplayUtil.getHeight());
                        }

                        prevY = nextY;
                        break;
                    case MotionEvent.ACTION_UP:
                        if ((float) mMoveAnimation.getCurrentPlayTime() <= mMoveAnimation.getDuration() * 0.70f) {
                            reverseAnimation();
                            firstClick = true;
                        } else {
                            if (!firstClick) {
                                if (handler != null){
                                    handler.removeCallbacksAndMessages(null);
                                }
                                mRotationAnimation.setCurrentPlayTime(0);
                                getActivity().onBackPressed();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void reverseAnimation() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (startTime <= 0) {
                    handler.removeCallbacksAndMessages(null);
                }else {
                    for (Animator a : animationSet.getChildAnimations()) {
                        ((ObjectAnimator) a).setCurrentPlayTime(startTime -= 4);
                    }
                    handler.postDelayed(this, 15);
                }
            }
        }, 0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }

}
