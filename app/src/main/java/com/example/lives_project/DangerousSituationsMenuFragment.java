package com.example.lives_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DangerousSituationsMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view  =  inflater.inflate(R.layout.fragment_dangerous_situations_menu, container, false);
        ImageView kidnapping_image = view.findViewById(R.id.kidnappings_image_bg);
        ImageView chasings_image = view.findViewById(R.id.chasing_image_bg);
        ImageView attacks_image = view.findViewById(R.id.attacks_image_bg);


        attacks_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttacksImageClick();
            }
        });

        chasings_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChasingsImageClick();
            }
        });
        kidnapping_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onKidnappingImageClick();
            }
        });

      return  view;
    }
    public  void onKidnappingImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new KidnappingsStartFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public  void onChasingsImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new ChasingsStartFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public  void onAttacksImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new AttacksStartFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}