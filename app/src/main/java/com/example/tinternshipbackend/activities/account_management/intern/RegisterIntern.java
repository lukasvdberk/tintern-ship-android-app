package com.example.tinternshipbackend.activities.account_management.intern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.adapters.EducationArrayAdapter;
import com.example.tinternshipbackend.controllers.education.EducationController;
import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;

public class RegisterIntern extends AppCompatActivity {
    Context mContext;
    EducationController educationController;
    ArrayList<Education> allEducations = new ArrayList<Education>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_intern);

        this.educationController = new EducationController(this);
        setupDrownDown();
    }

    private void setupDrownDown() {
        Spinner educationDropdown = findViewById(R.id.dropdown);
        //get the spinner from the xml.
        educationController.getAllEducations(new HttpResponse<ArrayList<Education>>() {
            @Override
            public void onSuccess(ArrayList<Education> data) {
                allEducations.addAll(data);
                EducationArrayAdapter adapter = new EducationArrayAdapter(getApplicationContext(), allEducations);

                educationDropdown.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                Log.d("EDUCATIONS", error);
                ToastUtil.showLongToast(mContext, "Failed to fetch educations");
            }
        });
    }
}