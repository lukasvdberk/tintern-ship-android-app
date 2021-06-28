package com.example.tinternshipbackend.activities.match;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.databinding.ActivityLikeInternBinding;
import com.example.tinternshipbackend.databinding.ActivityMatchInternBinding;
import com.example.tinternshipbackend.viewUtil.DownloadImageAndSet;

public class MInternActivity extends  AppCompatActivity{

    ActivityMatchInternBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchInternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){

            String name = intent.getStringExtra("name");
            String educationName = intent.getStringExtra("educationName");
            String phoneNumber = intent.getStringExtra("phoneNumber");

            binding.nameProfile.setText(name);
            binding.educationProfile.setText(educationName);
            binding.phoneProfile.setText(phoneNumber);

            String avatarUrl = intent.getStringExtra("avatarUrl");
            new DownloadImageAndSet(binding.profileImage, this).execute(avatarUrl);
        }
    }
}