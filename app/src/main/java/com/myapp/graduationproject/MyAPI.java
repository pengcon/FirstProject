package com.myapp.graduationproject;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyAPI {
    @POST("myapp/api-endpoint/")
    Call<ResponseBody> postData(@Body SubmitData data);

    @GET("another-api-endpoint/")
    Call<ResponseBody> getData();
}