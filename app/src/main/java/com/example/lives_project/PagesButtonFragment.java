package com.example.lives_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PagesButtonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_pages_button, container, false);
       Button button_pages = view.findViewById(R.id.button_pages);
       button_pages.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onPagesBtnClick();
           }

      });


        return view;
 }
    public void onPagesBtnClick() {
        DogsLessons2Fragment dogsLessons2Fragment = new DogsLessons2Fragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, dogsLessons2Fragment);
       fragmentTransaction.addToBackStack(null);
       fragmentTransaction.commit();
   }
}
