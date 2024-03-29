package com.example.lives_project;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BottomMenuFragment extends Fragment {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    TextView help_text;
    Button send;
    private List<String> phoneNumbers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);
        ImageView home_image = view.findViewById(R.id.home_image);
        ImageView lessonsImage = view.findViewById(R.id.lessons_image);
        ImageView compass_image = view.findViewById(R.id.compass_image);
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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

        // Загрузка номеров из SharedPreferences
        loadNumbersFromSharedPreferences();
        return view;
    }

    private void loadNumbersFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String phoneNumber1 = sharedPreferences.getString("phoneNumber1", "");
        String phoneNumber2 = sharedPreferences.getString("phoneNumber2", "");
        String phoneNumber3 = sharedPreferences.getString("phoneNumber3", "");
        phoneNumbers.clear();
        if (!phoneNumber1.isEmpty()) phoneNumbers.add(phoneNumber1);
        if (!phoneNumber2.isEmpty()) phoneNumbers.add(phoneNumber2);
        if (!phoneNumber3.isEmpty()) phoneNumbers.add(phoneNumber3);
    }

    public void onSend(View view) {
        String sendMessage = help_text.getText().toString();
        if (phoneNumbers.isEmpty() || sendMessage.isEmpty()) {
            Toast.makeText(getContext(), "Please enter phone numbers", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkPermissions(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            for (int i = 0; i < phoneNumbers.size(); i++) {
                if (i < 3) {
                    smsManager.sendTextMessage(phoneNumbers.get(i), null, sendMessage, null, null);
                } else {
                    break; // Остановить цикл, если уже отправлено три сообщения
                }
            }
            Toast.makeText(getContext(), "Message Sent!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }



    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Переместите вызов loadNumbersFromSharedPreferences() сюда
            loadNumbersFromSharedPreferences();
        }
    }

    public boolean checkPermissions(String permissions) {
        int check = ContextCompat.checkSelfPermission(requireContext(), permissions);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}