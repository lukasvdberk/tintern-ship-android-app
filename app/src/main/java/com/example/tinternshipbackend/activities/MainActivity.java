package com.example.tinternshipbackend.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tinternshipbackend.HomeFragment;
import com.example.tinternshipbackend.LikesFragment;
import com.example.tinternshipbackend.MatchesFragment;
import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.authentication.RegisterActivity;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        AuthController authController = new AuthController(this);

        // TODO refactor this by setting in the httpclient if it received a 401 and then redirect to the given page.
        if(!authController.isAuthenticated()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else {
            // TODO redirect to home page if authenticated or something.
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment = null;
            switch (menuItem.getItemId())
            {
                case R.id.matches:
                    fragment = new MatchesFragment();
                    break;

                case R.id.home:
                    fragment = new HomeFragment();
                    break;

                case R.id.like:
                    fragment = new LikesFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            return true;
        }
    };
}