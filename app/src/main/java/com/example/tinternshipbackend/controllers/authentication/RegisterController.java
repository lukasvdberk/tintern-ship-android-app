package com.example.tinternshipbackend.controllers.authentication;

import android.content.Context;

import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.responses.authentication.LoginResponse;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

public class RegisterController {
    Context context;
    public RegisterController(Context context) {
        this.context = context;
    }

    public void register(User user, HttpResponse<LoginResponse> response) {
        new HttpClient<LoginResponse>(context).post("/auth/register", user, response, LoginResponse.class);
    }
}
