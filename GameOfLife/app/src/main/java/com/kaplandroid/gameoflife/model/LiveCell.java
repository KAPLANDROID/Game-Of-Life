package com.kaplandroid.gameoflife.model;

import com.kaplandroid.gameoflife.GameData;
import com.kaplandroid.gameoflife.enums.CellState;

/**
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public class LiveCell extends  Cell{

    public LiveCell() {
        super(CellState.CELL_STATE_LIVE);

        cellSymbol = GameData.getInstance().getLiveCellSymbol();


    }


    @Override
    public void update() {

    }
}
