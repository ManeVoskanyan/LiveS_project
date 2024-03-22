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

public class FiresStartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fires_start, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LottieAnimationView lottie_radio = view.findViewById(R.id.lottie_radio);
        LottieAnimationView lottie_fireman = view.findViewById(R.id.lottie_fireman);
        TextView fires_start_textview  = view.findViewById(R.id.fires_start_textview);
        lottie_radio.animate().alpha(0).translationY(-1000).setDuration(2000).setStartDelay(1000);
        lottie_fireman.animate().alpha(0).translationY(-1000).setDuration(2000).setStartDelay(1000);
        fires_start_textview.animate().alpha(0).translationX(2000).setDuration(1400).setStartDelay(1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.lessons_fragment2_container, new Fires1Fragment());
                transaction.commit();
            }
        }, 3000);
        return view;
    }
}