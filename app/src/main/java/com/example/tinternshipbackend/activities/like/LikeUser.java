package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class LikeUser extends AppCompatActivity {
    private LikeController likeController;
    private CompanyController companyController;
    private AuthController authController;
    private int index = 0;
    private Context mContext;
    private List<Company> listOfCompanies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        this.likeController = new LikeController(this);
        this.companyController = new CompanyController(this);
        this.authController = new AuthController(this);
        this.mContext = this;
//        getListOfAllCompanies();

        companyController.getAllCompanies(new HttpResponse<ArrayList<Company>>() {
            @Override
            public void onSuccess(ArrayList<Company> data) {
                listOfCompanies.addAll(data);
            }

            @Override
            public void onError(String error) {
                System.out.println("ER GAAT IETS FOUT I GUESS");
                System.out.println(error);
            }
        });

        setupListeners();



    }
    private void setupListeners() {
        Button likeButton = (Button) findViewById(R.id.likeBtn);
        Button dislikeButton = (Button) findViewById(R.id.dislikeBtn);

        likeButton.setOnClickListener(v -> like());
        dislikeButton.setOnClickListener(v -> dislike());
    }

    private void getListOfAllCompanies() {
        companyController.getAllCompanies(new HttpResponse<ArrayList<Company>>() {
            @Override
            public void onSuccess(ArrayList<Company> data) {
                listOfCompanies.addAll(data);
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
    }

    private void like() {

        System.out.println(listOfCompanies);
//        this.index += 1;
//
//        System.out.println(this.index);

//        System.out.println(authController.getJWTKey());
//        System.out.println("like you girl");
    }

    private void dislike() {
        System.out.println("Dislike you girl");
    }
}
