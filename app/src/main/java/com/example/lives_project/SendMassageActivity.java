package com.example.lives_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

public class SendMassageActivity extends AppCompatActivity {

    final int  SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    EditText number;
    TextView help_text;
    Button send;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_massage);
        number = findViewById(R.id.inputNumber);
        help_text = findViewById(R.id.massage);
        send = findViewById(R.id.button_send);
        send.setEnabled(false);
        if ( checkPermissions(Manifest.permission.SEND_SMS) ){
            send.setEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    public void onSend ( View view ){
        String phoneNumber = number.getText().toString();
        String sendMessage = help_text.getText().toString();
        if (phoneNumber.isEmpty() || sendMessage.isEmpty()) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( checkPermissions(Manifest.permission.SEND_SMS) ){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, sendMessage, null, null);
            Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean checkPermissions(String permissions ){
        int check = ContextCompat.checkSelfPermission(this, permissions);
        return ( check == PackageManager.PERMISSION_GRANTED);
    }

}