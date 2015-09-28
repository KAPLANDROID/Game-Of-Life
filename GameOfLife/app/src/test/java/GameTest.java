import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Button;

import com.kaplandroid.gameoflife.BuildConfig;
import com.kaplandroid.gameoflife.GameActivity;
import com.kaplandroid.gameoflife.GameData;
import com.kaplandroid.gameoflife.R;
import com.kaplandroid.gameoflife.enums.CellState;
import com.kaplandroid.gameoflife.model.DeadCell;
import com.kaplandroid.gameoflife.model.TableItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by KAPLANDROID on 25.09.15.
 * kaplandroid@omerkaplan.com
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class GameTest {


    private GameActivity gmaeActivity;
    private Button btnStartStopTest;
    private Button btnClearTableTest;

    @Before
    public void setUp() throws Exception {
        gmaeActivity = Robolectric.setupActivity(GameActivity.class);
        btnStartStopTest = (Button) gmaeActivity.findViewById(R.id.btnStartStop);
        btnClearTableTest = (Button) gmaeActivity.findViewById(R.id.btnClearTable);

    }

    @Test
    public void shouldMainActivityNotBeNull() throws Exception {
        assertTrue(Robolectric.buildActivity(GameActivity.class).create().get() != null);

    }


    @Test
    public void resumePauseBroadcastWorkingTest() throws Exception {

        BroadcastReceiver mResumePauseGameReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                assertTrue(true);
            }
        };


        LocalBroadcastManager.getInstance(gmaeActivity).registerReceiver(
                mResumePauseGameReceiver, new IntentFilter(GameData.getInstance().getGameServiceResumePauseBroadcast()));

        btnStartStopTest.performClick();

    }

    @Test
    public void clearTableTest() throws Exception {

        btnClearTableTest.performClick();

        TableItem[][] gameTable = GameData.getInstance().getGameTable();
        int rows = gameTable.length;
        int columns = gameTable[0].length;

        boolean error = false;
        // Clear Table
        for (int iRow = 0; iRow < rows; iRow++) { // iRow: row Index
            for (int iCol = 0; iCol < columns; iCol++) { // iCol: column Index

                if (!(gameTable[iRow][iCol].getCurrentGenerationInfo().getCell().getCurrentState() == CellState.CELL_STATE_DEAD
                        && gameTable[iRow][iCol].getCurrentGenerationInfo().getLiveNeighboursCount() == 0)) {
                    assertFalse("Table Not Cleared Properly", true);
                    return;
                }
            }
        }
        assertTrue(true);
    }


}
