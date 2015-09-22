package com.kaplandroid.gameoflife.observer;

/**
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public abstract class Observer {

    // subject to observe
    protected Subject subject;

    // method to update the observer, used by subject
    public abstract void update();

}
