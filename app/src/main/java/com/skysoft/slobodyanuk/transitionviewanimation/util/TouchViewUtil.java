package com.skysoft.slobodyanuk.transitionviewanimation.util;

import android.content.res.Resources;

/**
 * Created by Serhii Slobodyanuk on 18.10.2016.
 */
public class TouchViewUtil {

    public static float distance(Resources resources, float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(resources, distanceInPx);
    }

    private static float pxToDp(Resources resources, float px) {
        return px / resources.getDisplayMetrics().density;
    }

}
