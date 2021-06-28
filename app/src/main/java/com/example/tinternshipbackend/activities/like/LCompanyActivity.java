package com.example.tinternshipbackend.activities.like;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.databinding.ActivityLikeCompanyBinding;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
            String avatarUrl = intent.getStringExtra("avatarUrl");

            binding.nameProfile.setText(name);
            binding.relativeDescription.setText(description);

            try {
                URL imageUrl = new URL(avatarUrl);
                Bitmap mIcon_val = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                binding.profileImage.setImageBitmap(mIcon_val);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
