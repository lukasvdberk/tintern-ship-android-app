package com.example.tinternshipbackend.controllers.company;

import android.content.Context;

import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.models.company.CompanyProject;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.responses.company.SaveCompanyProject;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CompanyController {
    Context context;

    public CompanyController(Context context) {
        this.context = context;
    }

    /**
     * Will add company information to the currently logged in user.
     * @param company
     */
    public void saveCompany(Company company, HttpResponse<Company> onResponse) {
        new HttpClient<Company>(context).post("companies/", company, onResponse, Company.class);
    }

    public void addCompanyProject(CompanyProject companyProject, HttpResponse<SaveCompanyProject> onResponse) {
        SaveCompanyProject saveCompanyProject = new SaveCompanyProject();
        saveCompanyProject.setEducationId(companyProject.getEducationId());
        saveCompanyProject.setCompanyId(companyProject.getCompanyId());
        saveCompanyProject.setDescription(companyProject.getDescription());

        new HttpClient<SaveCompanyProject>(context)
                .post("companies/internship-project", saveCompanyProject, onResponse, SaveCompanyProject.class);
    }

    public void getCompanyProjects(Company company, HttpResponse<ArrayList<CompanyProject>> onResponse) {
        Type type = new TypeToken<ArrayList<CompanyProject>>() {}.getType();
        new HttpClient<ArrayList<CompanyProject>>(context)
                .getList("companies/internship-project/" + company.getId(), onResponse, type);
    }

    public void getCompanyByCompanyId(String companyId, HttpResponse<Company> onResponse) {
        new HttpClient<Company>(context).get("companies/company/" + companyId, onResponse, Company.class);
    }

    public void getCompanyByUserId(String userId, HttpResponse<Company> onResponse) {
        new HttpClient<Company>(context).get("companies/userId/" + userId, onResponse, Company.class);
    }

    public void getCompany(String userId, HttpResponse<Company> onResponse) {
        new HttpClient<Company>(context).get("companies/user/" + userId, onResponse, Company.class);
    }


    public void getMyCompanyProjects(HttpResponse<ArrayList<CompanyProject>> onResponse) {
        Type type = new TypeToken<ArrayList<CompanyProject>>() {}.getType();
        new HttpClient<ArrayList<CompanyProject>>(context).getList("companies/projects", onResponse, type);
    }
}
