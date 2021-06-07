package com.example.tinternshipbackend.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.controllers.authentication.AuthController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthController authController = new AuthController(this);

        // TODO refactor this by setting in the httpclient if it received a 401 and then redirect to the given page.
        if(!authController.isAuthenticated()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            // TODO redirect to home page if authenticated or something.
        }
    }
}