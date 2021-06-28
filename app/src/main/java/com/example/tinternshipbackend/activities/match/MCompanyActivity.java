package com.example.tinternshipbackend.activities.match;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.databinding.ActivityLikeCompanyBinding;
import com.example.tinternshipbackend.databinding.ActivityMatchCompanyBinding;

public class MCompanyActivity extends AppCompatActivity {

    ActivityMatchCompanyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){

            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
            String phoneNumber = intent.getStringExtra("phoneNumber");
//            int imageid = intent.getIntExtra("imageid",R.drawable.a);

            binding.nameProfile.setText(name);
            binding.relativeDescription.setText(description);
            binding.phoneProfile.setText(phoneNumber);
//            binding.profileImage.setImageResource(imageid);


        }
    }
}
