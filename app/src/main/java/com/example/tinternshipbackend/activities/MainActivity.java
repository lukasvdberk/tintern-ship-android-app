package com.example.tinternshipbackend.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tinternshipbackend.HomeFragment;
import com.example.tinternshipbackend.LikesFragment;
import com.example.tinternshipbackend.MatchesFragment;
import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.authentication.RegisterActivity;
import com.example.tinternshipbackend.activities.like.LikeCompanyActivity;
import com.example.tinternshipbackend.activities.like.LikeInternActivity;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        AuthController authController = new AuthController(this);

        if(!authController.isAuthenticated()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else {
            UserController userController = new UserController(this);
            userController.getMe(new HttpResponse<User>() {
                @Override
                public void onSuccess(User data) {
                    Intent intent = null;

                    if(data.isCompany()) {
                        intent = new Intent(mContext, LikeInternActivity.class);
                    }
                    if(data.isIntern()) {
                        intent = new Intent(mContext, LikeCompanyActivity.class);
                    }

                    startActivity(intent);
                }

                @Override
                public void onError(String error) {
                    ToastUtil.showLongToast(mContext,"Failed to get user");
                }
            });
        }
    }

}