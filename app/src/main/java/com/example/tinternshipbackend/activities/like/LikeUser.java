package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.authentication.LoginActivity;
import com.example.tinternshipbackend.controllers.Like.LikeController;

public class LikeUser extends AppCompatActivity {
    private LikeController likeController;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        this.likeController = new LikeController(this);
        this.mContext = this;
        setupListeners();

    }
    private void setupListeners() {
        Button likeButton = (Button) findViewById(R.id.likeBtn);
        Button dislikeButton = (Button) findViewById(R.id.dislikeBtn);

        likeButton.setOnClickListener(v -> like());
        dislikeButton.setOnClickListener(v -> dislike());
    }

    private void like() {
        System.out.println("like you girl");
    }

    private void dislike() {
        System.out.println("Dislike you girl");
    }
}
