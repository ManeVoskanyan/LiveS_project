package com.example.lives_project;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class LessonsMenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

   View view = inflater.inflate(R.layout.fragment_lessons_menu, container, false);
        ImageView woman_dog_image = view.findViewById(R.id.girl_dog_bg);
        ImageView scared_girl_image = view.findViewById(R.id.scared_girl_bg);
        ImageView natural_disaster_image = view.findViewById(R.id.girl_danger_bg);

        natural_disaster_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNaturalImageClick();
            }
        });
        scared_girl_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDangerImageClick();
            }
        });
        woman_dog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnimalImageClick();
            }
        });

  return  view;
    }

    public  void onAnimalImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new AnimalMenuFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public  void onDangerImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new DangerousSituationsMenuFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public  void onNaturalImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new NaturalDisastersMenuFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}