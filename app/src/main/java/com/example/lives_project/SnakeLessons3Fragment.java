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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SnakeLessons3Fragment extends Fragment {

    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view  =  inflater.inflate(R.layout.fragment_snake_lessons3, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button page_turner1 = view.findViewById(R.id.page_turner1);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button page_turner2 = view.findViewById(R.id.page_turner2);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        page_turner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageTurner2BtnClick();
            }
        });
        page_turner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageTurner1BtnClick();
            }
        });

        for (int i = 8; i <= 11; i++) {
            getLessonData(i, view);
        }
        return view;
    }

    private void getLessonData(int lessonNumber, View view) {
        mDatabase.child("SnakeLessons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot lessonSnapshot = dataSnapshot.child("SnakeLessons" + lessonNumber);
                    if (lessonSnapshot.exists()) {
                        String lessonText = lessonSnapshot.getValue(String.class);
                        TextView lessonTextView = null;

                        switch (lessonNumber) {
                            case 8:
                                lessonTextView = view.findViewById(R.id.snakes_start_lesson1);
                                break;
                            case 9:
                                lessonTextView = view.findViewById(R.id.snakes_start_lesson2);
                                break;
                            case 10:
                                lessonTextView = view.findViewById(R.id.snakes_start_lesson3);
                                break;
                            case 11:
                                lessonTextView = view.findViewById(R.id.snakes_start_lesson4);
                                break;
                        }

                        if (lessonTextView != null) {
                            lessonTextView.setText(lessonText);
                        }
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void onPageTurner1BtnClick() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new SnakeLessons4Fragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void onPageTurner2BtnClick() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new SnakeLessons2Fragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
