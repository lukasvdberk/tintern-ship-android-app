package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.intern.InternController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.responses.authentication.LoginResponse;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class LikeUser extends AppCompatActivity {
    private LikeController likeController;
    private CompanyController companyController;
    private UserController userController;
    private AuthController authController;
    private InternController internController;
    private User user;
    private Intern intern;

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
        this.userController = new UserController(this);
        this.internController = new InternController(this);
        this.mContext = this;

        getMe();
        getListOfAllCompanies();

        setupListeners();

    }

    private void getIntern() {
        internController.getIntern(user.getId(), new HttpResponse<Intern>() {
            @Override
            public void onSuccess(Intern data) {
                intern = data;
                System.out.println(intern);
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
    }

    private void getMe() {
        userController.getMe(new HttpResponse<User>() {
            @Override
            public void onSuccess(User data) {
                user = data;
                getIntern();
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
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
        if(index < listOfCompanies.size()) {
            Company company = listOfCompanies.get(index);

//            Company company = new Company(
//                    listOfCompanies.get(index).getId(),
//                    listOfCompanies.get(index).getName(),
//                    listOfCompanies.get(index).getDescription(),
//                    listOfCompanies.get(index).getPhoneNumber()
//            );

            System.out.println(company);
//            System.out.println(listOfCompanies.get(index).getId());
            index += 1;
        }

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
