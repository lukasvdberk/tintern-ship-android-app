package com.example.tinternshipbackend.controllers.education;

import android.content.Context;

import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

public class EducationController {
    Context context;

    public EducationController(Context context) {
        this.context = context;
    }

    public void getAllEducations(HttpResponse<Education[]> onResponse) {
        new HttpClient<Education[]>(context).get("educations/", onResponse);
    }
}
