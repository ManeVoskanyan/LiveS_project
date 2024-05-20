package com.example.lives_project;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lives_project.MainActivity;
import com.example.lives_project.R;

import java.util.Collections;

public class SettingsActivity extends AppCompatActivity {

    private Switch buttonVisibilitySwitch;
    private static final String PREFS_NAME = "settings_prefs";
    private static final String BUTTON_VISIBLE_KEY = "button_visible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.lessons_fragment1_container, bottomMenuFragment);
        transaction.commit();


        buttonVisibilitySwitch = findViewById(R.id.button_visibility_switch);

        final SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isButtonVisible = preferences.getBoolean(BUTTON_VISIBLE_KEY, false);
        buttonVisibilitySwitch.setChecked(isButtonVisible);

        buttonVisibilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(BUTTON_VISIBLE_KEY, isChecked);
                editor.apply();

                if (isChecked) {
                    createShortcut();
                } else {
                    removeShortcut();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void createShortcut() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
            Intent shortcutIntent = new Intent(this, ButtonActionReceiver.class);
            shortcutIntent.setAction("com.example.lives_project.BUTTON_ACTION");

            ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id_button_action")
                    .setShortLabel("Button Action")
                    .setLongLabel("Perform Button Action")
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_icon))
                    .setIntent(shortcutIntent)
                    .build();

            shortcutManager.requestPinShortcut(shortcut, null);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void removeShortcut() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        if (shortcutManager != null) {
            shortcutManager.removeDynamicShortcuts(Collections.singletonList("id_button_action"));
        }
    }
}
