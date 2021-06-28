package com.example.tinternshipbackend.activities.like;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.databinding.ActivityLikeInternBinding;
import com.example.tinternshipbackend.viewUtil.DownloadImageAndSet;

public class LInternActivity extends  AppCompatActivity{

    ActivityLikeInternBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLikeInternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){

            String name = intent.getStringExtra("name");
            String educationName = intent.getStringExtra("educationName");
            String description = intent.getStringExtra("description");

            binding.nameProfile.setText(name);
            binding.educationProfile.setText(educationName);
            binding.relativeDescription.setText(description);
            String avatarUrl = intent.getStringExtra("avatarUrl");
            new DownloadImageAndSet(binding.profileImage, this).execute(avatarUrl);
        }
    }
}