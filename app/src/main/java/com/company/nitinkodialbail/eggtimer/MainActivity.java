package com.company.nitinkodialbail.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    Button controllerBt;

    private void updateTimer(int progess){
        int minutes = (int)progess/60;
        int seconds = progess-minutes*60;
        String secondsStr = Integer.toString(seconds);
        if(seconds<=9){
            secondsStr = "0"+secondsStr;
        }

        timerTextView.setText(Integer.toString(minutes)+":"+secondsStr);
    }
    public void resetTimer(){
        countDownTimer.cancel();
        timerTextView.setText("0:30");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        counterIsActive = false;
        controllerBt.setText("Go!");

    }

    public void controlTimer(View view) {
        if(counterIsActive==false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerBt.setText("Stop!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 100) {
                @Override
                public void onTick(long milliseconds) {
                    updateTimer((int) milliseconds / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();
                    resetTimer();
                }
            }.start();
        }
        else{
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar =  (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerBt = (Button) findViewById(R.id.controllerButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progess, boolean b) {
                updateTimer(progess);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
