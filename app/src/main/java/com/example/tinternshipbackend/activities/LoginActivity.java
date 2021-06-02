package com.example.tinternshipbackend.activities;

import android.os.Bundle;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.models.User;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tinternshipbackend.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    }

    private void setupListeners() {
        Button loginButton = (Button) findViewById(R.id.loginBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void login() {
        String email = ((EditText)findViewById(R.id.emailEdit)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextTextPassword)).getText().toString();

        User user = new User(email, password);

    }

}