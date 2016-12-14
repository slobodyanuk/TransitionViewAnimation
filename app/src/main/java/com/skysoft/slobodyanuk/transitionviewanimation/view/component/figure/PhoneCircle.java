package com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.skysoft.slobodyanuk.transitionviewanimation.data.PhoneState;
import com.skysoft.slobodyanuk.transitionviewanimation.util.MathUtils;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.Drawable;

/**
 * Created by Sergiy on 08.12.2016.
 */

public class PhoneCircle implements Drawable {

    private final int color;
    private Paint mPaint = new Paint();
    private Bitmap mIconCall;
    private PhoneState mState;

    private float centerX;
    private float initCenterX;
    private float initCenterY;
    private float centerBitmapX;
    private float centerY;
    private float centerBitmapY;

    private float radius;

    public PhoneCircle(Context context, int mWidth, int mHeight, PhoneState state) {
        this.mState = state;

        color = Color.parseColor("#016fff");
        radius = mHeight / 3;
        mIconCall = BitmapFactory.decodeResource(context.getApplicationContext().getResources(),
                android.R.drawable.ic_menu_call);

        initCenterX = mWidth / 2;
        initCenterY = mHeight / 2;

        setCenterX(initCenterX);
        setCenterY(initCenterY);
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        canvas.drawCircle(centerX, centerY, radius, mPaint);

        canvas.drawBitmap(mIconCall, centerBitmapX, centerBitmapY, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);

    }

    public boolean isTouched(float xTouch, float yTouch) {
        float touch = (float) Math.sqrt(Math.pow((getCenterX() - xTouch), 2) + Math.pow((getCenterY() - yTouch), 2));
        return touch <= getRadius() * 1.5f;
    }

    public void offset(float x, float y) {
        centerY += y;
        centerBitmapY += y;
        centerX += x;
        centerBitmapX += x;
    }

    public void checkRectRoundBorder() {
        if (MathUtils.hasTouchPoint(centerX, centerY,
                mState.getRectView(), getRadius())) {

            double[] mTouchPoints = MathUtils.getTouchPoints(centerX, centerY,
                    mState.getRectView(), getRadius());

            setCenterX((float) mTouchPoints[0]);
            setCenterY((float) mTouchPoints[1]);
        }
    }

    public float getInitCenterX() {
        return initCenterX;
    }

    public float getInitCenterY() {
        return initCenterY;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
        this.centerBitmapX = centerX - mIconCall.getWidth() / 2;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
        this.centerBitmapY = centerY - mIconCall.getHeight() / 2;
    }

    public float getRadius() {
        return radius;
    }
}
