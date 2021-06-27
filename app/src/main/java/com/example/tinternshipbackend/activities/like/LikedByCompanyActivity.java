package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.adapters.LikesAdapter;
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
    private ArrayList<Company> companiesWhoLikedMe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = this;

        this.likeController = new LikeController(this);
        this.userController = new UserController( this);
        this.companyController = new CompanyController(this);

        binding = ActivityLikesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Company company = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 1", "Dit is bedrijf 1", "1234567890");
        Company company2 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 2", "Dit is bedrijf 1", "1234567890");
        Company company3 = new Company("60d0ecc5277c8d40106887f0", "Bedrijf 3", "Dit is bedrijf 1", "1234567890");

        companiesWhoLikedMe.add(company);
        companiesWhoLikedMe.add(company2);
        companiesWhoLikedMe.add(company3);

        getMe();

        LikesAdapter likesAdapter = new LikesAdapter(LikedByCompanyActivity.this, companiesWhoLikedMe);

        binding.likesView.setAdapter(likesAdapter);
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
    }

    private void getCompaniesBelongingToLikes() {
        for (int i = 0; i < listOfLikes.size(); i++) {
            companyController.getCompanyByUserId(listOfLikes.get(i).getFromUserId(), new HttpResponse<Company>() {
                @Override
                public void onSuccess(Company data) {
                    companiesWhoLikedMe.add(data);
                }

                @Override
                public void onError(String error) {

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
