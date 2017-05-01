package com.example.tim_w.voiceadventure;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by tim_w on 3/16/2017.
 */

public class ShakeListener implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 3.7f;
    private static final int MIN_TIME_BETWEEN_SHAKE_MILLIS = 750;
    private static final int SHAKE_COUNT = 3;
    private static final int SHAKE_TIMEOUT = 500;
    private static final float TIME_THRESHOLD = 75;
    private long lastUpdate = 0;
    private long lastShake = 0;
    private SensorManager sensorManager;
    private Context mContext;
    private OnShakeListener shakeListener;
    private Sensor acclSensor;
    private int mShakeCount = 0;


    public interface OnShakeListener{
        public void onShake();
    }

    public ShakeListener(Context context){
        mContext = context;
        resume();
    }

    public void setOnShakeListener(OnShakeListener listener){
        shakeListener = listener;
    }

    public void resume() {
        sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager == null) {
            throw new UnsupportedOperationException("Sensors not supported");
        }

        acclSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(acclSensor == null){
            throw new UnsupportedOperationException("Accelerometer not supported");
        }else{
            sensorManager.registerListener(this, acclSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    public void pause() {
        if(sensorManager != null){
            sensorManager.unregisterListener(this, acclSensor);
            sensorManager = null;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            long currTime = System.currentTimeMillis();
            if((currTime - lastUpdate) > TIME_THRESHOLD){
                lastUpdate = currTime;
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                double acceleration = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)) - SensorManager.GRAVITY_EARTH;

                if(acceleration > SHAKE_THRESHOLD){
                    mShakeCount++;
                    if(mShakeCount >= SHAKE_COUNT && (currTime - lastShake) > MIN_TIME_BETWEEN_SHAKE_MILLIS){
                        mShakeCount = 0;
                        lastShake = currTime;
                        if(shakeListener != null){
                            shakeListener.onShake();
                        }
                        Log.d("TAG", "DEVICE HAS SHOOK!");
                    }

                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
