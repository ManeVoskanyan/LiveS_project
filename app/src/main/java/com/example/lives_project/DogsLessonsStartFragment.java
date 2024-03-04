package com.example.lives_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DogsLessonsStartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   View view = inflater.inflate(R.layout.fragment_dogs_lessons_start, container, false);
//        TextView start1 = view.findViewById(R.id.dogs_start_lesson1);
//        TextView start2 = view.findViewById(R.id.dogs_start_lesson2);
//        ImageView imageView = view.findViewById(R.id.imageView);
//        start1.animate().alpha(0);
//        start2.animate().alpha(0);
//        imageView.animate().alpha(0);
//        start1.animate().alpha(1);
//        start2.animate().alpha(1);
//        imageView.animate().alpha(1);
        return view;
    }
}