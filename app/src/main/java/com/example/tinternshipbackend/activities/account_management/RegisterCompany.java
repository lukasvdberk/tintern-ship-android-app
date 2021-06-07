package com.example.tinternshipbackend.activities.account_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.models.Company;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

public class RegisterCompany extends AppCompatActivity {
    CompanyController companyController;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        this.companyController = new CompanyController(this);
        this.mContext = this;
        setupListeners();
    }

    private void setupListeners() {
        Button profileButton = (Button) findViewById(R.id.createProfileBtn);

        profileButton.setOnClickListener(v -> {
            registerCompany();
        });
    }

    private void registerCompany() {
        // TODO add profile pic
        String companyName = ((EditText) findViewById(R.id.companyName)).getText().toString();
        String aboutTheCompany = ((EditText) findViewById(R.id.aboutTheCompany)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.companyPhoneNumber)).getText().toString();

        Company company = new Company(companyName, aboutTheCompany, phoneNumber);

        this.companyController.saveCompany(company, new HttpResponse<Company>() {
            @Override
            public void onSuccess(Company data) {
                // TODO add redirect
                ToastUtil.showLongToast(mContext, "Company information was saved!");
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to save company information. Try again later!");
            }
        });
    }
}