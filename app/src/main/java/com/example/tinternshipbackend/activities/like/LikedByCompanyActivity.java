package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.adapters.CompanyLikesAdapter;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.databinding.ActivityLikesBinding;
import com.example.tinternshipbackend.models.Like;
import com.example.tinternshipbackend.models.User;

import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class LikedByCompanyActivity extends AppCompatActivity {

    ActivityLikesBinding binding;

    private LikeController likeController;
    private UserController userController;
    private CompanyController companyController;
    private Context mContext;
    private User user;
    private List<Like> listOfLikes = new ArrayList<>();
    private ArrayList<Company> companiesWhoLikedMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = this;

        this.likeController = new LikeController(this);
        this.userController = new UserController( this);
        this.companyController = new CompanyController(this);

        binding = ActivityLikesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Company company = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 1", "Dit is bedrijf 1", "0638576839");
//        Company company2 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 2", "Dit is bedrijf 2", "0612345678");
//        Company company3 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 3", "Dit is bedrijf 3", "0687654321");
//        Company company4 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 4", "Dit is bedrijf 4", "0638576839");
//        Company company5 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 5", "Dit is bedrijf 5", "0612345678");
//        Company company6 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 6", "Dit is bedrijf 6", "0687654321");
//        Company company7 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 7", "Dit is bedrijf 7", "0687654321");
//        Company company8 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 8", "Dit is bedrijf 8", "0687654321");
//
//        companiesWhoLikedMe.add(company);
//        companiesWhoLikedMe.add(company2);
//        companiesWhoLikedMe.add(company3);
//        companiesWhoLikedMe.add(company4);
//        companiesWhoLikedMe.add(company5);
//        companiesWhoLikedMe.add(company6);
//        companiesWhoLikedMe.add(company7);
//        companiesWhoLikedMe.add(company8);

        companiesWhoLikedMe = new ArrayList<>();
        CompanyLikesAdapter companyLikesAdapter = new CompanyLikesAdapter(LikedByCompanyActivity.this, companiesWhoLikedMe);

        binding.likesView.setAdapter(companyLikesAdapter);
        binding.likesView.setClickable(true);
        binding.likesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(LikedByCompanyActivity.this, CompanyActivity.class);
                i.putExtra("name", companiesWhoLikedMe.get(position).getName());
                i.putExtra("phoneNumber", companiesWhoLikedMe.get(position).getPhoneNumber());
                i.putExtra("description", companiesWhoLikedMe.get(position).getDescription());
                startActivity(i);
            }
        });

        getMe();
    }

    private void getCompaniesBelongingToLikes() {
        for (int i = 0; i < listOfLikes.size(); i++) {
            companyController.getCompanyByUserId(listOfLikes.get(i).getFromUserId(), new HttpResponse<Company>() {
                @Override
                public void onSuccess(Company data) {
                    companiesWhoLikedMe.add(data);

                    CompanyLikesAdapter companyLikesAdapter = new CompanyLikesAdapter(LikedByCompanyActivity.this, companiesWhoLikedMe);

                    binding.likesView.setAdapter(companyLikesAdapter);
                }

                @Override
                public void onError(String error) {
                    System.out.println("HEROOOOO");
                }
            });
        }
    }

    private void getLikesFromUser() {
        likeController.getLikes(user.getId(), new HttpResponse<ArrayList<Like>>() {
            @Override
            public void onSuccess(ArrayList<Like> data) {
                listOfLikes.addAll(data);
                ToastUtil.showLongToast(mContext, "Success, fetched all likes from this user");
                getCompaniesBelongingToLikes();
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to fetch likes for this user");
            }
        });
    }

    private void getMe() {
        userController.getMe(new HttpResponse<User>() {
            @Override
            public void onSuccess(User data) {
                user = data;
                ToastUtil.showLongToast(mContext, "Success, fetched me");
                getLikesFromUser();
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to get me");
            }
        });
    }
}
