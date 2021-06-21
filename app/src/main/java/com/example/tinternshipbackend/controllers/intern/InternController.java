package com.example.tinternshipbackend.controllers.intern;

import android.content.Context;

import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.responses.intern.InternHttp;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

public class InternController {
    Context context;

    public InternController(Context context) {
        this.context = context;
    }

    public void saveIntern(Intern intern, HttpResponse<InternHttp> onResponse) {
        InternHttp internHttp = new InternHttp(intern);

        new HttpClient<InternHttp>(context).post("interns/user", internHttp, onResponse, InternHttp.class);
    }

    public void getIntern(String userId, HttpResponse< Intern > onResponse) {
        new HttpClient<Intern>(context).get("interns/user/" + userId, onResponse, Intern.class);
    }
}

