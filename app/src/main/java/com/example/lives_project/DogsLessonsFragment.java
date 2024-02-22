package com.example.lives_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class DogsLessonsFragment extends Fragment {

    TextView dogs_textview;
    LottieAnimationView dog_lottie;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dogs_lessons, container, false);
       dogs_textview = view.findViewById(R.id.dogs_start_textview);
        dog_lottie = view.findViewById(R.id.lottie_dog);
        dog_lottie.animate().alpha(0).translationY(-1000).setDuration(2000).setStartDelay(1000);
        dogs_textview.animate().alpha(0).translationX(2000).setDuration(1400).setStartDelay(1000);
        return view;
    }
}