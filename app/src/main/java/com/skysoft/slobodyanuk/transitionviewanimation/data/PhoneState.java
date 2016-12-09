package com.skysoft.slobodyanuk.transitionviewanimation.data;

import android.content.Context;

import com.skysoft.slobodyanuk.transitionviewanimation.view.component.Drawable;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure.PhoneCircle;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure.RectContainer;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.phone.PhoneSurfaceView;

import java.util.ArrayList;
import java.util.List;

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

    public List<Drawable> getDrawables() {
        return mDrawables;
    }

    public PhoneCircle getCircleDrawable(){
        return mCircleView;
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
