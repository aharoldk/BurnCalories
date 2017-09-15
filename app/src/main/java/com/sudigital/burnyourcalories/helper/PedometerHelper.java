package com.sudigital.burnyourcalories.helper;

import android.content.Context;
import android.os.Looper;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.motion.Smotion;
import com.samsung.android.sdk.motion.SmotionPedometer;

public class PedometerHelper {

    private SmotionPedometer mPedometer;
    private Context context;
    private boolean started = false;

    PedometerCallback pedometerCallback;


    public PedometerHelper(Context context) {
        this.context = context;

    }

    public void initialize() throws IllegalArgumentException,SsdkUnsupportedException {
        Smotion mMotion = new Smotion();

        mMotion.initialize(context);

        mPedometer = new SmotionPedometer(Looper.getMainLooper(), mMotion);
    }

    public void setPedometerCallback(PedometerCallback pedometerCallback) {
        this.pedometerCallback = pedometerCallback;
    }

    public void start(){
        if (!started) {
            started = true;
            mPedometer.start(new SmotionPedometer.ChangeListener() {
                @Override
                public void onChanged(SmotionPedometer.Info info) {
                    pedometerCallback.updateInfo(info);
                }
            });

            mPedometer.updateInfo();
        }
    }

    public void stop(){
        if (started) {
            started = false;
            mPedometer.stop();

        }
    }

}