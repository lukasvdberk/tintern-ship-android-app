package com.example.tinternshipbackend.controllers.Like;

import android.content.Context;

import com.example.tinternshipbackend.models.Like;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.responses.intern.InternHttp;
import com.example.tinternshipbackend.responses.like.LikeHttp;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

public class LikeController {
    Context context;

    public LikeController(Context context) {
        this.context = context;
    }

    public void saveLike(Like like, HttpResponse<Like> onResponse) {
        new HttpClient<Like>(context).post("likes", like, onResponse, Like.class);
    }

//    public void saveCompany(Company company, HttpResponse<Company> onResponse) {
//        new HttpClient<Company>(context).post("companies/", company, onResponse, Company.class);
//    }
}
