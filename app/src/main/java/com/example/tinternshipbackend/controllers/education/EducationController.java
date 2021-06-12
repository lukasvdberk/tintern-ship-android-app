package com.example.tinternshipbackend.controllers.education;

import android.content.Context;

import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class EducationController {
    Context context;

    public EducationController(Context context) {
        this.context = context;
    }

    public void getAllEducations(HttpResponse<ArrayList<Education>> onResponse) {
        HttpResponse<ArrayList<Object>> parseResponse = new HttpResponse<ArrayList<Object>>() {
            @Override
            public void onSuccess(ArrayList<Object> data) {
                // TODO refactor this by doing it automatically
                ArrayList<Education> educations = new ArrayList<Education>();
                for(Object object: data) {
                    LinkedTreeMap<Object,Object> t = (LinkedTreeMap) object;
                    educations.add(new Education(t.get("id").toString(), t.get("name").toString()));
                }
                onResponse.onSuccess(educations);
            }

            @Override
            public void onError(String error) {
                if(error != null) {
                    onResponse.onError(error);
                } else {
                    onResponse.onError("An network activity occurred");
                }
            }
        };
        new HttpClient<ArrayList<Object>>(context).getList("educations/", parseResponse);
    }
}
