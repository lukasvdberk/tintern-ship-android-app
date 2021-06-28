package com.example.tinternshipbackend.activities.match;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.databinding.ActivityLikeInternBinding;
import com.example.tinternshipbackend.databinding.ActivityMatchInternBinding;

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
//            String description = intent.getStringExtra("description");
            String phoneNumber = intent.getStringExtra("phoneNumber");

//            int imageid = intent.getIntExtra("imageid",R.drawable.a);

            binding.nameProfile.setText(name);
            binding.educationProfile.setText(educationName);
//            binding.relativeDescription.setText(description);
            binding.phoneProfile.setText(phoneNumber);
//            binding.profileImage.setImageResource(imageid);


        }
    }
}