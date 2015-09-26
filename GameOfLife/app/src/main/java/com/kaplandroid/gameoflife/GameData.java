package com.kaplandroid.gameoflife;

import com.kaplandroid.gameoflife.model.TableItem;

/**
 * Singleton Class
 * <p/>
 * <p/>
 * Created by KAPLANDROID on 21/09/15.
 * kaplandroid@omerkaplan.com
 */
public class GameData {

    private static GameData ref;

    private final int rowCount = 20;
    private final int columnCount = 20;

    private String deadCellSymbol = "-";
    private String liveCellSymbol = "+";

    private long delayBetweenFrames = 250; // in milliseconds

    private final String gameServicePrintResultBroadcast = "gameServicePrintResultBroadcast";
    private final String gameServiceResumePauseBroadcast = "gameServiceResumePauseBroadcast";

    private final String KEY_GAME_RESUME = "KEY_GAME_RESUME";
    private final String KEY_GAME_GENERATION_ID= "KEY_GAME_GENERATION_ID";

    private TableItem[][] gameTable = new TableItem[rowCount][columnCount];

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

    public TableItem[][] getGameTable() {
        return gameTable;
    }

    public long getDelayBetweenFrames() {
        return delayBetweenFrames;
    }

    public void setDelayBetweenFrames(long delayBetweenFrames) {
        this.delayBetweenFrames = delayBetweenFrames;
    }

    public String getGameServicePrintResultBroadcast() {
        return gameServicePrintResultBroadcast;
    }

    public String getGameServiceResumePauseBroadcast() {
        return gameServiceResumePauseBroadcast;
    }

    public String getKEY_GAME_RESUME() {
        return KEY_GAME_RESUME;
    }

    public String getKEY_GAME_GENERATION_ID() {
        return KEY_GAME_GENERATION_ID;
    }
}
