package com.aharoldk.burncalories;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aharoldk.burncalories.helper.DatabaseHelper;
import com.aharoldk.burncalories.helper.PedometerCallback;
import com.aharoldk.burncalories.helper.PedometerHelper;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.motion.SmotionPedometer;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KillerActivity extends AppCompatActivity implements View.OnClickListener, PedometerCallback {
    @BindView(R.id.tvCountdown) TextView tvCountDown;
    @BindView(R.id.tvCountdownView) TextView tvCountdownView;
    @BindView(R.id.tvTotalCount) TextView tvTotalCount;
    @BindView(R.id.tvTotalDistance) TextView tvTotalDistance;
    @BindView(R.id.tvTotalCalories) TextView tvTotalCalories;
    @BindView(R.id.pauseView) LinearLayout pauseView;
    @BindView(R.id.stopView) LinearLayout stopView;
    @BindView(R.id.ivPlayPause) ImageView ivPlayPause;


    PedometerHelper pedometerHelper;

    public String startpause = "pause";

    private int seconds=0;
    private boolean startRun = false;

    private int resultCount = -5;
    private double resultCalories = 0;
    private double oldresultCalories = 0;
    private double resultDistance = 0;
    private double oldresultDistance = 0;
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

        checkSDK();
        declarate();
        Timer();
    }

    private void checkSDK() {
        pedometerHelper = new PedometerHelper(this);

        try {
            pedometerHelper.initialize();
            pedometerHelper.setPedometerCallback(this);

        }catch (IllegalArgumentException e){
            showErrorDialog("Something went wrong", e.getMessage());
            return;
        }catch (SsdkUnsupportedException e){
            showErrorDialog("SDK Not Supported", e.getMessage());
            return;
        }
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
                startRun = true;

                pedometerHelper.start();

            } else {
                startRun=false;
                ivPlayPause.setImageResource(R.drawable.ic_play);
                stopView.setVisibility(View.VISIBLE);
                startpause = "start";
                pedometerHelper.stop();

            }

        } else if (view.equals(stopView)){
            startRun=false;

            pedometerHelper.stop();

            DatabaseHelper databaseHelper = new DatabaseHelper(this);

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy");

            Calendar calendar = Calendar.getInstance();
            String dateNow = dateTimeFormat.format(calendar.getTime());

            if(!databaseHelper.insertActivity(resultDistance, resultCalories, resultCount, dateNow)){
                Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show();

            } else {
                finish();
            }
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

    void showErrorDialog(String title,String message){

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void updateInfo(SmotionPedometer.Info info) {
        double calorie = info.getCalorie();
        double distance = info.getDistance() / interval;

        if(info.getCount(SmotionPedometer.Info.COUNT_TOTAL) != 0){

            if(resultCount < 0){
                oldresultCalories = calorie;
                oldresultDistance = distance;
                resultCount = 0;
            }

            resultDistance = resultDistance + (distance - oldresultDistance);
            resultCalories = resultCalories + (calorie - oldresultCalories);
        }

        tvTotalCalories.setText(String.format("%.2f", resultCalories));
        tvTotalDistance.setText(String.format("%.3f", resultDistance));
        tvTotalCount.setText(String.valueOf(resultCount));

        Log.d("Burn", "onChanged: "+ " calorie "+String.format("%.2f", resultCalories)+
                " distance"+String.format("%.3f", resultDistance)+
                " count"+resultCount);

        try {
            resultCount++;
            oldresultCalories = calorie;
            oldresultDistance = distance;

        } catch (Exception e ){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        pedometerHelper.stop();
    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            if(tvCountDown != null){
                tvCountDown.setVisibility(View.GONE);
                stopView.setVisibility(View.GONE);

                startRun = true;

                pedometerHelper.start();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(tvCountDown!=null){
                tvCountDown.setText("" + millisUntilFinished / 1000);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(startpause.equals("pause")){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Are you want back? ");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    KillerActivity.super.onBackPressed();
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.show();
        } else {
            super.onBackPressed();
            finish();
        }

    }
}
