package com.myapp.graduationproject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import org.json.JSONObject;



interface ApiService {
    @FormUrlEncoded
    @POST("app_data/")
    Call<JSONObject> requestPOST(@Field("email") String email, @Field("password")
            String password ,@Field("name") String name, @Field("introduce") String introduce);

    @GET("app_data/")
    Call<ResponseBody> requestGET();
}
