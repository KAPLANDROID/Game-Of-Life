package com.kaplandroid.gameoflife.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.kaplandroid.gameoflife.GameData;
import com.kaplandroid.gameoflife.model.TableItem;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KAPLANDROID on 23.09.15.
 * kaplandroid@omerkaplan.com
 */
public class GameBGService extends Service {


    private TableItem[][] mGameTable = GameData.getInstance().getGameTable();

    private int generationId = 0;

    private Timer gameTimer;
    private TimerTask gameFrameTimerTask;

    protected static final String TAG = "GameBGService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "service onStartCommand");

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mResumePauseGameReceiver, new IntentFilter(GameData.getInstance().getGameServiceResumePauseBroadcast()));

        generationId = intent.getIntExtra(GameData.getInstance().getKEY_GAME_GENERATION_ID(), 0);

        if (intent.getBooleanExtra(GameData.getInstance().getKEY_GAME_RESUME(), false)) {
            processGame();
        }

        return super.onStartCommand(intent, flags, startId);
    }


    private void processGame() {

        Log.d(TAG, "processGame");

        stopTimer();

        gameTimer = new Timer();

        gameFrameTimerTask = new TimerTask() {
            @Override
            public void run() {

                generationId++;

                calculateNextGeneration();

                swapCurrentAndNextGeneration();

                Intent intent = new Intent(GameData.getInstance().getGameServicePrintResultBroadcast());
                intent.putExtra(GameData.getInstance().getKEY_GAME_GENERATION_ID(), generationId);
                LocalBroadcastManager.getInstance(GameBGService.this).sendBroadcast(intent);

            }
        };


        gameTimer.schedule(gameFrameTimerTask, GameData.getInstance().getDelayBetweenFrames(), GameData.getInstance().getDelayBetweenFrames());

    }


    /**
     * Broadcast Receiver for Resume or Pause the game
     */
    private BroadcastReceiver mResumePauseGameReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            generationId = intent.getIntExtra(GameData.getInstance().getKEY_GAME_GENERATION_ID(), 0);

            if (intent.getBooleanExtra(GameData.getInstance().getKEY_GAME_RESUME(), false)) {
                processGame();
            } else {
                stopTimer();
            }

        }

    };

    private void calculateNextGeneration() {
        for (int iRow = 0; iRow < mGameTable.length; iRow++) {
            for (int iCol = 0; iCol < mGameTable[0].length; iCol++) {

                TableItem currentItem = mGameTable[iRow][iCol];

                boolean isLive = currentItem.getCurrentGenerationInfo().getCell().applyRuleForNextGeneration(currentItem.getCurrentGenerationInfo().getLiveNeighboursCount());

                currentItem.updateNextGeneration(isLive);

            }
        }
    }

    private void swapCurrentAndNextGeneration() {
        for (int iRow = 0; iRow < mGameTable.length; iRow++) {
            for (int iCol = 0; iCol < mGameTable[0].length; iCol++) {

                TableItem currentItem = mGameTable[iRow][iCol];

                currentItem.getCurrentGenerationInfo().setCell(currentItem.getNextGenerationInfo().getCell());
                currentItem.getCurrentGenerationInfo().setLiveNeighboursCount(currentItem.getNextGenerationInfo().getLiveNeighboursCount());

            }
        }

    }

    @Override
    public void onDestroy() {

        Log.d("GAME_SERVICE State", "onDestroy");


        LocalBroadcastManager.getInstance(this).unregisterReceiver(mResumePauseGameReceiver);
        stopTimer();


        super.onDestroy();
    }

    private void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer = null;
        }
    }

}
