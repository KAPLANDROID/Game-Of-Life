package com.kaplandroid.gameoflife.model;

import com.kaplandroid.gameoflife.enums.CellState;
import com.kaplandroid.gameoflife.model.base.BaseCell;
import com.kaplandroid.gameoflife.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KAPLANDROID on 23.09.15.
 * kaplandroid@omerkaplan.com
 */
public class TableItem extends Observer {

    private GenerationInfo currentGenerationInfo;
    private GenerationInfo nextGenerationInfo;


    private List<Observer> observers = new ArrayList<>();

    public TableItem(BaseCell cell) {

        this.observers = new ArrayList<>(8);
        this.nextGenerationInfo = new GenerationInfo();
        this.currentGenerationInfo = new GenerationInfo();

        this.currentGenerationInfo.setCell(cell);

    }


    public void updateNextGeneration(boolean isLive) {

        if (isLive) {
            // Next Generation is Live

            nextGenerationInfo.setCell(LiveCell.getInstance());

            if (getCurrentGenerationInfo().getCell().getCurrentState() == CellState.CELL_STATE_DEAD) {
                notifyAllObserversAboutNextGen(CellState.CELL_STATE_LIVE);// Notify Observers
            } else {
                notifyAllObserversAboutNextGen(CellState.CELL_STATE_NO_CHANGE);// Notify Observers
            }

        } else {
            // Next Generation is Dead

            nextGenerationInfo.setCell(DeadCell.getInstance());

            if (getCurrentGenerationInfo().getCell().getCurrentState() == CellState.CELL_STATE_LIVE) {
                notifyAllObserversAboutNextGen(CellState.CELL_STATE_DEAD);// Notify Observers
            } else {
                notifyAllObserversAboutNextGen(CellState.CELL_STATE_NO_CHANGE);// Notify Observers
            }

        }

    }

    // TODO control
    public void reverseCellState() {
        if (currentGenerationInfo.getCell().getCurrentState() == CellState.CELL_STATE_DEAD) {
            currentGenerationInfo.setCell(LiveCell.getInstance());
        } else {
            currentGenerationInfo.setCell(DeadCell.getInstance());
        }

        notifyAllObserversAboutCurrentGen(currentGenerationInfo.getCell().getCurrentState());

    }


    public void attach(TableItem observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);

            if (observer.getCurrentGenerationInfo().getCell().getCurrentState() == CellState.CELL_STATE_LIVE) {
                currentGenerationInfo.increaseLiveNeighboursCount();
            }

        }

    }

    @SuppressWarnings("unused")
    public void deAttach(Observer observer) {
        observers.remove(observer);

    }

    /**
     * Notifies neighbours about cell state
     */
    public void notifyAllObserversAboutNextGen(CellState state) {
        for (Observer observer : observers) {
            observer.update(state, false);
        }

    }


    /**
     * When user changes cell's current state neighbours must update current live negihtbour count
     * Notifies neighbours about cell state
     */
    public void notifyAllObserversAboutCurrentGen(CellState state) {
        for (Observer observer : observers) {
            observer.update(state, true);
        }

    }

    /**
     * Receives
     */
    @Override
    public void update(CellState state, boolean isUpdateCurrentGeneration) {

        if (isUpdateCurrentGeneration) {
            if (state == CellState.CELL_STATE_LIVE) {
                currentGenerationInfo.increaseLiveNeighboursCount();
                nextGenerationInfo.increaseLiveNeighboursCount();
            } else if (state == CellState.CELL_STATE_DEAD) {
                currentGenerationInfo.decreaseLiveNeighboursCount();
                nextGenerationInfo.decreaseLiveNeighboursCount();
            }

        } else {
            if (state == CellState.CELL_STATE_LIVE) {
                nextGenerationInfo.increaseLiveNeighboursCount();
            } else if (state == CellState.CELL_STATE_DEAD) {
                nextGenerationInfo.decreaseLiveNeighboursCount();
            }

        }
    }

    public GenerationInfo getNextGenerationInfo() {
        return nextGenerationInfo;
    }

    public GenerationInfo getCurrentGenerationInfo() {
        return currentGenerationInfo;
    }

}