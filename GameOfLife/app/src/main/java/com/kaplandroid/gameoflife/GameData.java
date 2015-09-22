package com.kaplandroid.gameoflife;

/**
 * Singleton Class
 * <p/>
 * <p/>
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public class GameData {

    private static GameData ref;

    private int rowCount = 20;
    private int columnCount = 20;

    private String deadCellSymbol = ".";
    private String liveCellSymbol = "o";

    private GameData() {
    }

    public static GameData getInstance() {
        if (ref == null) {
            ref = new GameData();
        }

        return ref;
    }


    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public String getDeadCellSymbol() {
        return deadCellSymbol;
    }

    public String getLiveCellSymbol() {
        return liveCellSymbol;
    }
}
