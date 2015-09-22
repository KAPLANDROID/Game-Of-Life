package com.kaplandroid.gameoflife.model;

import com.kaplandroid.gameoflife.GameData;
import com.kaplandroid.gameoflife.enums.CellState;

/**
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public class DeadCell extends Cell {


    public DeadCell() {
        super(CellState.CELL_STATE_DEAD);

        cellSymbol = GameData.getInstance().getDeadCellSymbol();

    }


    @Override
    public void update() {

    }

}
