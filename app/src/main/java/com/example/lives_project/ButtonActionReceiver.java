package com.example.lives_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ButtonActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ButtonActionService.class);
        serviceIntent.putExtra("latitude", intent.getDoubleExtra("latitude", 0));
        serviceIntent.putExtra("longitude", intent.getDoubleExtra("longitude", 0));
        context.startService(serviceIntent);
    }
}
