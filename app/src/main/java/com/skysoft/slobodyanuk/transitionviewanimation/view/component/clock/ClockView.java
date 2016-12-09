package com.skysoft.slobodyanuk.transitionviewanimation.view.component.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Serhii Slobodyanuk on 11.10.2016.
 */
public class ClockView extends View {

    private Paint mPaint = new Paint();
    private ViewGroup mContainer;

    public ClockView(Context context, ViewGroup mContainer) {
        super(context);
        this.mContainer = mContainer;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = (int) (mContainer.getX() + mContainer.getWidth() / 2);
        int centerY = (int) (mContainer.getY() + mContainer.getHeight() / 2);

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2.5f);
        mPaint.setColor(Color.BLACK);
        int radius = 25;
        canvas.drawRect(centerX - 3, centerY - radius - 8, centerX + 3, centerY - radius - 5, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPoint(centerX, centerY, mPaint);
        canvas.drawLine(centerX, centerY, centerX, (centerY - radius) + radius * 0.35f, mPaint);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius - 5, mPaint);
        mPaint.setStrokeWidth(2);
        canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + 5, mPaint);
        canvas.drawLine(centerX, centerY + radius, centerX, centerY + radius - 5, mPaint);
        canvas.drawLine(centerX - radius, centerY, centerX - radius + 5, centerY, mPaint);
        canvas.drawLine(centerX + radius, centerY, centerX + radius - 5, centerY, mPaint);

    }

}
