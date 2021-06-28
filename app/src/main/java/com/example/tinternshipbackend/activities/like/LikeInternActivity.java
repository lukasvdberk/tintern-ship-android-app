package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.match.CompanyMatchesActivity;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.intern.InternController;
import com.example.tinternshipbackend.controllers.matchController.MatchController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.models.Like;
import com.example.tinternshipbackend.models.Match;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.models.company.CompanyProject;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.DownloadImageAndSet;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LikeInternActivity extends AppCompatActivity {
    private LikeController likeController;
    private CompanyController companyController;
    private UserController userController;
    private AuthController authController;
    private InternController internController;
    private MatchController matchController;
    private User user;
    private Company company;
    private Boolean matchAvailable;

    private int index = 0;
    private Context mContext;
    private List<String> listOfAvailableEducationIds = new ArrayList<>();
    private List<Intern> listOfAvailableInterns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        this.likeController = new LikeController(this);
        this.companyController = new CompanyController(this);
        this.authController = new AuthController(this);
        this.userController = new UserController(this);
        this.internController = new InternController(this);
        this.matchController = new MatchController(this);
        this.mContext = this;

        getMe();

    }

    private void getMe() {
        userController.getMe(new HttpResponse<User>() {
            @Override
            public void onSuccess(User data) {
                user = data;
                getCompany();
                ToastUtil.showLongToast(mContext, "Success, fetched me");
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to get me");
            }
        });
    }

    private void getCompany() {
        this.companyController.getCompany(user.getId(), new HttpResponse<Company>() {
            @Override
            public void onSuccess(Company data) {
                company = data;
                ToastUtil.showLongToast(mContext, "Success, fetched company");
                getProjectsBelongingToCompany();
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to fetch company");
            }
        });
    }

    private void getProjectsBelongingToCompany() {
        this.companyController.getMyCompanyProjects(new HttpResponse<ArrayList<CompanyProject>>() {
            @Override
            public void onSuccess(ArrayList<CompanyProject> data) {
                for(int i = 0; i < data.size(); i++) {
                    boolean educationAlreadyInListOfOfEducations = listOfAvailableEducationIds.contains(data.get(i).getEducationId());
                    if (!educationAlreadyInListOfOfEducations) {
                        listOfAvailableEducationIds.add(data.get(i).getEducationId());
                    }
                }
                ToastUtil.showLongToast(mContext, "Success, fetched projects belonging to this company");
                getAllFittingInterns();
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to fetch projects belonging to this company");

            }
        });
    }

    private void getAllFittingInterns() {
        for(int i = 0; i < listOfAvailableEducationIds.size(); i++) {
            this.internController.getAllFittingInterns(listOfAvailableEducationIds.get(i) ,new HttpResponse<ArrayList<Intern>>() {
                @Override
                public void onSuccess(ArrayList<Intern> data) {
                    listOfAvailableInterns.addAll(data);
                    ToastUtil.showLongToast(mContext, "Success, fetched list of available interns");
                    setupListeners();
                }

                @Override
                public void onError(String error) {
                    ToastUtil.showLongToast(mContext, "Failed to fetch list of available interns");
                }
            });
        }
    }

    private void shuffleListOfInters() {
        Collections.shuffle(listOfAvailableInterns);

        System.out.println(listOfAvailableInterns.get(index).getName());

        setupListeners();
    }

    private void setupListeners() {
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(listOfAvailableInterns.get(index).getName());

        TextView age = (TextView) findViewById(R.id.age);
        age.setText(Integer.toString(listOfAvailableInterns.get(index).getAge()));

        ImageView profileImage = (ImageView) findViewById(R.id.profile_image);
        new DownloadImageAndSet(profileImage, this).execute(listOfAvailableInterns.get(index).getAvatarUrl());

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(listOfAvailableInterns.get(index).getDescription());

        Button likeButton = (Button) findViewById(R.id.likeBtn);
        Button dislikeButton = (Button) findViewById(R.id.dislikeBtn);
        Button likesButton = (Button) findViewById(R.id.likesBtn);
        Button matchesButton = (Button) findViewById(R.id.matchesBtn);

        likeButton.setOnClickListener(v -> like());
        dislikeButton.setOnClickListener(v -> dislike());
        likesButton.setOnClickListener(v -> likes());
        matchesButton.setOnClickListener(v -> matches());
    }

    private void checkIfMatchAvailable() {
        this.matchController.checkIfMatchIsAvailable(new HttpResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                matchAvailable = data;
                ToastUtil.showLongToast(mContext, "Success, checked if match is available");

                if(matchAvailable == true) {
                    saveMatch();
                } else {
                    setupListeners();
                }
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to check if match is available");
            }
        });
    }

    private void saveMatch() {
        Match match = new Match(listOfAvailableInterns.get(index).getUserId(), company.getUserId());
        this.matchController.saveMatch(match, new HttpResponse<Match>() {
            @Override
            public void onSuccess(Match data) {
                ToastUtil.showLongToast(mContext, "Success, saved match");
                setupListeners();

            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to save match");
            }
        });

    }

    private void like() {
        if(index + 1 <= listOfAvailableInterns.size()) {
            saveLike();
            index += 1;
        }
    }

    private void dislike() {
        if(index + 1 < listOfAvailableInterns.size()) {
            index += 1;
            setupListeners();
        }
    }

    private void matches() {
        Intent intent = new Intent(mContext, CompanyMatchesActivity.class);
        startActivity(intent);
    }

    private void likes() {
        Intent intent = new Intent(mContext, LikedByInternActivity.class);
        startActivity(intent);
    }

    private void saveLike() {
        Like like = new Like(company.getUserId(), listOfAvailableInterns.get(index).getUserId(), true);

        likeController.saveLike(like, new HttpResponse<Like>() {
            @Override
            public void onSuccess(Like data) {
                ToastUtil.showLongToast(mContext, "Success, saved like");
                checkIfMatchAvailable();
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to save like");
            }
        });
    }
}
