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

public class DogsLessons4Fragment extends Fragment {
//    private OnEndBtnClickListener onEndBtnClickListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dogs_lessons4, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button page_turner2 = view.findViewById(R.id.page_turner2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button end_button = view.findViewById(R.id.end_button);
//        end_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onEndBtnClickListener != null) {
//                    onEndBtnClickListener.onEndBtnClick();
//                }
//            }
//        });
        page_turner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageTurner2BtnClick();
            }
        });
        return view;
    }
    public void onPageTurner2BtnClick() {
        DogsLessons3Fragment dogsLessons3Fragment = new DogsLessons3Fragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lessons_fragment2_container, dogsLessons3Fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
//    public interface OnEndBtnClickListener {
//        void onEndBtnClick();
//    }
//    public void setOnEndBtnClickListener ( OnEndBtnClickListener onEndBtnClickListener ){
//        this.onEndBtnClickListener = onEndBtnClickListener;
//    }

}