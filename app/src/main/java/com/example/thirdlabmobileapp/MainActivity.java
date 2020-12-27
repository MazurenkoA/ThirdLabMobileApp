package com.example.thirdlabmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private int x;
    private int y;

    private int width;
    private int height;
    private int circleRadius;

    private AnimationView animationView = null;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height -= resources.getDimensionPixelSize(resourceId);
        }

        circleRadius = 70;
        x = width / 2;
        y = height / 2;

        animationView = new AnimationView(this, x, y, circleRadius, height, width);

        setContentView(animationView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int newX = x - (int) event.values[0];
        int newY = y + (int) event.values[1];

        if (newY > height || newY < 0) {
            newY = y;
        }

        if (newX < 0 || newX > width - circleRadius) {
            newX = x;
        }

        if (newX != x || newY != y) {
            x = newX;
            y = newY;
            animationView.updateDraw(x, y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}