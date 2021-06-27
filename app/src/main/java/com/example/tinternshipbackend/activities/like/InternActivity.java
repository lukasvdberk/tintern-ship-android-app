package com.example.tinternshipbackend.activities.like;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.databinding.ActivityInternBinding;

public class InternActivity extends  AppCompatActivity{

    ActivityInternBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){

            String name = intent.getStringExtra("name");
            String phone = intent.getStringExtra("phoneNumber");
            String education = intent.getStringExtra("education");
            String description = intent.getStringExtra("description");
//            int imageid = intent.getIntExtra("imageid",R.drawable.a);

            binding.nameProfile.setText(name);
            binding.phoneProfile.setText(phone);
            binding.educationProfile.setText(education);
            binding.relativeDescription.setText(description);
//            binding.profileImage.setImageResource(imageid);


        }
    }
}