package com.kaplandroid.gameoflife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaplandroid.gameoflife.model.DeadCell;
import com.kaplandroid.gameoflife.model.LiveCell;
import com.kaplandroid.gameoflife.model.TableItem;
import com.kaplandroid.gameoflife.service.GameBGService;

import java.util.Random;

/**
 * @author KAPLANDROID
 */
public class
        GameActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView tvGameGrid;
    private TextView tvGenerationId;

    private Button btnStartStop;
    private Button btnClearTable;

    //In order not to use method tables every time, I pass singleton references to variables.
    private final int rowCount = GameData.getInstance().getRowCount();
    private final int columnCount = GameData.getInstance().getColumnCount();

    private TableItem[][] gameTable = GameData.getInstance().getGameTable();

    private Intent gameServiceIntent;

    private int generationId;

    private boolean isGameRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.d("Activity State", "onCreate");
        //

        tvGameGrid = (TextView) findViewById(R.id.tvGameGrid);
        tvGenerationId = (TextView) findViewById(R.id.tvGenerationId);

        btnStartStop = (Button) findViewById(R.id.btnStartStop);
        btnClearTable = (Button) findViewById(R.id.btnClearTable);

        //

        btnStartStop.setOnClickListener(this);
        btnClearTable.setOnClickListener(this);

        //


        gameServiceIntent = new Intent(this, GameBGService.class);

        if (savedInstanceState == null) {

            fillTableRandomly(); // Fill the table randomly

            doObserverMapingsAndInitializeNextGeneration();

//            printOutLiveNeighbors(); // Write LOG

        } else {

            isGameRunning = savedInstanceState.getBoolean(GameData.getInstance().getKEY_GAME_RESUME());
            generationId = savedInstanceState.getInt(GameData.getInstance().getKEY_GAME_GENERATION_ID());

            gameServiceIntent.putExtra(GameData.getInstance().getKEY_GAME_GENERATION_ID(), generationId);
            gameServiceIntent.putExtra(GameData.getInstance().getKEY_GAME_RESUME(), isGameRunning);

            tvGenerationId.setText(String.valueOf(generationId));
        }


        startService(gameServiceIntent);

        showGameTableOnTextView(); // print table to screen

        setGameTableTouchEvent();


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(GameData.getInstance().getKEY_GAME_RESUME(), isGameRunning);
        savedInstanceState.putInt(GameData.getInstance().getKEY_GAME_GENERATION_ID(), generationId);

        super.onSaveInstanceState(savedInstanceState);

    }

    /**
     * Fill the table randomly
     */
    private void fillTableRandomly() {

        for (int iRow = 0; iRow < rowCount; iRow++) {
            for (int iCol = 0; iCol < columnCount; iCol++) {

                int rnd = new Random().nextInt();

                if (rnd % 5 != 0) {
                    gameTable[iRow][iCol] = new TableItem(DeadCell.getInstance());
                } else {
                    gameTable[iRow][iCol] = new TableItem(LiveCell.getInstance());
                }

            }
        }
    }

    public void printOutLiveNeighbors() {

        for (int iRow = 0; iRow < rowCount; iRow++) {

            String rowS = "";

            for (int iCol = 0; iCol < columnCount; iCol++) {

                rowS += gameTable[iRow][iCol].getCurrentGenerationInfo().getLiveNeighboursCount();
                rowS += ", ";
            }

            Log.i("Live Neighbours", rowS);

        }

    }

    /**
     * Maps every items as an Observer to their neighbours
     */
    private void doObserverMapingsAndInitializeNextGeneration() {
        for (int iRow = 0; iRow < rowCount; iRow++) { // iRow: row Index
            for (int iCol = 0; iCol < columnCount; iCol++) { // iCol: column Index

                TableItem currentItem = gameTable[iRow][iCol];

                // Register Current Item To Neighbours
                for (int r = -1; r <= 1; r++) { // -1 0 1
                    /** neighbourRowIndex*/
                    int nbrRow = iRow + r;
                    if (nbrRow >= 0 && nbrRow < rowCount) {

                        for (int c = -1; c <= 1; c++) { // -1 0 1
                            /** neighbourColumnIndex*/
                            int nbrCol = iCol + c;
                            if (nbrCol >= 0 && nbrCol < columnCount) {

                                if (!(r == 0 && c == 0)) { // Not The Cell Itself
                                    // Attaching Observer to Neighbours
                                    gameTable[nbrRow][nbrCol].attach(currentItem);

                                }
                            }
                        }
                    }
                }

                //
                //

                //

            }
        }

        // Initialize NextGeneration
        for (int iRow = 0; iRow < rowCount; iRow++) { // iRow: row Index
            for (int iCol = 0; iCol < columnCount; iCol++) { // iCol: column Index

                TableItem currentItem = gameTable[iRow][iCol];

                currentItem.getNextGenerationInfo().setCell(currentItem.getCurrentGenerationInfo().getCell());
                currentItem.getNextGenerationInfo().setLiveNeighboursCount(currentItem.getCurrentGenerationInfo().getLiveNeighboursCount());
            }
        }


    }

    public void showGameTableOnTextView() {

        StringBuilder sb = new StringBuilder();

        for (int iRow = 0; iRow < rowCount; iRow++) {
            for (int iCol = 0; iCol < columnCount; iCol++) {

                sb.append(gameTable[iRow][iCol].getCurrentGenerationInfo().getCell().getCellSymbol());


            }
            sb.append("\n");
        }
        sb.deleteCharAt(sb.lastIndexOf("\n"));

        tvGameGrid.setText(sb.toString());

    }

    private void setGameTableTouchEvent() {
        tvGameGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Layout layout = ((TextView) v).getLayout();

                int line = 0;
                int offset = 0;

                if (layout != null) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    line = layout.getLineForVertical(y);
                    offset = layout.getOffsetForHorizontal(line, x - getCharWidth() / 2);

                }

                // TextView adds new index to end of all lines.
                // We need to re-calculate the Array index
                int selectedIndex = offset - line;
                // check for end of line spare index
                if (selectedIndex != (columnCount * (line + 1))) {
                    // Cell is selected

                    int row = selectedIndex / rowCount;
                    int col = selectedIndex - (row * rowCount);

                    doProcessForSelectedIndex(row, col);


                } else {
                    // Cell is not selected
                }

                return false;
            }
        });
    }

    private void doProcessForSelectedIndex(int row, int col) {
        TableItem selectedItem = gameTable[row][col];
        selectedItem.reverseCellState();
        showGameTableOnTextView();
    }

    /**
     * @returns single character width
     */
    private int getCharWidth() {

        char[] text = {'@'};

        Rect bounds = new Rect();
        Paint textPaint = tvGameGrid.getPaint();
        textPaint.getTextBounds(text, 0, text.length, bounds);
        Log.i("height", "" + bounds.height());
        return bounds.width();
    }


    /**
     * Broadcast Receiver for GameBGService result
     */
    private BroadcastReceiver mResultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            generationId = intent.getIntExtra(GameData.getInstance().getKEY_GAME_GENERATION_ID(), -1);

            String generation = String.valueOf(generationId);

            tvGenerationId.setText(generation);

