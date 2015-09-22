package com.kaplandroid.gameoflife;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.kaplandroid.gameoflife.model.Cell;
import com.kaplandroid.gameoflife.model.DeadCell;
import com.kaplandroid.gameoflife.model.LiveCell;

import java.util.Random;

/**
 * @author KAPLANDROID
 */
public class MainActivity extends ActionBarActivity {

    TextView tvGameGrid;
    TextView tvLog;

    public static boolean isRunningFlag = false;


    private final int rowCount = GameData.getInstance().getRowCount();
    private final int columnCount = GameData.getInstance().getColumnCount();

    Cell[][] gameTable = new Cell[rowCount][columnCount];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //

        tvGameGrid = (TextView) findViewById(R.id.tvGameGrid);
        tvLog = (TextView) findViewById(R.id.tvLog);

        //


        for (int iRow = 0; iRow < rowCount; iRow++) {
            for (int iCol = 0; iCol < columnCount; iCol++) {

                int rnd = new Random().nextInt();

                if (rnd % 2 == 0) {
                    gameTable[iRow][iCol] = new DeadCell();
                } else {
                    gameTable[iRow][iCol] = new LiveCell();
                }


            }
        }


        showGameTableOnTextView(tvGameGrid);

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
                if (selectedIndex == (20 * (line + 1))) {
                    // Cell is not selected
                    tvLog.setText("");
                } else {
                    // Cell is selected
                    if (BuildConfig.DEBUG) {
                        tvLog.setText(selectedIndex + " " + offset + " " + line + " " + getCharWidth());
                    }

                    doProcessForSelectedIndex();
                }

                return false;
            }
        });


    }

    private void doProcessForSelectedIndex() {
        //TODO
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


    private void showGameTableOnTextView(TextView tv) {

        StringBuilder sb = new StringBuilder();

        for (int iRow = 0; iRow < rowCount; iRow++) {
            for (int iCol = 0; iCol < columnCount; iCol++) {

                sb.append(gameTable[iRow][iCol].getCellSymbol());


            }
            sb.append("\n");
        }
        sb.deleteCharAt(sb.lastIndexOf("\n"));

        tv.setText(sb.toString());

    }

}
