package com.example.tinternshipbackend.controllers.authentication;
import android.content.Context;
import android.util.Log;

import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.responses.authentication.LoginResponse;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;


public class LoginController {
    AuthController authController;
    Context context;
    public LoginController(Context context) {
        this.context = context;
        authController = new AuthController(context);
    }

    public void login(User user, HttpResponse<LoginResponse> response) {
        HttpResponse<LoginResponse> onResponse = new HttpResponse<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                authController.setJWTKey(data.token);
                response.onSuccess(data);
            }

            @Override
            public void onError(String error) {
                response.onError(error);
            }
        };
        new HttpClient<LoginResponse>(context).post("/auth/login", user, onResponse, LoginResponse.class);
    }
}
