package com.example.tinternshipbackend.controllers.company;

import android.content.Context;

import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

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
}
