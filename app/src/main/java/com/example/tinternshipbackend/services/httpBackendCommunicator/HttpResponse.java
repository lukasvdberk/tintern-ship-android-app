package com.example.tinternshipbackend.services.httpBackendCommunicator;

import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.responses.authentication.LoginResponse;

import java.util.List;

public interface HttpResponse<T> {
    public void onSuccess(T data);

    // TODO refactor to seperate type which also includes body?
    public void onError(String error);
}
