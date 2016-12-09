package com.skysoft.slobodyanuk.transitionviewanimation.manager;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import com.skysoft.slobodyanuk.transitionviewanimation.view.component.phone.PhoneSurfaceView;

/**
 * Created by Sergiy on 09.12.2016.
 */

public class SurfaceViewThread extends Thread {

    private boolean isRunning;
    private PhoneSurfaceView mPhoneSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas = null;
    private boolean stop;

    public SurfaceViewThread(PhoneSurfaceView mPhoneSurfaceView) {
        this.mPhoneSurfaceView = mPhoneSurfaceView;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void stopThread(){
        this.isRunning = false;
        this.stop = true;
    }
    @Override
    public void run() {
        while (!stop) {
            if (isRunning) {
                try {
                    mSurfaceHolder = mPhoneSurfaceView.getHolder();
                    if (mSurfaceHolder != null && mSurfaceHolder.getSurface().isValid()) {

                        mCanvas = mSurfaceHolder.lockCanvas();

                        if (mCanvas != null) {
                            mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

                            synchronized (mPhoneSurfaceView.getHolder()) {
                                mPhoneSurfaceView.draw(mCanvas);
                            }
                        }
                    }
                } finally {
                    if (mSurfaceHolder != null && mSurfaceHolder.getSurface().isValid() && mCanvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
            }
        }
    }
}
