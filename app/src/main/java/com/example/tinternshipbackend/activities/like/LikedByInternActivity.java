package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.adapters.InternLikesAdapter;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.education.EducationController;
import com.example.tinternshipbackend.controllers.intern.InternController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.databinding.ActivityLikesBinding;
import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.models.Like;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class LikedByInternActivity extends AppCompatActivity {

    ActivityLikesBinding binding;

    private LikeController likeController;
    private UserController userController;
    private InternController internController;
    private EducationController educationController;

    private Context mContext;
    private User user;
    private Education education;

    private List<Like> listOfLikes = new ArrayList<>();
    private List<Education> listOfEducations = new ArrayList<>();
    private ArrayList<Intern> internsWhoLikedMe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = this;

        this.likeController = new LikeController(this);
        this.userController = new UserController( this);
        this.internController = new InternController(this);
        this.educationController = new EducationController(this);

        binding = ActivityLikesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getMe();

        InternLikesAdapter internLikesAdapter = new InternLikesAdapter(LikedByInternActivity.this, internsWhoLikedMe);

        binding.likesView.setAdapter(internLikesAdapter);
        binding.likesView.setClickable(true);
        binding.likesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println(internsWhoLikedMe.get(position).getEducationId());

                Intent i = new Intent(LikedByInternActivity.this, LInternActivity.class);
                i.putExtra("name", internsWhoLikedMe.get(position).getName());
                i.putExtra("age", internsWhoLikedMe.get(position).getAge());
                i.putExtra("educationName", listOfEducations.get(position).getName());
//                i.putExtra("phoneNumber", internsWhoLikedMe.get(position).getPhoneNumber());
                i.putExtra("description", internsWhoLikedMe.get(position).getDescription());

                startActivity(i);
            }
        });
    }

    private void getEducation(String educationId) {
        educationController.getEducationById(educationId, new HttpResponse<Education>() {
            @Override
            public void onSuccess(Education data) {
                education = data;
                listOfEducations.add(education);
                ToastUtil.showLongToast(mContext, "Success, fetched education");

                InternLikesAdapter internLikesAdapter = new InternLikesAdapter(LikedByInternActivity.this, internsWhoLikedMe);

                binding.likesView.setAdapter(internLikesAdapter);
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to get Education");
            }
        });
    }


    private void getInternsBelongingToLikes() {
        for (int i = 0; i < listOfLikes.size(); i++) {
            internController.getInternByUserId(listOfLikes.get(i).getFromUserId(), new HttpResponse<Intern>() {
                @Override
                public void onSuccess(Intern data) {
                    internsWhoLikedMe.add(data);
                    getEducation(data.getEducationId());

//                    InternLikesAdapter internLikesAdapter = new InternLikesAdapter(LikedByInternActivity.this, internsWhoLikedMe);
//
//                    binding.likesView.setAdapter(internLikesAdapter);
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
                getInternsBelongingToLikes();
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
