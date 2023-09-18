package com.myapp.graduationproject;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginUtils {
    public static void saveLoginMethod(Context context, String method) {
        SharedPreferences sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("method", method);
        editor.apply();
    }

    public static String getLoginMethod(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        return sharedPref.getString("method", "");
    }
}