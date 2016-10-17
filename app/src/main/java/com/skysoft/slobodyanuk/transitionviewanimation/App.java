package com.skysoft.slobodyanuk.transitionviewanimation;

import android.app.Application;

import com.thefinestartist.Base;

/**
 * Created by Serhii Slobodyanuk on 13.10.2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Base.initialize(this);
    }
}
