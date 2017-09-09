package com.aharoldk.burncalories.helper;

import com.samsung.android.sdk.motion.SmotionPedometer;

/**
 * Created by aharoldk on 09/09/17.
 */

public interface PedometerCallback {
    void motionStarted();
    void motionStopped();
    void updateInfo(SmotionPedometer.Info info);
}
