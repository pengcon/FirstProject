package com.example.graduationproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;;

public class SignUp extends AppCompatActivity {
    private EditText edtSignEmail,edtSignPwd,edtSignName;
    private ImageView btnSignUpFinish;
    private FirebaseAuth mFirebaseAuth;
    private CheckBox checkbox_gaein, checkbox_service, checkbox_location,checkbox_age;
    private TextView service_yakgwan, gaein_yakgwan, location_yakgwan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtSignEmail = findViewById(R.id.edtSignEmail);
        edtSignPwd = findViewById(R.id.edtSignPwd);
        btnSignUpFinish = findViewById(R.id.btnSignUpFinish);

        checkbox_gaein = findViewById(R.id.checkbox_gaein);
        checkbox_service = findViewById(R.id.checkbox_service);
        checkbox_location = findViewById(R.id.checkbox_location);

        service_yakgwan = findViewById(R.id.service_yakgwan);
        gaein_yakgwan = findViewById(R.id.gaein_yakgwan);
        location_yakgwan = findViewById(R.id.location_yakgwan);

        service_yakgwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gigantic-salt-db6.notion.site/a5dfb146545249f3aaf102c032faa341"));
                startActivity(intent);
            }
        });

        location_yakgwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gigantic-salt-db6.notion.site/222e74347d9440daa793d11fe40d3247"));
                startActivity(intent);
            }
        });

        gaein_yakgwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gigantic-salt-db6.notion.site/d6674dc80b884d8f97926023e878ff0b"));
                startActivity(intent);
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();

        btnSignUpFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = edtSignEmail.getText().toString();
                String strPwd = edtSignPwd.getText().toString();
                int strLength = strPwd.length();
                if(strLength>=6 && checkbox_gaein.isChecked() && checkbox_service.isChecked() && checkbox_location.isChecked()){
                    createUser(strEmail,strPwd);
                }else{
                    Toast.makeText(SignUp.this, "이용약관 동의 필수 및, 비밀번호는 6자리 이상이여야 합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplication(),LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(SignUp.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}