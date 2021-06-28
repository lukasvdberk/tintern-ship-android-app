package com.example.tinternshipbackend.activities.match;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.adapters.CompanyMatchesAdapter;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.intern.InternController;
import com.example.tinternshipbackend.controllers.matchController.MatchController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.databinding.ActivityMatchesBinding;
import com.example.tinternshipbackend.models.Match;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CompanyMatchesActivity extends AppCompatActivity {

    ActivityMatchesBinding binding;

    private LikeController likeController;
    private MatchController matchController;
    private UserController userController;
    private CompanyController companyController;
    private InternController internController;
    private Context mContext;
    private User user;
    private List<Match> listOfMatches = new ArrayList<>();
    private ArrayList<Intern> internsWhoMatchedMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = this;

        this.likeController = new LikeController(this);
        this.matchController = new MatchController(this);
        this.userController = new UserController( this);
        this.companyController = new CompanyController(this);
        this.internController = new InternController(this);

        binding = ActivityMatchesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        internsWhoMatchedMe = new ArrayList<>();
        CompanyMatchesAdapter companyMatchesAdapter = new CompanyMatchesAdapter(CompanyMatchesActivity.this, internsWhoMatchedMe);

        binding.matchesView.setAdapter(companyMatchesAdapter);
        binding.matchesView.setClickable(true);
        binding.matchesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(CompanyMatchesActivity.this, MCompanyActivity.class);
                i.putExtra("name", internsWhoMatchedMe.get(position).getName());
                i.putExtra("phoneNumber", internsWhoMatchedMe.get(position).getPhoneNumber());
                i.putExtra("description", internsWhoMatchedMe.get(position).getDescription());

                startActivity(i);
            }
        });

        getMe();
    }

    private void getInternsBelongingToMatches() {
        for (int i = 0; i < listOfMatches.size(); i++) {
            if(listOfMatches.get(i).getFirstUserId() != user.getId()) {
                internController.getInternByUserId(listOfMatches.get(i).getFirstUserId(), new HttpResponse<Intern>() {
                    @Override
                    public void onSuccess(Intern data) {
                        internsWhoMatchedMe.add(data);

                        CompanyMatchesAdapter companyMatchesAdapter = new CompanyMatchesAdapter(CompanyMatchesActivity.this, internsWhoMatchedMe);

                        binding.matchesView.setAdapter(companyMatchesAdapter);
                    }

                    @Override
                    public void onError(String error) {
                        System.out.println("HEROOOOO");
                    }
                });
            } else {
                internController.getInternByUserId(listOfMatches.get(i).getSecondUserId(), new HttpResponse<Intern>() {
                    @Override
                    public void onSuccess(Intern data) {
                        internsWhoMatchedMe.add(data);

                        CompanyMatchesAdapter companyMatchesAdapter = new CompanyMatchesAdapter(CompanyMatchesActivity.this, internsWhoMatchedMe);

                        binding.matchesView.setAdapter(companyMatchesAdapter);
                    }

                    @Override
                    public void onError(String error) {
                        System.out.println("HEROOOOO");
                    }
                });
            }
        }
    }

    private void getMatchesFromUser() {
        matchController.getMatches(user.getId(), new HttpResponse<ArrayList<Match>>() {
            @Override
            public void onSuccess(ArrayList<Match> data) {
                listOfMatches.addAll(data);
                ToastUtil.showLongToast(mContext, "Success, fetched all likes from this user");
                getInternsBelongingToMatches();
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
                getMatchesFromUser();
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to get me");
            }
        });
    }
}
