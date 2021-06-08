package com.example.tinternshipbackend.activities.account_management.company;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.controllers.education.EducationController;
import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class ManageCompanyInternshipsActivity extends AppCompatActivity {
    EducationController educationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_company_internships);

        this.educationController = new EducationController(this);
        setupDrownDown();
    }

    private void setupDrownDown() {
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.dropdown);
        educationController.getAllEducations(new HttpResponse<ArrayList<Education>>() {
            @Override
            public void onSuccess(ArrayList<Education> data) {
                Log.d("EDUCATIONS", data.toString());
            }

            @Override
            public void onError(String error) {
                Log.d("EDUCATIONS", error);
            }
        });
        //create a list of items for the spinner.
        // TODO fetch from API and change to custom adapter
        String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
}