//            Log.d("ACTIVITY", "ACTIVITY - RECEIVED: " + generation);

            showGameTableOnTextView();

//            printOutLiveNeighbors();


        }

    };

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Activity State", "onResume");

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mResultReceiver, new IntentFilter(GameData.getInstance().getGameServicePrintResultBroadcast()));

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Activity State", "onPause");

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mResultReceiver);

    }

    @Override
    protected void onDestroy() {
        stopService(gameServiceIntent);
        super.onDestroy();
        Log.d("Activity State", "onDestroy");

    }

    @Override
    public void onClick(View v) {
        if (v == btnStartStop) {

            if (!isGameRunning) {
                isGameRunning = true;
                Intent intent = new Intent(GameData.getInstance().getGameServiceResumePauseBroadcast());
                intent.putExtra(GameData.getInstance().getKEY_GAME_RESUME(), true);
                intent.putExtra(GameData.getInstance().getKEY_GAME_GENERATION_ID(), generationId);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

            } else {
                isGameRunning = false;
                Intent intent = new Intent(GameData.getInstance().getGameServiceResumePauseBroadcast());
                intent.putExtra(GameData.getInstance().getKEY_GAME_RESUME(), false);
                intent.putExtra(GameData.getInstance().getKEY_GAME_GENERATION_ID(), generationId);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

            }
        } else if (v == btnClearTable) {

            generationId = 0;

            if (isGameRunning) {
                isGameRunning = false;
                Intent intent = new Intent(GameData.getInstance().getGameServiceResumePauseBroadcast());
                intent.putExtra(GameData.getInstance().getKEY_GAME_RESUME(), false);
                intent.putExtra(GameData.getInstance().getKEY_GAME_GENERATION_ID(), generationId);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }

            // Clear Table
            for (int iRow = 0; iRow < rowCount; iRow++) { // iRow: row Index
                for (int iCol = 0; iCol < columnCount; iCol++) { // iCol: column Index
                    gameTable[iRow][iCol].getCurrentGenerationInfo().setCell(DeadCell.getInstance());
                    gameTable[iRow][iCol].getCurrentGenerationInfo().setLiveNeighboursCount(0);
                    gameTable[iRow][iCol].getNextGenerationInfo().setCell(DeadCell.getInstance());
                    gameTable[iRow][iCol].getNextGenerationInfo().setLiveNeighboursCount(0);

                }
            }

            tvGenerationId.setText(String.valueOf(generationId));

            showGameTableOnTextView();

        }

    }

}