package com.example.tinternshipbackend.activities.like;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.databinding.ActivityLikeCompanyBinding;
import com.example.tinternshipbackend.viewUtil.DownloadImageAndSet;

import java.io.IOException;
import java.io.InputStream;
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

            new DownloadImageAndSet(binding.profileImage, this).execute(avatarUrl);
        }
    }
}
