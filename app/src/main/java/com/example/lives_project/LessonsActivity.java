package com.example.lives_project;
;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LessonsActivity extends AppCompatActivity { // implements DogsLessons4Fragment.OnEndBtnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.lessons_fragment1_container, bottomMenuFragment);
        transaction.commit();

        FragmentManager fragmentManager2 = getSupportFragmentManager();
        LessonsMenuFragment lessonsMenuFragment = new LessonsMenuFragment();
        FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
        transaction2.add(R.id.lessons_fragment2_container, lessonsMenuFragment);
        transaction2.commit();

//        DogsLessons4Fragment dogsLessons4Fragment = new DogsLessons4Fragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.lessons_fragment2_container, dogsLessons4Fragment).commit();
//        dogsLessons4Fragment.setOnEndBtnClickListener(this);

    }
//    public void onEndBtnClick () {
//     AnimalMenuFragment animalMenuFragment = (AnimalMenuFragment) getSupportFragmentManager().findFragmentById(R.id.lessons_fragment2_container);
//      if ( animalMenuFragment != null ){
//          animalMenuFragment.showImage();
//      }
//    }
}
