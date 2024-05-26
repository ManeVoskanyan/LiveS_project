package com.example.lives_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SettingsActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private long lastVolumeUpPressTime = 0;
    private long lastVolumeDownPressTime = 0;
    private static final long DOUBLE_PRESS_INTERVAL = 300;
    private Switch volumeButtonSwitch;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "com.example.lives_project.PREFS";
    private static final String PREF_SWITCH_STATE = "switch_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.lessons_fragment1_container, bottomMenuFragment);
        transaction.commit();

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean switchState = sharedPreferences.getBoolean(PREF_SWITCH_STATE, false);

        volumeButtonSwitch = findViewById(R.id.switch_button);
        volumeButtonSwitch.setChecked(switchState);

        volumeButtonSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PREF_SWITCH_STATE, isChecked);
            editor.apply();
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            long currentTime = System.currentTimeMillis();
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && currentTime - lastVolumeUpPressTime <= DOUBLE_PRESS_INTERVAL) {
                performXButtonAction();
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && currentTime - lastVolumeDownPressTime <= DOUBLE_PRESS_INTERVAL) {
                performXButtonAction();
            }
            lastVolumeUpPressTime = currentTime;
            lastVolumeDownPressTime = currentTime;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void performXButtonAction() {
        if (volumeButtonSwitch.isChecked()) {
            Toast.makeText(this, "Вы нажали X-button!", Toast.LENGTH_SHORT).show();
        }
    }
}
