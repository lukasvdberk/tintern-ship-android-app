package com.example.tinternshipbackend.controllers.Like;

import android.content.Context;

import com.example.tinternshipbackend.models.Like;
import com.example.tinternshipbackend.responses.intern.InternHttp;
import com.example.tinternshipbackend.responses.like.LikeHttp;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

public class LikeController {
    Context context;

    public LikeController(Context context) {
        this.context = context;
    }

    public void saveLike(Like like, HttpResponse<LikeHttp> onResponse) {
        LikeHttp likeHttp = new LikeHttp();

        new HttpClient<LikeHttp>(context).post("likes", likeHttp, onResponse, LikeHttp.class);
    }
}
