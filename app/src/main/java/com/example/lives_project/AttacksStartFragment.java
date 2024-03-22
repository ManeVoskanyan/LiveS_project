package com.example.lives_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class AttacksStartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attacks_start, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView attacks_start_textview = view.findViewById(R.id.attacks_start_textview);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LottieAnimationView lottie_knife = view.findViewById(R.id.lottie_knife);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LottieAnimationView lottie_ambulance = view.findViewById(R.id.lottie_ambulance);
        lottie_knife.animate().alpha(0).translationY(-1000).setDuration(2000).setStartDelay(1000);
        attacks_start_textview.animate().alpha(0).translationX(2000).setDuration(1400).setStartDelay(1000);
        lottie_ambulance.animate().alpha(0).translationX(2000).setDuration(1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.lessons_fragment2_container, new Attacks1Fragment());
                transaction.commit();
            }
        }, 3000);
        return view;
    }
}