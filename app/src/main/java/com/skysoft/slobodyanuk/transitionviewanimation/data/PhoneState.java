package com.skysoft.slobodyanuk.transitionviewanimation.data;

import android.content.Context;

import com.skysoft.slobodyanuk.transitionviewanimation.util.MathUtils;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.Drawable;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure.PhoneCircle;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure.RectContainer;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.phone.PhoneSurfaceView;

import java.util.ArrayList;
import java.util.List;

import static com.skysoft.slobodyanuk.transitionviewanimation.view.component.ViewConstants.SPEED_PER_SECONDS;

/**
 * Created by Sergiy on 09.12.2016.
 */

public class PhoneState {

    private Context mContext;

    private List<Drawable> mDrawables = new ArrayList<>();

    private PhoneCircle mCircleView;
    private RectContainer mRectView;
    private PhoneSurfaceView mSurfaceView;

    private float mShiftCircleX;
    private float mShiftCircleY;
    private double angle;

    private boolean allowMove;
    private double mPointDistance;
    private float lastCenterX;
    private float lastCenterY;

    public PhoneState(Context context, PhoneSurfaceView surfaceView) {
        this.mSurfaceView = surfaceView;
        this.mContext = context;
        initViews();
    }

    private void initViews() {
        mRectView = new RectContainer(mSurfaceView.getWidth(), mSurfaceView.getHeight());
        mCircleView = new PhoneCircle(mContext, mSurfaceView.getWidth(),
                mSurfaceView.getHeight(), this);

        mDrawables.add(mRectView);
        mDrawables.add(mCircleView);
    }

    public void calculateCircleMove(long delta) {
        if (allowMove) {
            final float shift = SPEED_PER_SECONDS * ((float) delta) / 1000;
            mCircleView.setCenterX(mCircleView.getCenterX() + shift * (float) Math.cos(Math.toRadians(angle)));
            mCircleView.setCenterY(mCircleView.getCenterY() + shift * (float) Math.sin(Math.toRadians(angle)));
            if (checkFinish()) {
                mCircleView.setCenterX(mCircleView.getInitCenterX());
                mCircleView.setCenterY(mCircleView.getInitCenterY());
                allowMove = false;
            }
        }
    }

    private boolean checkFinish() {
        return MathUtils.calcDistance(mCircleView.getCenterX(), mCircleView.getCenterY(),
                lastCenterX, lastCenterY) >= mPointDistance;
    }

    private void updateAngle() {
        this.angle = MathUtils.calcAngle(mCircleView.getInitCenterX() - mCircleView.getCenterX(),
                mCircleView.getInitCenterY() - mCircleView.getCenterY());
    }

    public void setMove(boolean move) {
        if (move) {
            lastCenterX = mCircleView.getCenterX();
            lastCenterY = mCircleView.getCenterY();
            mPointDistance = MathUtils.calcDistance(mCircleView.getCenterX(), mCircleView.getCenterY(),
                    mCircleView.getInitCenterX(), mCircleView.getInitCenterY());

            if (lastCenterX < mCircleView.getInitCenterX() - mCircleView.getRadius() * 2.5f ||
                    lastCenterX > mCircleView.getInitCenterX() + mCircleView.getRadius() * 2.5f) {
                move = false;
            }
            updateAngle();
        }
        this.allowMove = move;
    }

    public List<Drawable> getDrawables() {
        return mDrawables;
    }

    public PhoneCircle getCircleDrawable() {
        return mCircleView;
    }

    public RectContainer getRectView() {
        return mRectView;
    }

    public float getShiftCircleX() {
        return mShiftCircleX;
    }

    public void setShiftCircleX(float mShiftCircleX) {
        this.mShiftCircleX = mShiftCircleX;
    }

    public float getShiftCircleY() {
        return mShiftCircleY;
    }

    public void setShiftCircleY(float mShiftCircleY) {
        this.mShiftCircleY = mShiftCircleY;
    }
}
