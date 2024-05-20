package com.example.lives_project;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ButtonActionService extends IntentService {

    private MediaRecorder mediaRecorder;
    private String filepath;
    private double latitude;
    private double longitude;

    public ButtonActionService() {
        super("ButtonActionService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            latitude = intent.getDoubleExtra("latitude", 0);
            longitude = intent.getDoubleExtra("longitude", 0);
        }

        List<String> phoneNumbers = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String phoneNumber1 = sharedPreferences.getString("phoneNumber1", "");
        String phoneNumber2 = sharedPreferences.getString("phoneNumber2", "");
        String phoneNumber3 = sharedPreferences.getString("phoneNumber3", "");

        if (!phoneNumber1.isEmpty()) phoneNumbers.add(phoneNumber1);
        if (!phoneNumber2.isEmpty()) phoneNumbers.add(phoneNumber2);
        if (!phoneNumber3.isEmpty()) phoneNumbers.add(phoneNumber3);

        startRecording();

        String sendMessage = "Help message"; // Замените на ваше сообщение
        if (phoneNumbers.isEmpty() || sendMessage.isEmpty()) {
            showToast("Please enter phone numbers");
            return;
        }
        if (checkPermissions(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            for (int i = 0; i < phoneNumbers.size(); i++) {
                if (i < 3) {
                    smsManager.sendTextMessage(phoneNumbers.get(i), null, sendMessage + "\nMy location: " + latitude + ", " + longitude + "\nClick here to open in maps: https://maps.google.com/maps?q=" + latitude + "," + longitude, null, null);
                } else {
                    break;
                }
            }
            showToast("Message Sent!");
        } else {
            showToast("Permission denied");
        }
    }

    private void startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            filepath = getRecordingFilePath();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(filepath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            showToast("Recording is started");
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Recording failed");
        }
    }

    private String getRecordingFilePath() {
        Context context = getApplicationContext();
        File musicDirectory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (musicDirectory == null) {
            musicDirectory = new File(context.getExternalFilesDir(null), "Music");
            musicDirectory.mkdirs();
        }

        String fileName = "Recording_" + System.currentTimeMillis() + ".mp3";
        File file = new File(musicDirectory, fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getPath();
    }

    private boolean checkPermissions(String permissions) {
        int check = ContextCompat.checkSelfPermission(getApplicationContext(), permissions);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
