package com.aharoldk.burnyourcalories.helper;

import com.samsung.android.sdk.motion.SmotionPedometer;

/**
 * Created by aharoldk on 09/09/17.
 */

public interface PedometerCallback {
    void updateInfo(SmotionPedometer.Info info);
}
