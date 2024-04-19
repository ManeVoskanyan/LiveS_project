package com.example.lives_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.lives_project.MainActivity;
import com.example.lives_project.R;

public class FirstActivity extends AppCompatActivity {
    TextView first_lives;
    LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        first_lives = findViewById(R.id.first_lives);
        lottie = findViewById(R.id.lottie);
        first_lives.animate().alpha(0).translationY(-1000).setDuration(1400).setStartDelay(2500);
        lottie.animate().alpha(0).translationX(2000).setDuration(2000).setStartDelay(2500);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(FirstActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                },3500);
            }
        });


    }
}