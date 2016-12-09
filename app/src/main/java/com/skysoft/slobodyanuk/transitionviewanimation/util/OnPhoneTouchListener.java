package com.skysoft.slobodyanuk.transitionviewanimation.util;

import android.view.MotionEvent;
import android.view.View;

import com.skysoft.slobodyanuk.transitionviewanimation.data.PhoneState;
import com.skysoft.slobodyanuk.transitionviewanimation.manager.SurfaceViewThread;

/**
 * Created by Sergiy on 09.12.2016.
 */

public class OnPhoneTouchListener implements View.OnTouchListener {

    private SurfaceViewThread mSurfaceViewThread;
    private PhoneState mPhoneState;
    private float mShiftDistance;
    private float touchX = 0;
    private float touchY = 0;
    private boolean allowDraw = false;

    public OnPhoneTouchListener(SurfaceViewThread mSurfaceViewThread, PhoneState phoneState) {
        this.mSurfaceViewThread = mSurfaceViewThread;
        this.mPhoneState = phoneState;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                if (mPhoneState.getCircleDrawable().isTouched(touchX, touchY)) {
                    allowDraw = true;
                    mSurfaceViewThread.setRunning(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (allowDraw) {
//                    mSurfaceViewThread.setRunning(true);

//                    mShiftDistance = event.getX() - touchX;
//                    mPhoneState.setShiftCircleX(mShiftDistance);
//
//                    mShiftDistance = event.getY() - touchY;
//                    mPhoneState.setShiftCircleY(mShiftDistance);
                    mPhoneState.getCircleDrawable().offset(event.getX() - touchX, event.getY() - touchY);

                    touchX = event.getX();
                    touchY = event.getY();

                }
                break;
            case MotionEvent.ACTION_UP:
                mSurfaceViewThread.setRunning(false);
                allowDraw = false;
                break;
        }

        return true;
    }

}
