package com.aharoldk.burncalories;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KillerActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.tvCountdown) TextView tvCountDown;
    @BindView(R.id.tvCountdownView) TextView tvCountdownView;
    @BindView(R.id.tvTotalCalories) TextView tvTotalCalories;
    @BindView(R.id.pauseView) LinearLayout pauseView;
    @BindView(R.id.stopView) LinearLayout stopView;
    @BindView(R.id.ivPlayPause) ImageView ivPlayPause;

    public String startpause = "pause";

    private int seconds=0;
    private boolean startRun = false;

    private static final long totalTime = 4 * 1000;
    private static final long interval = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_killer);

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            startRun=savedInstanceState.getBoolean("startRun");
        }

        declarate();
        Timer();
    }

    private void declarate() {
        ButterKnife.bind(this);

        CountDownTimer countDownTimer = new MyCountDownTimer(totalTime, interval);
        if(tvCountDown!=null){
            tvCountDown.setText(String.valueOf(totalTime / 1000));
        }
        countDownTimer.start();
        pauseView.setOnClickListener(this);
        stopView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(pauseView)){
            if(!startpause.equals("pause")){
                ivPlayPause.setImageResource(R.drawable.ic_pause);
                stopView.setVisibility(View.GONE);
                startpause = "pause";
                startRun=true;
            } else {
                startRun=false;
                ivPlayPause.setImageResource(R.drawable.ic_play);
                stopView.setVisibility(View.VISIBLE);
                startpause = "start";

            }

        } else if (view.equals(stopView)){
            startRun=false;
        }
    }

    public void onSaveInstanceState(Bundle saveInstanceState){
        saveInstanceState.putInt("seconds", seconds);
        saveInstanceState.putBoolean("startRun", startRun);
    }

    private void Timer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                tvCountdownView.setText(time);

                if(startRun){
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });

    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            if(tvCountDown!=null){
                tvCountDown.setVisibility(View.GONE);
                stopView.setVisibility(View.GONE);
                startRun=true;
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(tvCountDown!=null){
                tvCountDown.setText("" + millisUntilFinished / 1000);
            }
        }
    }
}
