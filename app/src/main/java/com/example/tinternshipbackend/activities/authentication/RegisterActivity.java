package com.example.tinternshipbackend.activities.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.account_management.DecideUserTypeActivity;
import com.example.tinternshipbackend.controllers.authentication.RegisterController;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.responses.authentication.LoginResponse;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

public class RegisterActivity extends AppCompatActivity {
    private RegisterController registerController;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.registerController = new RegisterController(this);
        this.mContext = this;
        setupListeners();
    }

    private void setupListeners() {
        Button registerButton = (Button) findViewById(R.id.registerBtn);
        Button gotoLoginPage = (Button) findViewById(R.id.gotLoginPage);

        registerButton.setOnClickListener(v -> register());

        gotoLoginPage.setOnClickListener((View.OnClickListener) v -> {
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        });
    }

    private void register() {
        String email = ((EditText)findViewById(R.id.emailEdit)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextTextPassword)).getText().toString();

        User user = new User(email, password);
        registerController.register(user, new HttpResponse<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                Intent decideUserTypeActivity = new Intent(mContext, DecideUserTypeActivity.class);
                startActivity(decideUserTypeActivity);
                ToastUtil.showLongToast(mContext,"You are now registered!");
            }

            @Override
            public void onError(String error) {
                if(error != null) {
                    ToastUtil.showLongToast(mContext,error);
                }
                else {
                    ToastUtil.showLongToast(mContext,"Invalid register credentials.");
                }
            }
        });
    }
}