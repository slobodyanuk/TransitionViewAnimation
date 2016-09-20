package com.skysoft.slobodyanuk.transitionviewanimation;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * Created by Serhii Slobodyanuk on 19.09.2016.
 */
public class DetailsTransition extends TransitionSet {
    public DetailsTransition() {
        init();
    }

    /**
     * This constructor allows us to use this transition in XML
     */
    public DetailsTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}
