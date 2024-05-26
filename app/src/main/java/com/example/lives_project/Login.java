package com.example.lives_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    static Boolean GuestMode = false;
    private static final String TAG = "LoginActivity";

    EditText emailEditText;
    TextView error;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.gmail);
        passwordEditText = findViewById(R.id.password);
        error = findViewById(R.id.error);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        boolean isGuestMode = sharedPreferences.getBoolean("isGuestMode", false);

        if (isLoggedIn && !isGuestMode) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {

        }


        Intent intent = getIntent();
        if (intent != null) {
            Login.GuestMode = intent.getBooleanExtra("GuestMode", false);
        }
    }

    public void signIn(View v) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                String uid = user.getUid();
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

                                userRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();

                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else if (user != null && !user.isEmailVerified()) {
                                Toast.makeText(Login.this, "Please verify your email before logging in", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "User not found or authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void Register(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
//
//    public void ForgetPass(View v) {
//        Intent intent = new Intent(this, ForgetPass.class);
//        startActivity(intent);
//    }

    public void Guest(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isGuestMode", true);
        editor.apply();

        // Вызываем метод для входа в режиме гостя
        signInAsGuest();
    }

    private void signInAsGuest() {
        String guestEmail = "sictst4@gmail.com";
        String guestPassword = "Samsung2023";

        mAuth.signInWithEmailAndPassword(guestEmail, guestPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Вход в режиме гостя выполнен успешно
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            // Закрытие текущей активности
                            finish();
                        } else {
                            // Ошибка входа в режиме гостя
                            Toast.makeText(Login.this, "Error signing in as guest", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}