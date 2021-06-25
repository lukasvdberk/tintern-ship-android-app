package com.example.tinternshipbackend.controllers.matchController;

import android.content.Context;

import com.example.tinternshipbackend.models.Like;
import com.example.tinternshipbackend.models.Match;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

public class MatchController {
    Context context;

    public MatchController(Context context) {
        this.context = context;
    }

    public void checkIfMatchIsAvailable(HttpResponse<Boolean> onResponse) {
        new HttpClient<Boolean>(context).get("matches", onResponse, Boolean.class);
    }

    public void saveMatch(Match match, HttpResponse<Match> onResponse) {
        new HttpClient<Match>(context).post("matches", match, onResponse, Match.class);
    }
}
