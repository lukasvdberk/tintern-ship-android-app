package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.controllers.Like.LikeController;
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
    private Context mContext;
    private User user;
    private List<Like> listOfLikes = new ArrayList<>();
    private List<Company> companiesWhoLikedMe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = this;

        this.likeController = new LikeController(this);
        this.userController = new UserController( this);

        getMe();


        binding = ActivityLikesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ListAdapter listAdapter = new ListAdapter(LikedByCompanyActivity.this, companiesWhoLikedMe);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(LikedByCompanyActivity.this,LikeCompanyActivity.class);
                i.putExtra("name",companiesWhoLikedMe.get(position).getName();
                i.putExtra("phoneNumber",companiesWhoLikedMe.get(position).getPhoneNumber();
                i.putExtra("description",companiesWhoLikedMe.get(position).getDescription();
                startActivity(i);

            }
        });
    }

    private void getLikesFromUser() {
        likeController.getLikes(user.getId(), new HttpResponse<ArrayList<Like>>() {
            @Override
            public void onSuccess(ArrayList<Like> data) {
                listOfLikes.addAll(data);
                ToastUtil.showLongToast(mContext, "Success, fetched all fitting internship projects");
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to get company from project");
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
