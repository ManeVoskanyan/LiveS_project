package com.example.lives_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class KidnappingsStartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_kidnappings_start, container, false);
//        dogs_textview = view.findViewById(R.id.dogs_start_textview);
//        dog_lottie = view.findViewById(R.id.lottie_dog);
//        dog_lottie.animate().alpha(0).translationY(-1000).setDuration(2000).setStartDelay(1000);
//        dogs_textview.animate().alpha(0).translationX(2000).setDuration(1400).setStartDelay(1000);
//        new Handler().postDelayed(new Runnable() {
//        @Override
//            public void run() {
//                DogsLessonsStartFragment dogsLessonsStartFragment = new DogsLessonsStartFragment();
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.lessons_fragment2_container, dogsLessonsStartFragment);
//                transaction.commit();
//            }
//        }, 3000);
        return view;
    }
}