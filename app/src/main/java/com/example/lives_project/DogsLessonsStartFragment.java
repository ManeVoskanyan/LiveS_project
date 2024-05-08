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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DogsLessonsStartFragment extends Fragment {

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dogs_lessons_start, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button page_turner1 = view.findViewById(R.id.page_turner1);
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
        String lessonKey = "DogsLessons" + lessonNumber;
        mDatabase.child(lessonKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String lessonText = dataSnapshot.getValue(String.class);
                    TextView lessonTextView = null;

                    switch (lessonNumber) {
                        case 1:
                            lessonTextView = view.findViewById(R.id.dogs_start_lesson1);
                            break;
                        case 2:
                            lessonTextView = view.findViewById(R.id.dogs_start_lesson2);
                            break;
                        case 3:
                            lessonTextView = view.findViewById(R.id.dogs_start_lesson3);
                            break;
                    }

                    if (lessonTextView != null) {
                        lessonTextView.setText(lessonText);
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
        DogsLessons2Fragment dogsLessons2Fragment = new DogsLessons2Fragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, dogsLessons2Fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
