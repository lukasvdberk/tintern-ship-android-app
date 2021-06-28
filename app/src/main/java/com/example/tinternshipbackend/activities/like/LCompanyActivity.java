package com.example.tinternshipbackend.activities.like;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.databinding.ActivityLikeCompanyBinding;

public class LCompanyActivity extends AppCompatActivity {

    ActivityLikeCompanyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLikeCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){

            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
//            int imageid = intent.getIntExtra("imageid",R.drawable.a);

            binding.nameProfile.setText(name);
            binding.relativeDescription.setText(description);
//            binding.profileImage.setImageResource(imageid);


        }
    }
}
