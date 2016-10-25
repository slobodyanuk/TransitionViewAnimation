package com.skysoft.slobodyanuk.transitionviewanimation.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Serhii Slobodyanuk on 21.10.2016.
 */

public class RecyclerManager extends LinearLayoutManager {

    private boolean canScroll;

    public RecyclerManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean enabled){
        canScroll = enabled;
    }

    @Override
    public boolean canScrollVertically() {
        return canScroll;
    }
}
