package com.example.tinternshipbackend.activities.account_management.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.adapters.EducationArrayAdapter;
import com.example.tinternshipbackend.controllers.education.EducationController;
import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;

public class ManageCompanyInternshipsActivity extends AppCompatActivity {
    ArrayList<Education> allEducations = new ArrayList<Education>();
    EducationController educationController;
    Spinner educationDropdown;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_company_internships);

        this.educationController = new EducationController(this);
        this.mContext = this;
        educationDropdown = findViewById(R.id.dropdown);
        setupDrownDown();
    }

    private void setupDrownDown() {
        //get the spinner from the xml.
        educationController.getAllEducations(new HttpResponse<ArrayList<Education>>() {
            @Override
            public void onSuccess(ArrayList<Education> data) {
                allEducations.addAll(data);
                ArrayAdapter<Education> adapter = new EducationArrayAdapter(mContext, 0 , allEducations);
                educationDropdown.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                Log.d("EDUCATIONS", error);
                ToastUtil.showLongToast(mContext, "Failed to fetch educations");
            }
        });
    }

    private void submit() {
        String description = ((EditText) findViewById(R.id.aboutTheCompany)).getText().toString();
        Education selectedEducation = allEducations.get(educationDropdown.getSelectedItemPosition());
    }
}