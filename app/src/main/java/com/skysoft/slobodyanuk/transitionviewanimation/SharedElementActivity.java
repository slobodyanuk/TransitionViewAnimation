package com.skysoft.slobodyanuk.transitionviewanimation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by Serhii Slobodyanuk on 12.10.2016.
 */
public class SharedElementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildSharedElementTransitions();
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(getIntent().getStringExtra("url")))
                    .commit();
        }
    }

    private void buildSharedElementTransitions() {
        TransitionSet enterTransition = new TransitionSet();
        enterTransition.addTransition(new ChangeBounds());
        enterTransition.addTransition(new ChangeClipBounds());
        enterTransition.addTransition(new ChangeImageTransform());
        enterTransition.addTransition(new ChangeTransform());

        getWindow().setSharedElementEnterTransition(enterTransition);
        getWindow().setSharedElementExitTransition(enterTransition);
        getWindow().setSharedElementReturnTransition(enterTransition);
        getWindow().setSharedElementReenterTransition(enterTransition);
    }


}
