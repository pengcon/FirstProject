package com.myapp.graduationproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class MypageFragment extends Fragment {
    private LinearLayout btnSecondLogout, btnSecondResign,mylounge, btnServiceYakgwan, btnGaein, btnLocation, btnOpensource;

    // xml 레이아웃을 inflate(메모리에 올리면서 코드와 연결) 작업을 하는 역할
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 아래 코드를 통하여 xml과 연결됨
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        btnSecondLogout = v.findViewById(R.id.btn_second_logout);
        btnSecondResign = v.findViewById(R.id.btn_second_resign);
        btnServiceYakgwan = v.findViewById(R.id.btn_service_yakgwan);
        btnGaein = v.findViewById(R.id.btn_gaein);
        btnLocation = v.findViewById(R.id.btn_location);
        btnOpensource = v.findViewById(R.id.btn_opensource);





        btnSecondLogout.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   String method = LoginUtils.getLoginMethod(getActivity());
                                                   if (method.equals("google")){
                                                       FirebaseAuth.getInstance().signOut();
                                                       GoogleSignIn.getClient(getContext(), GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
                                                       directToLoginActivity(true);
                                                   }
                                                   else if (method.equals("kakao")) {
                                                       UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                                                           @Override
                                                           public Unit invoke(Throwable throwable) {
                                                               if (throwable != null) {
                                                                   // 에러가 발생했을 때 동작을 여기에 구현합니다.
                                                                   Log.e("Logout Error", "Failed to logout from Kakao: ", throwable);
                                                               } else {
                                                                   // 성공적으로 로그아웃되었을 때 동작을 여기에 구현합니다.
                                                                   directToLoginActivity(true);
                                                               }
                                                               return null;
                                                           }
                                                       });


                                                   }
                                               }
                                           });
        btnSecondResign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String method = LoginUtils.getLoginMethod(getActivity());
                if (method.equals("google")) {
                    // 구글 연동 해제
                    try {
                        FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    directToLoginActivity(true);
                                }
                                else {
                                    directToLoginActivity(false);
                                }
                            }
                        }); // Firebase 인증 해제
                        GoogleSignIn.getClient(getContext(), GoogleSignInOptions.DEFAULT_SIGN_IN).revokeAccess(); // Google 계정 해제
                    } catch (Exception e) {
                        directToLoginActivity(false);
                    }
                }
                else if(method.equals("kakao")){
                    //카카오 회원탈퇴
                    UserApiClient.getInstance().unlink(new Function1<Throwable, Unit>() {
                        @Override
                        public Unit invoke(Throwable throwable) {

                            if (throwable != null) {
                                // 에러가 발생했을 때 동작을 여기에 구현합니다.
                                Log.e("Unlink Error", "Failed to unlink from Kakao: ", throwable);
                            } else {
                                // 성공적으로 회원탈퇴되었을 때 동작을 여기에 구현합니다.
                                directToLoginActivity(true);
                            }

                            return null;
                        }
                    });
                }
            }
        });








        btnServiceYakgwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skitter-anteater-08d.notion.site/c2bd627b554e49bd95a3f3b1879936aa?pvs=4"));
                startActivity(intent);
            }
        });

        btnGaein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skitter-anteater-08d.notion.site/6f24bba0fede47308a58d589456e57c9?pvs=4"));
                startActivity(intent);
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skitter-anteater-08d.notion.site/b712f796c65541bbad1d9f776636d0ef?pvs=4"));
                startActivity(intent);
            }
        });

        btnOpensource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OssLicensesMenuActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


//        btnSecondResign.setOnClickListener(mResignListener);
        return v;
    }



    public void directToLoginActivity(Boolean result) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    }
