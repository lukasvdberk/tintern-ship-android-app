package com.example.tinternshipbackend.services.httpBackendCommunicator;

import com.example.tinternshipbackend.models.User;

import java.util.List;

public interface HttpResponse<T> {
    public void onSuccess(Class<T> users);
    // TODO refactor to seperate type which also includes body?
    public void onError(String error);
}
