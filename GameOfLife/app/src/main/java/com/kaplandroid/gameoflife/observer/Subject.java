package com.kaplandroid.gameoflife.observer;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */

public class Subject {

    private List<Observer> observers = new ArrayList<Observer>();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }

    }

    public void deAttach(Observer observer) {
        observers.remove(observer);

    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
