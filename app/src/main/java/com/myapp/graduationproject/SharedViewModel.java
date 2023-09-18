package com.myapp.graduationproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> data1 = new MutableLiveData<>();
    private final MutableLiveData<String> data2 = new MutableLiveData<>();
    private final MutableLiveData<String> data3 = new MutableLiveData<>();
    private final MutableLiveData<String> data4 = new MutableLiveData<>();

    public void setData1(String value) {
        data1.setValue(value);
    }

    public LiveData<String> getData1() {
        return data1;
    }

    public void setData2(String value) {
        data2.setValue(value);
    }

    public LiveData<String> getData2() {
        return data2;
    }


    public void setData3(String value) {
        data3.setValue(value);
    }

    public LiveData<String> getData3() {
        return data3;
    }

    public void setData4(String value) {
        data4.setValue(value);
    }

    public LiveData<String> getData4() {
        return data4;
    }

}


