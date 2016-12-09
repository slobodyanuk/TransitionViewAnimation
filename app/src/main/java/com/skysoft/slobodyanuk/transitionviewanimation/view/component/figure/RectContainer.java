package com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.skysoft.slobodyanuk.transitionviewanimation.view.component.Drawable;

import static com.skysoft.slobodyanuk.transitionviewanimation.view.component.ViewConstants.RECT_CONTAINER_RADIUS;
import static com.skysoft.slobodyanuk.transitionviewanimation.view.component.ViewConstants.RECT_HEIGHT_PADDING;
import static com.skysoft.slobodyanuk.transitionviewanimation.view.component.ViewConstants.RECT_WIDTH_PADDING;

/**
 * Created by Sergiy on 08.12.2016.
 */
public class RectContainer implements Drawable {

    private int mWidth;
    private int mHeight;

    private int top;
    private int bottom;
    private int left;
    private int right;

    private float roundX;
    private int roundY;

    private Paint mPaint = new Paint();

    public RectContainer(int mWidth, int mHeight) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        calculateViews();
    }

    private void calculateViews() {
        left = RECT_WIDTH_PADDING;
        top = 0;
        right = mWidth - RECT_WIDTH_PADDING;
        bottom = (int) (mHeight * RECT_HEIGHT_PADDING);
        roundX = mWidth * RECT_CONTAINER_RADIUS;
        roundY = mHeight / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#1Affffff"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);

        Rect rect = new Rect(left, top, right, bottom);

        RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, roundX, roundY, mPaint);
    }
}
