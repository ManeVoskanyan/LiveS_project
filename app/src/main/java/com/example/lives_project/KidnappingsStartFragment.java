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


public class KidnappingsStartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_kidnappings_start, container, false);
       TextView kidnappings_start_textview = view.findViewById(R.id.kidnappings_start_textview);
        LottieAnimationView lottie_man = view.findViewById(R.id.lottie_man);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LottieAnimationView lottie_police = view.findViewById(R.id.lottie_police);
        lottie_police.animate().alpha(0).translationX(2000).setDuration(1500);
        lottie_man.animate().alpha(0).translationY(-1000).setDuration(2000).setStartDelay(1000);
        kidnappings_start_textview.animate().alpha(0).translationX(2000).setDuration(1400).setStartDelay(1000);
        new Handler().postDelayed(new Runnable() {
       @Override
            public void run() {
               FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.lessons_fragment2_container, new Kidnappings1Fragment());
                transaction.commit();
         }
      }, 3000);
        return view;
    }
}