package com.skysoft.slobodyanuk.transitionviewanimation.view.component.phone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.skysoft.slobodyanuk.transitionviewanimation.data.PhoneState;
import com.skysoft.slobodyanuk.transitionviewanimation.manager.SurfaceViewThread;
import com.skysoft.slobodyanuk.transitionviewanimation.util.OnPhoneTouchListener;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.Drawable;

/**
 * Created by Sergiy on 08.12.2016.
 */

public class PhoneSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private Context context;

    private SurfaceViewThread mSurfaceViewThread;
    private PhoneState mPhoneState;
    private OnPhoneTouchListener mPhoneTouchListener;

    public PhoneSurfaceView(Context context) {
        super(context);
        this.context = context;
        initSurfaceHolder();
    }

    public PhoneSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initSurfaceHolder();
    }

    public PhoneSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initSurfaceHolder();
    }

    private void initSurfaceHolder() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
    }

    private void initSurfaceView(){
        mPhoneState = new PhoneState(context, this);

        mSurfaceViewThread = new SurfaceViewThread(this);
        mSurfaceViewThread.setRunning(true);
        mSurfaceViewThread.start();

        mPhoneTouchListener = new OnPhoneTouchListener(mSurfaceViewThread, mPhoneState);
        setOnTouchListener(mPhoneTouchListener);
    }


    public void draw(Canvas canvas) {
        if (mPhoneState != null) {
            for (Drawable drawable : mPhoneState.getDrawables()) {
                drawable.draw(canvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initSurfaceView();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceViewThread.stopThread();
        mSurfaceViewThread = null;
    }

}
