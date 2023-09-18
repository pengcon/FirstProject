package com.myapp.graduationproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripRecommendFragment2 extends Fragment {
    private SharedViewModel sharedViewModel;
    Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_recommend2,container, false);

        submitButton= (Button) view.findViewById(R.id.submit);

        //뷰모델 선언
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        Spinner actspinner = (Spinner) view.findViewById(R.id.actspinner);
        ArrayAdapter<CharSequence> actadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.places_array, android.R.layout.simple_spinner_item);
        actadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actspinner.setAdapter(actadapter);

        Spinner foodspinner = (Spinner) view.findViewById(R.id.foodspinner);
        ArrayAdapter<CharSequence> foodadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.places_array, android.R.layout.simple_spinner_item);
        foodadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodspinner.setAdapter(foodadapter);




        actspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selectedActivity = (String) parent.getItemAtPosition(position);
                sharedViewModel.setData3(selectedActivity);
                // 여기에서 selectedPlace를 사용하여 필요한 작업 수행

                Toast.makeText(getContext(), "선택된 활동: " + selectedActivity, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 동작 구현
                Toast.makeText(getContext(), "아무것도 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();

            }
        });



        foodspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selectedFood = (String) parent.getItemAtPosition(position);
                sharedViewModel.setData4(selectedFood);
                // 여기에서 selectedPlace를 사용하여 필요한 작업 수행

                Toast.makeText(getContext(), "선택된 장소: " + selectedFood, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 동작 구현
                Toast.makeText(getContext(), "아무것도 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();

            }
        });



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build();

                // Retrofit 인스턴스 생성
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://18f7-14-34-31-165.ngrok-free.app/")
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MyAPI api = retrofit.create(MyAPI.class);

                SubmitData submitData = new SubmitData();
                submitData.setData1(sharedViewModel.getData1().getValue());
                submitData.setData2(sharedViewModel.getData2().getValue());
                submitData.setData3(sharedViewModel.getData3().getValue());
                submitData.setData4(sharedViewModel.getData4().getValue());

                Log.e("DATA", "DATA: " +submitData.getData1());








                Call<ResponseBody> call = api.postData(submitData);

// POST 요청 실행

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // 응답 처리
                        if (response.isSuccessful()) {  // HTTP status code 2xx
                            Log.d("API", "POST 성공");

                        } else {  // HTTP status code 4xx, 5xx 등
                            Log.e("API", "Request failed with status code: " + response.code());

                        }



                        // 상위 액티비티 종료 및 다른 액티비티 시작 코드 작성

                        Activity activity = getActivity();
                        if (activity != null) {

                            activity.finish();
                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivity(intent);
                        }
                            }



                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("APIFail", "Request failed with error: " + t.getMessage(), t);
                    }

                });
            }
        });

        return view;
    }
}