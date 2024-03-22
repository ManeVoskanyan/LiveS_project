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

public class NaturalDisastersMenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view =  inflater.inflate(R.layout.fragment_natural_disasters_menu, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView earthquakes_image = view.findViewById(R.id.earthquakes_image_bg);
        ImageView fire_image = view.findViewById(R.id.fire_image_bg);
        ImageView tsunamis_image = view.findViewById(R.id.tsunamis_image_bg);

        tsunamis_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTsunamisImageClick();
            }
        });


        fire_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFiresImageClick();
            }
        });
        earthquakes_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEarthquakesImageClick();
            }
        });
      return view;
    }

    public  void onEarthquakesImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new EarthquakesStartFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public  void onFiresImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new FiresStartFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public  void onTsunamisImageClick(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, new TsunamisStartFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}