package com.skysoft.slobodyanuk.transitionviewanimation.manager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import com.skysoft.slobodyanuk.transitionviewanimation.view.component.phone.PhoneSurfaceView;

import java.util.LinkedList;

/**
 * Created by Sergiy on 09.12.2016.
 */

public class SurfaceViewThread extends Thread {

    private boolean isRunning;
    private PhoneSurfaceView mPhoneSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas = null;
    private boolean stop;
    private long beforeTime;
    private long timeThisFrame;

    LinkedList<Long> times = new LinkedList<Long>(){{
        add(System.nanoTime());
    }};
    private final int MAX_SIZE = 100;
    private final double NANOS = 1000000000.0;

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

    private double fps() {
        long lastTime = System.nanoTime();
        double difference = (lastTime - times.getFirst()) / NANOS;
        times.addLast(lastTime);
        int size = times.size();
        if (size > MAX_SIZE) {
            times.removeFirst();
        }
        return difference > 0 ? times.size() / difference : 0.0;
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
                            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//
//                            synchronized (mPhoneSurfaceView.getHolder()) {
                                beforeTime = System.currentTimeMillis();
                                mPhoneSurfaceView.move(timeThisFrame);
                                mPhoneSurfaceView.draw(mCanvas);
                                timeThisFrame = System.currentTimeMillis() - beforeTime;
//                            }
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
