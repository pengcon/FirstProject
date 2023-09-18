package com.myapp.graduationproject;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class kakaoLogin extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        //Kakao SDK 초기화
        KakaoSdk.init(this,"f73bfeb0e254736ae16e4c043698f69b");

    }
}
