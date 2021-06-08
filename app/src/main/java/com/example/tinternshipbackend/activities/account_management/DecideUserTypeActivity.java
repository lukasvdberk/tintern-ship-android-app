package com.example.tinternshipbackend.activities.account_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.account_management.company.RegisterCompany;

public class DecideUserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide_user_type);

        setupListeners();
    }

    private void setupListeners() {
        Button registerAsInternBtn = findViewById(R.id.registerAsIntern);
        Button registerAsCompanyBtn = findViewById(R.id.registerAsCompany);

        registerAsInternBtn.setOnClickListener(v -> {
            Intent registerAsInternIntent = new Intent(this, RegisterIntern.class);
            startActivity(registerAsInternIntent);
        });

        registerAsCompanyBtn.setOnClickListener(v -> {
            Intent registerAsCompanyIntent = new Intent(this, RegisterCompany.class);
            startActivity(registerAsCompanyIntent);
        });
    }
}