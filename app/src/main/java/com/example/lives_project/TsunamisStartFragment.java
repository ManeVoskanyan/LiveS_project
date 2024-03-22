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

public class TsunamisStartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tsunamis_start, container, false);
        LottieAnimationView lottie_tsunami = view.findViewById(R.id.lottie_tsunami);
        TextView tsunamis_start_textview = view.findViewById(R.id.tsunamis_start_textview);
        lottie_tsunami.animate().alpha(0).translationY(-1000).setDuration(2000).setStartDelay(1000);
        tsunamis_start_textview.animate().alpha(0).translationX(2000).setDuration(1500).setStartDelay(1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.lessons_fragment2_container, new Tsunamis1Fragment());
                transaction.commit();
            }
        }, 3000);
        return view;
    }
}