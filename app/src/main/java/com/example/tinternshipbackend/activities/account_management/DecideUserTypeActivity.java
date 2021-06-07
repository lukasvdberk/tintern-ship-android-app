package com.example.tinternshipbackend.activities.account_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tinternshipbackend.R;

public class DecideUserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide_user_type);

        setupListeners();
    }

    private void setupListeners() {
        Button registerAsIntern = findViewById(R.id.registerAsIntern);
        Button registerAsCompany = findViewById(R.id.registerAsCompany);

        registerAsIntern.setOnClickListener(v -> {

        });

        registerAsCompany.setOnClickListener(v -> {

        });
    }
}