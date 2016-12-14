package com.skysoft.slobodyanuk.transitionviewanimation.util;

import com.skysoft.slobodyanuk.transitionviewanimation.view.component.figure.RectContainer;

/**
 * Created by Sergiy on 12.12.2016.
 */

public class MathUtils {

    private static final String TAG = MathUtils.class.getSimpleName();

    private static float rectCenterX;

    private static double[][] mTouchPoints = new double[2][2];
    private static double[] mNewCenter = new double[2];

    public static double calcAngle(float x, float y) {
        if (x == 0 && y == 0)
            return 0;
        else if (x >= 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x));
        else if (x < 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if (x < 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if (x >= 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 360;
        return 0;
    }

    /**
     * Return array of touch points.
     *
     * Touch point 1 (double[0][0], double[0][1])
     * and
     * Touch point 2 (double[1][0], double[1][1])
     *
     * @param circleX      a circle X center
     * @param circleY      a circle Y center
     * @param rectView     a rectangle container
     * @param circleRadius a circle radius
     * @return the array of points.
     */
    public static double[] getTouchPoints(float circleX, float circleY, RectContainer rectView, float circleRadius) {

        double centerDistance = calcDistance(circleX, circleY, rectCenterX, rectView.getCenterY());

        double a = ((Math.pow(rectView.getRadiusLength(), 2) - Math.pow(circleRadius, 2) +
                Math.pow(centerDistance, 2)) / (2 * centerDistance));

        double h = Math.sqrt((Math.pow(rectView.getRadiusLength(), 2) - Math.pow(a, 2)));
        double pX = rectCenterX + a * (circleX - rectCenterX) / centerDistance;
        double pY = rectView.getCenterY() + a * (circleY - rectView.getCenterY()) / centerDistance;

        calculateTouchPoint(pX, pY, circleX, circleY, h,
                rectCenterX, rectView.getCenterY(), centerDistance);

        return getCalculateCenter(rectView, circleRadius);
    }

    public static boolean hasTouchPoint(float currentX, float currentY, RectContainer rectView, float circleRadius) {

        rectCenterX = (currentX > rectView.getRightCenterX()) ?
                rectView.getRightCenterX() : rectView.getLeftCenterX();

        double centerDistance = calcDistance(currentX, currentY, rectCenterX, rectView.getCenterY());

        boolean externalTouch = currentX > rectView.getRightCenterX() || currentX < rectView.getLeftCenterX();

        return centerDistance < rectView.getRadius() + circleRadius &&
                centerDistance > rectView.getRadius() - circleRadius && externalTouch;
    }

    private static void calculateTouchPoint(double pX, double pY,
                                            double circleX, double circleY, double h,
                                            double rectX, double rectY, double distance) {

        mTouchPoints[0][0] = pX + h * (circleY - rectY) / distance;
        mTouchPoints[0][1] = pY - h * (circleX - rectX) / distance;
        mTouchPoints[1][0] = pX - h * (circleY - rectY) / distance;
        mTouchPoints[1][1] = pY + h * (circleX - rectX) / distance;

    }

    private static double[] getCalculateCenter(RectContainer rectView, float circleRadius) {

        float cX = (float) ((mTouchPoints[0][0] + mTouchPoints[1][0]) / 2);
        float cY = (float) ((mTouchPoints[0][1] + mTouchPoints[1][1]) / 2);

        double distanceC = calcDistance(rectCenterX, rectView.getCenterY(), cX, cY);
        double distanceM = rectView.getRadiusLength() - distanceC;

        double coef = distanceC / distanceM;

        double mX = (cX - rectCenterX + coef * cX) / coef;
        double mY = (cY - rectView.getCenterY() + coef * cY) / coef;

        double circlePoint = rectView.getRadiusLength() - circleRadius;

        coef = circlePoint / circleRadius;

        mNewCenter[0] = (rectCenterX + coef * mX) / (1 + coef);
        mNewCenter[1] = (rectView.getCenterY() + coef * mY) / (1 + coef);

        return mNewCenter;
    }

    public static double calcDistance(float currentX, float currentY, float initX, float initY) {
        return Math.sqrt(Math.pow(currentX - initX, 2) + Math.pow(currentY - initY, 2));
    }


}
