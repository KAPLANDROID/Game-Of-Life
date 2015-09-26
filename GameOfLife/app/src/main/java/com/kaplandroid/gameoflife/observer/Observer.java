package com.kaplandroid.gameoflife.observer;

import com.kaplandroid.gameoflife.enums.CellState;

/**
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public abstract class Observer {


    // method to update the observer, used by subject
    public abstract void update(CellState state,boolean isUpdateCurrent);

}
