package com.myapp.graduationproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    // xml 레이아웃을 inflate(메모리에 올리면서 코드와 연결) 작업을 하는 역할
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 아래 코드를 통하여 xml과 연결됨
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}