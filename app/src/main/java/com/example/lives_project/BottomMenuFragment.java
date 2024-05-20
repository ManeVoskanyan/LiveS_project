package com.example.lives_project;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BottomMenuFragment extends Fragment {
    private static final int MICROPHONE_PERMISSION_CODE = 200;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private StorageReference storageReference;

    private int recordingNumber = 1;

    String filepath;

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    TextView help_text;
    Button send;

    private LocationManager locationManager;
    private double latitude, longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);
        ImageView home_image = view.findViewById(R.id.home_image);
        ImageView lessonsImage = view.findViewById(R.id.lessons_image);
        ImageView compass_image = view.findViewById(R.id.compass_image);
        ImageView settings_image = view.findViewById(R.id.settings_image);
        Button stopButton = view.findViewById(R.id.stop_button);
        stopButton.setVisibility(View.INVISIBLE);
        storageReference = FirebaseStorage.getInstance().getReference().child("recordings");

        if (isMicrophonePresent()) {
            getMicrophonePermission();
        }
        settings_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        compass_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CompassActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        lessonsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LessonsActivity.class);
                startActivity(intent);
            }
        });
        home_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        help_text = view.findViewById(R.id.massage);
        send = view.findViewById(R.id.main_btn1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend(v);
            }
        });
        send.setEnabled(false);
        if (checkPermissions(Manifest.permission.SEND_SMS)) {
            send.setEnabled(true);
        } else {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        if (checkPermissions(Manifest.permission.ACCESS_FINE_LOCATION)) {
            getLocation();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
        return view;
    }

    private void getLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        } else {
            Toast.makeText(getContext(), "Turn on GPS", Toast.LENGTH_SHORT).show();
        }
    }


    public void onSend(View view) {

        List<String> phoneNumbers = new ArrayList<>();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String phoneNumber1 = sharedPreferences.getString("phoneNumber1", "");
        String phoneNumber2 = sharedPreferences.getString("phoneNumber2", "");
        String phoneNumber3 = sharedPreferences.getString("phoneNumber3", "");;
        if (!phoneNumber1.isEmpty()) phoneNumbers.add(phoneNumber1);
        if (!phoneNumber2.isEmpty()) phoneNumbers.add(phoneNumber2);
        if (!phoneNumber3.isEmpty()) phoneNumbers.add(phoneNumber3);

        Button stopButton = getView().findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopPressed(v);
            }
        });
        stopButton.setVisibility(View.VISIBLE);
        try {
            mediaRecorder = new MediaRecorder();
            filepath = getRecordingFilePath();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(filepath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(getContext(), "Recording is started", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sendMessage = help_text.getText().toString();
        if (phoneNumbers.isEmpty() || sendMessage.isEmpty()) {
            Toast.makeText(getContext(), "Please enter phone numbers", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkPermissions(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            for (int i = 0; i < phoneNumbers.size(); i++) {
                if (i < 3) {
                    smsManager.sendTextMessage(phoneNumbers.get(i), null, sendMessage + "\nMy location: " + latitude + ", " + longitude + "\nClick here to open in maps: https://maps.google.com/maps?q=" + latitude + "," + longitude,  null, null);
                } else {
                    break;
                }
            }
            Toast.makeText(getContext(), "Message Sent!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkPermissions(String permissions) {
        int check = ContextCompat.checkSelfPermission(requireContext(), permissions);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
    private boolean isMicrophonePresent() {
        PackageManager packageManager = getContext().getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    private void getMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
    }

    private String getRecordingFilePath() {
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (musicDirectory == null) {
            musicDirectory = new File(contextWrapper.getExternalFilesDir(null), "Music");
            musicDirectory.mkdirs();
        }

        String fileName = "Recording_" + recordingNumber + ".mp3";
        recordingNumber++;

        File file = new File(musicDirectory, fileName);


        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file.getPath();
    }


    private void saveRecordingToFirebase(String filePath) {
        Uri fileUri = Uri.fromFile(new File(filePath));

        String fileName = "Recording_" + System.currentTimeMillis() + ".mp3";

        StorageReference fileRef = storageReference.child(fileName);

        fileRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(getContext(), "Recording saved to Firebase", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error saving recording to Firebase", Toast.LENGTH_SHORT).show();
                });
    }



    public void btnStopPressed(View view) {
        Button stopButton = view.findViewById(R.id.stop_button);
        stopButton.setVisibility(View.INVISIBLE);
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;

            Toast.makeText(getContext(), "Recording is stopped", Toast.LENGTH_LONG).show();

            saveRecordingToFirebase(filepath);
        } else {
            Toast.makeText(getContext(), "No recording to stop", Toast.LENGTH_LONG).show();

        }
    }

}