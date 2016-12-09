package com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.skysoft.slobodyanuk.transitionviewanimation.data.PhoneState;
import com.skysoft.slobodyanuk.transitionviewanimation.view.component.Drawable;

import static com.skysoft.slobodyanuk.transitionviewanimation.view.component.ViewConstants.RECT_HEIGHT_PADDING;

/**
 * Created by Sergiy on 08.12.2016.
 */

public class PhoneCircle implements Drawable {

    private int mWidth;
    private int mHeight;
    private Paint mPaint = new Paint();
    private Bitmap mIconCall;
    private PhoneState mState;

    private float centerX;
    private float centerBitmapX;
    private float centerY;
    private float centerBitmapY;

    private float radius;

    public PhoneCircle(Context context, int mWidth, int mHeight, PhoneState state) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.mState = state;

        radius = mHeight / 3;
        mIconCall = BitmapFactory.decodeResource(context.getApplicationContext().getResources(),
                android.R.drawable.ic_menu_call);

        setCenterX(mWidth / 2);
        setCenterY(
                mHeight * RECT_HEIGHT_PADDING / 2);
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#016fff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        canvas.drawCircle(centerX, centerY, radius, mPaint);

        canvas.drawBitmap(mIconCall, centerBitmapX, centerBitmapY, mPaint);
    }

    public boolean isTouched(float xTouch, float yTouch) {
        float touch = (float) Math.sqrt(Math.pow((getCenterX() - xTouch), 2) + Math.pow((getCenterY() - yTouch), 2));
        return touch <= getRadius() * 1.5f;
    }

    public void offset(float x, float y) {
        centerX += x;
        centerY += y;
        centerBitmapX += x;
        centerBitmapY += y;
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
