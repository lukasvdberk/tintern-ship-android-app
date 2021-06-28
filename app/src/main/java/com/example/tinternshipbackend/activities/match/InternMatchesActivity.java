package com.example.tinternshipbackend.activities.match;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.adapters.InternMatchesAdapter;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.intern.InternController;
import com.example.tinternshipbackend.controllers.matchController.MatchController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.databinding.ActivityMatchesBinding;
import com.example.tinternshipbackend.models.Match;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class InternMatchesActivity extends AppCompatActivity {

    ActivityMatchesBinding binding;

    private LikeController likeController;
    private MatchController matchController;
    private UserController userController;
    private CompanyController companyController;
    private InternController internController;
    private Context mContext;
    private User user;
    private List<Match> listOfMatches = new ArrayList<>();
    private ArrayList<Company> companiesWhoMatchedMe;

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

        companiesWhoMatchedMe = new ArrayList<>();
        InternMatchesAdapter internMatchesAdapter = new InternMatchesAdapter(InternMatchesActivity.this, companiesWhoMatchedMe);

        binding.matchesView.setAdapter(internMatchesAdapter);
        binding.matchesView.setClickable(true);
        binding.matchesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(InternMatchesActivity.this, MInternActivity.class);
                i.putExtra("name", companiesWhoMatchedMe.get(position).getName());
                i.putExtra("phoneNumber", companiesWhoMatchedMe.get(position).getPhoneNumber());
                i.putExtra("description", companiesWhoMatchedMe.get(position).getDescription());
                startActivity(i);
            }
        });

        getMe();
    }

    private void getCompaniesBelongingToMatches() {
        for (int i = 0; i < listOfMatches.size(); i++) {
            if(listOfMatches.get(i).getFirstUserId() != user.getId()) {
                companyController.getCompanyByUserId(listOfMatches.get(i).getFirstUserId(), new HttpResponse<Company>() {
                    @Override
                    public void onSuccess(Company data) {
                        companiesWhoMatchedMe.add(data);

                        InternMatchesAdapter internMatchesAdapter = new InternMatchesAdapter(InternMatchesActivity.this, companiesWhoMatchedMe);

                        binding.matchesView.setAdapter(internMatchesAdapter);
                    }

                    @Override
                    public void onError(String error) {
                        System.out.println("HEROOOOO");
                    }
                });
            } else {
                companyController.getCompanyByUserId(listOfMatches.get(i).getFirstUserId(), new HttpResponse<Company>() {
                    @Override
                    public void onSuccess(Company data) {
                        companiesWhoMatchedMe.add(data);

                        InternMatchesAdapter internMatchesAdapter = new InternMatchesAdapter(InternMatchesActivity.this, companiesWhoMatchedMe);

                        binding.matchesView.setAdapter(internMatchesAdapter);
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
                getCompaniesBelongingToMatches();
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
