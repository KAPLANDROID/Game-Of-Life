package com.kaplandroid.gameoflife.model.base;

import com.kaplandroid.gameoflife.enums.CellState;
import com.kaplandroid.gameoflife.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public abstract class BaseCell {

    private String cellSymbol;

    private CellState currentState;


    // Constructor
    public BaseCell(CellState currentState, String cellSymbol) {
        this.currentState = currentState;
        this.cellSymbol = cellSymbol;

        // TODO

    }

    public String getCellSymbol() {
        return cellSymbol;
    }

    public CellState getCurrentState() {
        return currentState;
    }


    /**
     *
     * @param liveNeighbourCount Live Neighbour cell Count
     * @return <b>true</b> if cell is live in next generation
     * <br/> <b>false</b> if cell is dead in next generation
     *
     */
    public abstract boolean applyRuleForNextGeneration(int liveNeighbourCount);

}
