package com.example.tinternshipbackend.responses.firebase;

public class FirebaseDeviceToken {
    String token;

    public FirebaseDeviceToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
