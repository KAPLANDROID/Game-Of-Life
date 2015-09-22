package com.kaplandroid.gameoflife.model;

import com.kaplandroid.gameoflife.enums.CellState;
import com.kaplandroid.gameoflife.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public abstract class Cell extends Observer {

    private int aliveNeighboursCount;

    protected String cellSymbol;

    private CellState currentState;

    private boolean stateChanged;
    private List<Observer> observers;


    // Constructor
    public Cell(CellState currentState) {
        this.stateChanged = false;
        this.currentState = currentState;
        this.observers = new ArrayList<Observer>(8);

        // TODO

    }


    public String getCellSymbol() {
        return cellSymbol;
    }

    public CellState getCurrentState() {
        return currentState;
    }

    public int getAliveNeighboursCount() {
        return aliveNeighboursCount;
    }

    public boolean isStateChanged() {
        return stateChanged;
    }

    public void setStateChanged(boolean stateChanged) {
        this.stateChanged = stateChanged;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

}
