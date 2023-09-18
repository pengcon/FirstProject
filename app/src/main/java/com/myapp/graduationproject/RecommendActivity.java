package com.myapp.graduationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class RecommendActivity extends AppCompatActivity {


    private SharedViewModel sharedViewModel;

    Fragment tripRecommendFragment;
    Fragment tripRecommendFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        tripRecommendFragment = new TripRecommendFragment();
        tripRecommendFragment2 = new TripRecommendFragment2();


        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, (Fragment) tripRecommendFragment).commitAllowingStateLoss();


    }
}

