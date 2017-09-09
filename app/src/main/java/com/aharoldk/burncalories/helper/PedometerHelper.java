package com.aharoldk.burncalories.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.motion.Smotion;
import com.samsung.android.sdk.motion.SmotionPedometer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aharoldk on 09/09/17.
 */

public class PedometerHelper {

    final static int MODE_PEDOMETER_REALTIME = 0;
    final static int MODE_PEDOMETER_PERIODIC = 1;

    private int mMode = MODE_PEDOMETER_REALTIME;

    private Smotion mMotion;
    private SmotionPedometer mPedometer;
    private Context context;
    private boolean started = false;
    private boolean isPedometerUpDownAvailable;

    PedometerCallback pedometerCallback;


    public PedometerHelper(Context context) {

        this.context = context;

    }

    public void initialize() throws IllegalArgumentException,SsdkUnsupportedException {
        mMotion = new Smotion();

        mMotion.initialize(context);

        mPedometer = new SmotionPedometer(Looper.getMainLooper(), mMotion);

        isPedometerUpDownAvailable = mMotion.isFeatureEnabled(Smotion.TYPE_PEDOMETER_WITH_UPDOWN_STEP);
    }

    public void setPedometerCallback(PedometerCallback pedometerCallback) {
        this.pedometerCallback = pedometerCallback;
    }

    public void start(){
        if (!started) {
            started = true;
            mPedometer.start(changeListener);
            mPedometer.updateInfo();

            pedometerCallback.motionStarted();
        }
    }

    public void stop(){
        if (started == true) {
            started = false;
            mPedometer.stop();

            pedometerCallback.motionStopped();

        }
    }

    final SmotionPedometer.ChangeListener changeListener = new SmotionPedometer.ChangeListener() {
        @Override
        public void onChanged(SmotionPedometer.Info info) {
            if (mMode == MODE_PEDOMETER_REALTIME) {
                pedometerCallback.updateInfo(info);
            }
        }
    };
}