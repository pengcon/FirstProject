package com.myapp.graduationproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;


import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class LoginActivity extends AppCompatActivity{
    private ImageView EmailLoginBtn,mgoogleLoginBtn,KakaoLoginBtn,profileImage;
    private TextView nickName;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패

                if (throwable != null) {
                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
                else{
                    // 로그인 성공 처리
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    // 메인 액티비티로 이동
                    LoginUtils.saveLoginMethod(LoginActivity.this, "kakao");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

                updateKakaoLoginUi();
                return null;
            }
        };

        KakaoLoginBtn = findViewById(R.id.btn_kakaologin);//카카오
        KakaoLoginBtn.setOnClickListener(view -> {
            // 카카오 로그인 실행
            UserApiClient.getInstance().loginWithKakaoAccount(this, callback);

        });

        EmailLoginBtn=findViewById(R.id.btn_email_login);

        EmailLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, EmailLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });




            }
    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인이 되어있으면
                if (user != null) {



//                    Glide.with(profileImage).load(user.getKakaoAccount().
//                            getProfile().getProfileImageUrl()).circleCrop().into(profileImage);


                } else {
                    // 로그인이 되어 있지 않다면 위와 반대로
//                    nickName.setText(null);
//                    profileImage.setImageBitmap(null);


                }
                return null;
            }
        });



    }


}
