package com.example.lives_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SnakeLessonsFragment extends Fragment {
    TextView snakes_textview;
    LottieAnimationView snake_lottie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snake_lessons, container, false);
        snakes_textview = view.findViewById(R.id.snakes_start_textview);
        snake_lottie = view.findViewById(R.id.lottie_snake);
        snake_lottie.animate().alpha(0).translationY(-1000).setDuration(2000).setStartDelay(1000);
        snakes_textview.animate().alpha(0).translationX(2000).setDuration(1400).setStartDelay(1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.lessons_fragment2_container, new SnakeLessonsStartFragment());
                transaction.commit();
            }
        }, 3000);
        return view;
    }
}