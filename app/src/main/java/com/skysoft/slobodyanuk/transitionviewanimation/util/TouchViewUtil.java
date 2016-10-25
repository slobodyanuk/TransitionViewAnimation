package com.skysoft.slobodyanuk.transitionviewanimation.util;

import android.util.DisplayMetrics;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static com.thefinestartist.utils.service.ServiceUtil.getWindowManager;

/**
 * Created by Serhii Slobodyanuk on 18.10.2016.
 */
public class TouchViewUtil {

    public static double distanceX(float coordinate1, float coordinate2) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double xDist = Math.pow(Math.abs(coordinate1 - coordinate2) / dm.xdpi, 2);
        return Math.sqrt(xDist) * dm.xdpi;
    }

    public static double distanceY(float coordinate1, float coordinate2) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double yDist = Math.pow(Math.abs(coordinate1 - coordinate2) / dm.ydpi, 2);
        return Math.sqrt(yDist) * dm.ydpi;
    }

    public static float distance(float coordinate1, float coordinate2) {
        float dc = (coordinate2 > coordinate1) ? coordinate2 - coordinate1 : coordinate1 - coordinate2;
        Log.d(TAG, "distance: "  + dc);
        return dc;
    }

}
