package com.skysoft.slobodyanuk.transitionviewanimation.view.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by Serhii Slobodyanuk on 11.10.2016.
 */
public class ClockArrowView extends View {

    private Paint mPaint = new Paint();
    private ViewGroup mContainer;
    private RotateAnimation rotate;
    private Canvas mCanvas;
    private int pivotX;
    private int pivotY;

    public ClockArrowView(Context context, ViewGroup mContainer) {
        super(context);
        this.mContainer = mContainer;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        if (rotate == null){
            pivotX = mCanvas.getWidth() / 2;
            pivotY = mCanvas.getHeight() / 2;
            createAnimation();
        }

        int centerX = (int) (mContainer.getX() + mContainer.getWidth() / 2);
        int centerY = (int) (mContainer.getY() + mContainer.getHeight() / 2);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2.5f);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        int radius = 25;
        mCanvas.drawLine(centerX, centerY, centerX, (centerY - radius) + radius * 0.15f, mPaint);

    }

    public RotateAnimation createAnimation() {
        rotate = new RotateAnimation(0, 360, pivotX, pivotY);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setStartOffset(0);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());

        startAnimation(rotate);
        return rotate;
    }

}
