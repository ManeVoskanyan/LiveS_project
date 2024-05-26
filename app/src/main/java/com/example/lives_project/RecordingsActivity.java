package com.example.lives_project;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordingsActivity extends AppCompatActivity {

    private ListView recordingsListView;
    private List<String> recordingNames;
    private List<String> recordingPaths;
    private ArrayAdapter<String> adapter;
    private MediaPlayer mediaPlayer;
    private StorageReference storageReference;

    TextView recording_text;
    LottieAnimationView recording_lottie;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings);

        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.lessons_fragment1_container, bottomMenuFragment);
        transaction.commit();

        recordingsListView = findViewById(R.id.recordingsListView);
        recording_lottie = findViewById(R.id.lottie_recording);
        recording_text = findViewById(R.id.recording_text);
        recording_lottie.setVisibility(View.INVISIBLE);
        recording_text.setVisibility(View.INVISIBLE);

        recordingNames = new ArrayList<>();
        recordingPaths = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recordingNames);
        recordingsListView.setAdapter(adapter);

        storageReference = FirebaseStorage.getInstance().getReference().child("recordings");

        retrieveRecordingsFromFirebase();

        recordingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String fileName = recordingNames.get(position);
                String filePath = recordingPaths.get(position);
                playRecording(fileName, filePath);
            }
        });
    }

    private void retrieveRecordingsFromFirebase() {
        storageReference.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                String fileName = item.getName();
                String filePath = item.getPath();
                Log.d("RecordingPath", "Name: " + fileName + ", Path: " + filePath);
                recordingNames.add(fileName);
                recordingPaths.add(filePath);
            }
            adapter.notifyDataSetChanged();
            checkIfListViewIsEmpty();
        }).addOnFailureListener(e -> {
            Log.e("Firebase", "Failed to retrieve recordings", e);
            Toast.makeText(RecordingsActivity.this, "Failed to retrieve recordings", Toast.LENGTH_SHORT).show();
        });
    }


    private void playRecording(String fileName, String filePath) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        try {
            StorageReference audioRef = storageReference.child(fileName);
            audioRef.getDownloadUrl().addOnSuccessListener(uri -> {
                try {
                    mediaPlayer.setDataSource(getApplicationContext(), uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(this, "Playing recording: " + fileName, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to play recording", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                e.printStackTrace();
                Toast.makeText(this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to play recording", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfListViewIsEmpty() {
        if (adapter.isEmpty()) {
            recording_lottie.setVisibility(View.VISIBLE);
            recording_text.setVisibility(View.VISIBLE);
        } else {
            recording_lottie.setVisibility(View.INVISIBLE);
            recording_text.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {

    }
}
