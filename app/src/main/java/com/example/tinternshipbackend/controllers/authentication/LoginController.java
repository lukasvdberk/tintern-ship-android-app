package com.example.tinternshipbackend.controllers.authentication;

import android.content.Context;

import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.responses.authentication.LoginResponse;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

import java.util.Map;

public class LoginController {
    Context context;


    public LoginController(Context context) {
        this.context = context;
    }

    public void login(User user, HttpResponse<LoginResponse> response) {
        new HttpClient<LoginResponse>(context).post("/auth/login", user, response);
    }
}
