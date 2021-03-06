package com.example.tinternshipbackend.controllers.authentication;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthController {
    Context context;
    SharedPreferences sharedPref;
    final String JWT_KEY = "JWT_KEY";

    public AuthController(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences("tokens", Context.MODE_PRIVATE);
    }

    public void setJWTKey(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(JWT_KEY, token);
        editor.apply();
    }

    public String getJWTKey() {
        return sharedPref.getString(JWT_KEY, "");
    }

    public boolean isAuthenticated() {
        return !this.getJWTKey().isEmpty();
    }
}
