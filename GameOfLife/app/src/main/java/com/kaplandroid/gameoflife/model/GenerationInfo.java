package com.kaplandroid.gameoflife.model;

import com.kaplandroid.gameoflife.model.base.BaseCell;

/**
 * Created by KAPLANDROID on 25.09.15.
 * kaplandroid@omerkaplan.com
 */
public class GenerationInfo {

    private BaseCell cell;

    private int liveNeighboursCount;

    //

    //

    //

    public BaseCell getCell() {
        return cell;
    }

    public void increaseLiveNeighboursCount() {
        liveNeighboursCount++;
    }

    public void decreaseLiveNeighboursCount() {
        liveNeighboursCount--;
    }

    public int getLiveNeighboursCount() {
        return liveNeighboursCount;
    }


    public void setCell(BaseCell cell) {
        this.cell = cell;
    }

    public void setLiveNeighboursCount(int liveNeighboursCount) {
        this.liveNeighboursCount = liveNeighboursCount;
    }
}
