package com.myapp.graduationproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class TripRecommendFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    Button nextbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_recommend,container, false);

        nextbutton= (Button) view.findViewById(R.id.next);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        Spinner locspinner = (Spinner) view.findViewById(R.id.locspinner);
        ArrayAdapter<CharSequence> locadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.places_array, android.R.layout.simple_spinner_item);
        locadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locspinner.setAdapter(locadapter);

        Spinner schspinner = (Spinner) view.findViewById(R.id.schspinner);
        ArrayAdapter<CharSequence> schadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.places_array, android.R.layout.simple_spinner_item);
        schadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schspinner.setAdapter(schadapter);




        locspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selectedPlace = (String) parent.getItemAtPosition(position);
                // 여기에서 selectedPlace를 사용하여 필요한 작업 수행
                sharedViewModel.setData1(selectedPlace);
                Toast.makeText(getContext(), "선택된 장소: " + selectedPlace, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 동작 구현
                Toast.makeText(getContext(), "아무것도 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        schspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selectedSchedule = (String) parent.getItemAtPosition(position);
                // 여기에서 selectedPlace를 사용하여 필요한 작업 수행
                sharedViewModel.setData2(selectedSchedule);
                Toast.makeText(getContext(), "선택된 날짜: " + selectedSchedule, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 동작 구현
                Toast.makeText(getContext(), "아무것도 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripRecommendFragment2 tripRecommendFragment2 = new TripRecommendFragment2();

                // FragmentTransaction을 시작합니다.
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // 현재 표시되고 있는 프래그먼트를 secondFragment로 교체합니다.
                transaction.replace(R.id.main_layout, tripRecommendFragment2);  // 'fragment_container'은 프래그먼트가 표시될 뷰의 ID입니다.

                // 이전 상태를 백스택에 추가하여 사용자가 'Back' 버튼을 눌렀을 때 이전 프래그먼트로 돌아갈 수 있게 합니다.
                transaction.addToBackStack(null);

                // 변경사항을 커밋하여 적용합니다.
                transaction.commit();
            }
        });






        return view;
    }
}