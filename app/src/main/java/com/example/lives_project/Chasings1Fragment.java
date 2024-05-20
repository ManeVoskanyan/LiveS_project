package com.example.lives_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Chasings1Fragment extends Fragment {

    private DatabaseReference mDatabase;

    private LottieAnimationView lottie_loading;
    ImageView image1, image2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_chasings1, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button page_turner1 = view.findViewById(R.id.page_turner1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lottie_loading = view.findViewById(R.id.lottie_loading);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        lottie_loading.setVisibility(View.VISIBLE);
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);


        page_turner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageTurner1BtnClick();
            }
        });
        for (int i = 1; i <= 3; i++) {
            getLessonData(i, view);
        }
        return view;
    }

    private void getLessonData(int lessonNumber, View view) {
        mDatabase.child("StalkingLessons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot lessonSnapshot = dataSnapshot.child("StalkingLessons" + lessonNumber);
                    if (lessonSnapshot.exists()) {
                        String lessonText = lessonSnapshot.getValue(String.class);
                        TextView lessonTextView = null;

                        switch (lessonNumber) {
                            case 1:
                                lessonTextView = view.findViewById(R.id.stalkings_start_lesson1);
                                break;
                            case 2:
                                lessonTextView = view.findViewById(R.id.stalkings_start_lesson2);
                                break;
                            case 3:
                                lessonTextView = view.findViewById(R.id.stalkings_start_lesson3);
                                break;

                        }

                        if (lessonTextView != null) {
                            lessonTextView.setText(lessonText);
                        }
                    } else {

                    }
                } else {

                }
                checkDataLoaded(view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkDataLoaded(View view) {
        TextView lesson1 = view.findViewById(R.id.stalkings_start_lesson1);
        TextView lesson2 = view.findViewById(R.id.stalkings_start_lesson2);
        TextView lesson3 = view.findViewById(R.id.stalkings_start_lesson3);

        if (!lesson1.getText().toString().isEmpty() &&
                !lesson2.getText().toString().isEmpty() &&
                !lesson3.getText().toString().isEmpty()) {
            lottie_loading.setVisibility(View.INVISIBLE);
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.VISIBLE);
        }
    }

    public void onPageTurner1BtnClick() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new Chasings2Fragment() );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
