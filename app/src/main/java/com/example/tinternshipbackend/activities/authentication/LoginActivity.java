package com.example.tinternshipbackend.activities.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.MainActivity;
import com.example.tinternshipbackend.activities.account_management.DecideUserTypeActivity;
import com.example.tinternshipbackend.controllers.authentication.LoginController;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.responses.authentication.LoginResponse;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tinternshipbackend.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    private ActivityLoginBinding binding;
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        this.loginController = new LoginController(binding.toolbar.getContext());
        this.mContext = this;
        setupListeners();
    }

    private void setupListeners() {
        Button loginButton = (Button) findViewById(R.id.loginBtn);

        loginButton.setOnClickListener(v -> login());
    }

    private void login() {
        String email = ((EditText)findViewById(R.id.emailEdit)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextTextPassword)).getText().toString();

        User user = new User(null, email, password);
        loginController.login(user, new HttpResponse<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                Intent decideUserTypeActivity = new Intent(mContext, MainActivity.class);
                startActivity(decideUserTypeActivity);
                showToast("You are logged in!");
            }

            @Override
            public void onError(String error) {
                if(error != null) {
                    showToast(error);
                }
                else {
                    showToast("Invalid register credentials.");
                }
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

}