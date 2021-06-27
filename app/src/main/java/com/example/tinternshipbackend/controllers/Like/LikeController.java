package com.example.tinternshipbackend.controllers.Like;

import android.content.Context;

import com.example.tinternshipbackend.models.Like;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.responses.intern.InternHttp;
import com.example.tinternshipbackend.responses.like.LikeHttp;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LikeController {
    Context context;

    public LikeController(Context context) {
        this.context = context;
    }

    public void saveLike(Like like, HttpResponse<Like> onResponse) {
        new HttpClient<Like>(context).post("likes", like, onResponse, Like.class);
    }

    public void getLikes(String userId, HttpResponse<ArrayList<Like>> onRespone) {
        Type type = new TypeToken<ArrayList<Like>>() {}.getType();
        new HttpClient<ArrayList<Like>>(context)
                .getList("likes/user/" + userId, onRespone, type);
    }
}
