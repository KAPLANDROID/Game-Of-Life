package com.kaplandroid.gameoflife.model;

import com.kaplandroid.gameoflife.GameData;
import com.kaplandroid.gameoflife.enums.CellState;
import com.kaplandroid.gameoflife.model.base.BaseCell;

/**
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public class DeadCell extends BaseCell {

    private static DeadCell ref;

    public static DeadCell getInstance() {
        if (ref == null) {
            ref = new DeadCell();
        }
        return ref;
    }


    private DeadCell() {
        super(CellState.CELL_STATE_DEAD, GameData.getInstance().getDeadCellSymbol());


    }

    @Override
    public boolean applyRuleForNextGeneration(int liveNeighbourCount) {

        // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

        if (liveNeighbourCount == 3) {
            // by reproduction LIVE
            return true;
        } else {
            // stays  DEAD
            return false;
        }

    }
}
