package com.example.tinternshipbackend.activities.account_management.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.MainActivity;
import com.example.tinternshipbackend.adapters.EducationArrayAdapter;
import com.example.tinternshipbackend.adapters.ProjectCompanyAdapter;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.education.EducationController;
import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.models.company.CompanyProject;
import com.example.tinternshipbackend.responses.company.SaveCompanyProject;
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
    Company projectsForCompany;

    CompanyController companyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO get company via intent
        super.onCreate(savedInstanceState);

        this.projectsForCompany = (Company) getIntent().getSerializableExtra("company");

        if(projectsForCompany == null) {
            Log.d("ERROR", "Missing required intent extra`company` of type Company");
        }

        this.companyController = new CompanyController(this);

        setContentView(R.layout.activity_manage_company_internships);

        this.educationController = new EducationController(this);
        this.mContext = this;
        this.educationDropdown = findViewById(R.id.dropdown);

        this.companyProjectsListView = (ListView) findViewById(R.id.companyProjects);
        this.projectsAdapter = new ProjectCompanyAdapter(this,0, companyProjects);
        this.projectsAdapter.onClose(project -> {
            companyProjects.remove(project);
            projectsAdapter.remove(project);
        });
        companyProjectsListView.setAdapter(projectsAdapter);

        setupDrownDown();
        setupListeners();
        getExistingProjects();
    }

    private void getExistingProjects() {
        this.companyController.getCompanyProjects(this.projectsForCompany, new HttpResponse<ArrayList<CompanyProject>>() {
            @Override
            public void onSuccess(ArrayList<CompanyProject> data) {
                Log.d("PROJECT", String.valueOf(data));
                companyProjects.addAll(data);
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Could not fetch existing company projects");
            }
        });
    }


    private void setupListeners() {
        Button addInternship = findViewById(R.id.addInternshipProject);
        Button saveChanges = findViewById(R.id.saveProjectChanges);

        addInternship.setOnClickListener(v -> addCompanyProject());
        saveChanges.setOnClickListener(v -> saveChanges());
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

        CompanyProject project = new CompanyProject(description, selectedEducation, projectsForCompany);
        companyProjects.add(project);
        this.projectsAdapter.add(project);
    }

    private void saveChanges() {
        HttpResponse<SaveCompanyProject> companyProjectSaved = new HttpResponse<SaveCompanyProject>() {
            int requestsMade = 0;
            @Override
            public void onSuccess(SaveCompanyProject data) {
                ToastUtil.showLongToast(mContext, "Project company saved");
                requestsMade++;

                if(requestsMade == companyProjects.size()) {
                    Intent mainActivity = new Intent(mContext, MainActivity.class);
                    startActivity(mainActivity);
                }
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to save a company project");
                requestsMade++;
            }
        };

        for(CompanyProject project : companyProjects) {
            this.companyController.addCompanyProject(project, companyProjectSaved);
        }
    }
}