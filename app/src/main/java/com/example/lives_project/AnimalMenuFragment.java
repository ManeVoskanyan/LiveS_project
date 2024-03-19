package com.example.lives_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AnimalMenuFragment extends Fragment {
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    ImageView correct_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal_menu, container, false);
         ImageView dogs_image  = view.findViewById(R.id.dog_image_bg);
         correct_image = view.findViewById(R.id.correct_image);
        dogs_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDogImageClick();
            }
        });
        ImageView snake_image  = view.findViewById(R.id.snake_image_bg);
        snake_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onSnakeImageClick();
            }
        });
        ImageView bear_image  = view.findViewById(R.id.bear_image_bg);
        bear_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBearImageClick();
            }
        });

        return  view;
    }



    public  void onDogImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new DogsLessonsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void onSnakeImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new SnakeLessonsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    public void onBearImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new BearsLessonsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
//    public void showImage() {
//        correct_image.setVisibility(View.VISIBLE);
//    }

}
