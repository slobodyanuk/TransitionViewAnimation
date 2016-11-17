package com.skysoft.slobodyanuk.transitionviewanimation.view.adapter.models;

/**
 * Created by Serhii Slobodyanuk on 27.10.2016.
 */

public class FoldItem {

    private String name;
    private boolean isSwiped;

    public FoldItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSwiped() {
        return isSwiped;
    }

    public void setSwiped(boolean swiped) {
        isSwiped = swiped;
    }
}
