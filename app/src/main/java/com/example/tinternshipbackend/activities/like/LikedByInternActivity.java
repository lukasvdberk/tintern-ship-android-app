package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.adapters.CompanyLikesAdapter;
import com.example.tinternshipbackend.adapters.InternLikesAdapter;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.intern.InternController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.databinding.ActivityLikesBinding;
import com.example.tinternshipbackend.models.Like;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.company.Company;
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

    private Context mContext;
    private User user;

    private List<Like> listOfLikes = new ArrayList<>();
    private ArrayList<Intern> internsWhoLikedMe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = this;

        this.likeController = new LikeController(this);
        this.userController = new UserController( this);
        this.internController = new InternController(this);

        binding = ActivityLikesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intern intern = new Intern("60d0ecc5277c8d40106887f0", "60b1fec430adfe2426a4b277", "Stagair 1", 22, "Dit is Stagaire 1", "0638576839");
        Intern intern1 = new Intern("60d0ecc5277c8d40106887f0", "60b1fec430adfe2426a4b277", "Stagair 2", 22, "Dit is Stagaire 2", "0638576839");
        Intern intern2 = new Intern("60d0ecc5277c8d40106887f0", "60b1fec430adfe2426a4b277", "Stagair 3", 22, "Dit is Stagaire 3", "0638576839");

        internsWhoLikedMe.add(intern);
        internsWhoLikedMe.add(intern1);
        internsWhoLikedMe.add(intern2);

        getMe();

        InternLikesAdapter internLikesAdapter = new InternLikesAdapter(LikedByInternActivity.this, internsWhoLikedMe);

        binding.likesView.setAdapter(internLikesAdapter);
        binding.likesView.setClickable(true);
        binding.likesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(LikedByInternActivity.this, InternActivity.class);
                i.putExtra("name", internsWhoLikedMe.get(position).getName());
                i.putExtra("age", internsWhoLikedMe.get(position).getAge());
                i.putExtra("educationId", internsWhoLikedMe.get(position).getEducationId());
                i.putExtra("phoneNumber", internsWhoLikedMe.get(position).getPhoneNumber());
                i.putExtra("description", internsWhoLikedMe.get(position).getDescription());

                startActivity(i);
            }
        });
    }

//    private void getInternsBelongingToLikes() {
//        internController.getInternsWhoLikeMe(listOfLikes, new HttpResponse<Intern>() {
//            @Override
//            public void onSuccess(Intern data) {
//                internsWhoLikedMe.add(data);
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        });
//    }

    private void getLikesFromUser() {
        likeController.getLikes(user.getId(), new HttpResponse<ArrayList<Like>>() {
            @Override
            public void onSuccess(ArrayList<Like> data) {
                listOfLikes.addAll(data);
                ToastUtil.showLongToast(mContext, "Success, fetched all likes from this user");
//                getCompaniesBelongingToLikes();
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
