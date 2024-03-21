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

public class ChasingsStartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_chasings_start, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView chasings_start_textview = view.findViewById(R.id.chasings_start_textview);
        LottieAnimationView lottie_man = view.findViewById(R.id.lottie_man);
        lottie_man.animate().alpha(0).translationXBy(2000).setDuration(3500);
        chasings_start_textview.animate().alpha(0).translationXBy(2000).setDuration(4500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              Chasings1Fragment chasings1Fragment = new Chasings1Fragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.lessons_fragment2_container, chasings1Fragment);
                transaction.commit();
            }
        }, 3000);
        return view;
    }
}