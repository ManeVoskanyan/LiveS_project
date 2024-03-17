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

public class DogsLessonsStartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   View view = inflater.inflate(R.layout.fragment_dogs_lessons_start, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button page_turner1 = view.findViewById(R.id.page_turner1);
        page_turner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageTurner1BtnClick();
            }
        });
        return view;
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