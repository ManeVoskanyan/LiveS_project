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

public class DogsLessons4Fragment extends Fragment {

    private DatabaseReference mDatabase;
    ImageView image1, image2, image3;
    private LottieAnimationView lottie_loading;
//    private OnEndBtnClickListener onEndBtnClickListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dogs_lessons4, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button page_turner2 = view.findViewById(R.id.page_turner2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button end_button = view.findViewById(R.id.end_button);
//        end_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onEndBtnClickListener != null) {
//                    onEndBtnClickListener.onEndBtnClick();
//                }
//            }
//        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lottie_loading = view.findViewById(R.id.lottie_loading);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        lottie_loading.setVisibility(View.VISIBLE);
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);
        page_turner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageTurner2BtnClick();
            }
        });
        for (int i = 12; i <= 15; i++) {
            getLessonData(i, view);
        }
        return view;
    }
    public void onPageTurner2BtnClick() {
        DogsLessons3Fragment dogsLessons3Fragment = new DogsLessons3Fragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, dogsLessons3Fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
//    public interface OnEndBtnClickListener {
//        void onEndBtnClick();
//    }
//    public void setOnEndBtnClickListener ( OnEndBtnClickListener onEndBtnClickListener ){
//        this.onEndBtnClickListener = onEndBtnClickListener;
//    }



    private void getLessonData(int lessonNumber, View view) {
        String lessonKey = "DogsLessons" + lessonNumber;
        mDatabase.child(lessonKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String lessonText = dataSnapshot.getValue(String.class);
                    TextView lessonTextView = null;

                    switch (lessonNumber) {
                        case 12:
                            lessonTextView = view.findViewById(R.id.dogs_start_lesson1);
                            break;
                        case 13:
                            lessonTextView = view.findViewById(R.id.dogs_start_lesson2);
                            break;
                        case 14:
                            lessonTextView = view.findViewById(R.id.dogs_start_lesson3);
                            break;
                        case 15:
                            lessonTextView = view.findViewById(R.id.dogs_start_lesson4);
                            break;
                    }

                    if (lessonTextView != null) {
                        lessonTextView.setText(lessonText);
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
        TextView lesson1 = view.findViewById(R.id.dogs_start_lesson1);
        TextView lesson2 = view.findViewById(R.id.dogs_start_lesson2);
        TextView lesson3 = view.findViewById(R.id.dogs_start_lesson3);

        if (!lesson1.getText().toString().isEmpty() &&
                !lesson2.getText().toString().isEmpty() &&
                !lesson3.getText().toString().isEmpty()) {
            lottie_loading.setVisibility(View.INVISIBLE);
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.VISIBLE);
            image3.setVisibility(View.VISIBLE);
        }
    }

}