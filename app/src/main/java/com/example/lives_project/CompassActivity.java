package com.example.lives_project;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    TextView degreetxt;
    ImageView compassimg;
    SensorManager sensorManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_activity);
        degreetxt = findViewById(R.id.textview_degree);
        compassimg = findViewById(R.id.image_compass);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.lessons_fragment1_container, bottomMenuFragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List <Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for ( Sensor s: sensorList){
            Log.d("Sensor", s.toString());
        }
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int degree = Math.round(sensorEvent.values[0]);
        degreetxt.setText("Degree: " + degree);
        compassimg.setRotation(-degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {

    }
}
