package com.example.tinternshipbackend.activities.account_management.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.adapters.EducationArrayAdapter;
import com.example.tinternshipbackend.adapters.ProjectCompanyAdapter;
import com.example.tinternshipbackend.controllers.education.EducationController;
import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.models.company.CompanyProject;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;

public class ManageCompanyInternshipsActivity extends AppCompatActivity {
    ArrayList<CompanyProject> companyProjects = new ArrayList<CompanyProject>();
    ArrayList<Education> allEducations = new ArrayList<Education>();
    EducationController educationController;
    ListView companyProjectsListView;
    ProjectCompanyAdapter projectsAdapter;
    Spinner educationDropdown;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO get company via intent
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_company_internships);

        this.educationController = new EducationController(this);
        this.mContext = this;
        this.educationDropdown = findViewById(R.id.dropdown);

        this.companyProjectsListView = (ListView) findViewById(R.id.companyProjects);
        this.projectsAdapter = new ProjectCompanyAdapter(this,0, companyProjects);
        companyProjectsListView.setAdapter(projectsAdapter);

        setupDrownDown();
        setupListeners();
    }

    private void setupListeners() {
        Button addInternship = findViewById(R.id.addInternshipProject);
        addInternship.setOnClickListener(v -> addCompanyProject());
    }

    private void setupDrownDown() {
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

    private void addCompanyProject() {
        String description = ((EditText) findViewById(R.id.aboutTheCompany)).getText().toString();
        Education selectedEducation = allEducations.get(educationDropdown.getSelectedItemPosition());

        CompanyProject project = new CompanyProject(description, selectedEducation, null);
        companyProjects.add(new CompanyProject(description, selectedEducation, null));
        this.projectsAdapter.add(project);
    }
}