package com.myapp.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    Fragment homeFragment;
    Fragment googleMapFragment;
    Fragment mypageFragment;
    Fragment tripFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment= new HomeFragment();
//        googleMapFragment = new GoogleMapFragment();
        mypageFragment = new MypageFragment();
//        tripFragment = new Tripfragment();


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, (Fragment) homeFragment).commitAllowingStateLoss();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, homeFragment).commit();
                        return true;
                    case R.id.trip:
                        Intent intent = new Intent(getApplicationContext(), RecommendActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.map:
                        Intent intent2 = new Intent(getApplicationContext(), GoogleMapActivity2.class);
                        startActivity(intent2);
                        return true;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, mypageFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
        }

