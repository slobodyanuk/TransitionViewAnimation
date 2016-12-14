package com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.skysoft.slobodyanuk.transitionviewanimation.util.MathUtils;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.Drawable;

import static com.skysoft.slobodyanuk.transitionviewanimation.view.component.ViewConstants.RECT_CONTAINER_RADIUS;
import static com.skysoft.slobodyanuk.transitionviewanimation.view.component.ViewConstants.RECT_WIDTH_PADDING;

/**
 * Created by Sergiy on 08.12.2016.
 */
public class RectContainer implements Drawable {

    private int mWidth;
    private int mHeight;

    public int top;
    public int bottom;
    public int left;
    public int right;

    private float radius;
    private double radiusLength;
    private float leftCenterX;
    private float rightCenterX;
    private float centerY;

    private Paint mPaint = new Paint();
    private int color;
    private RectF rectF;

    public RectContainer(int mWidth, int mHeight) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        calculateViews();
    }

    private void calculateViews() {
        left = RECT_WIDTH_PADDING;
        top = 0;
        right = mWidth - RECT_WIDTH_PADDING;
        bottom = mHeight;
        radius = (mWidth - RECT_WIDTH_PADDING)* RECT_CONTAINER_RADIUS ;
        color = Color.parseColor("#1Affffff");

        radiusLength = MathUtils.calcDistance(mWidth - radius - RECT_WIDTH_PADDING, getCenterY(), right, getCenterY());
        centerY = mHeight / 2;
        leftCenterX = radius + RECT_WIDTH_PADDING;
        rightCenterX = mWidth - radius - RECT_WIDTH_PADDING;

        Rect rect = new Rect(left, top, right, bottom);
        rectF = new RectF(rect);
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, radius, radius, mPaint);
    }

    public float getRightCenterX(){
        return rightCenterX;
    }

    public float getLeftCenterX(){
        return leftCenterX;
    }

    public float getCenterY(){
        return centerY;
    }

    public float getRadius(){
        return radius;
    }

    public double getRadiusLength(){
        return radiusLength;
    }
}
