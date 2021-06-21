package com.example.tinternshipbackend.activities.like;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.controllers.Like.LikeController;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.intern.InternController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.models.company.CompanyProject;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.responses.authentication.LoginResponse;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

import org.w3c.dom.Text;

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
    private Company company;

    private int index = 0;
    private Context mContext;
    private List<CompanyProject> listOfProjects = new ArrayList<>();

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


    }
    private void getAllFittingInternshipProjects() {
        internController.getAllFittingProjects(new HttpResponse<ArrayList<CompanyProject>>() {
            @Override
            public void onSuccess(ArrayList<CompanyProject> data) {
                listOfProjects.addAll(data);
                getCompanyBelongingToProject();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void getIntern() {
        System.out.println(user.getId());
        internController.getIntern(user.getId(), new HttpResponse<Intern>() {
            @Override
            public void onSuccess(Intern data) {
                intern = data;
                getAllFittingInternshipProjects();
            }

            @Override
            public void onError(String error) {
                System.out.println("hallo");
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

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(company.getName());

        TextView age = (TextView) findViewById(R.id.age);
        age.setText(listOfProjects.get(index).getEducationId());

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(listOfProjects.get(index).getDescription());

        Button likeButton = (Button) findViewById(R.id.likeBtn);
        Button dislikeButton = (Button) findViewById(R.id.dislikeBtn);

        likeButton.setOnClickListener(v -> like());
        dislikeButton.setOnClickListener(v -> dislike());
    }

    private void getCompanyBelongingToProject() {
        companyController.getCompanyByCompanyId(listOfProjects.get(index).getCompanyId(), new HttpResponse<Company>() {
            @Override
            public void onSuccess(Company data) {
                company = data;
                setupListeners();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void like() {
        if(index + 1 < listOfProjects.size()) {
            index += 1;
            System.out.println(listOfProjects.size());
            System.out.println(index);
            getCompanyBelongingToProject();
        }
    }

    private void dislike() {
        System.out.println("Dislike you girl");
    }
}
