package com.example.tinternshipbackend.activities.account_management.intern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.MainActivity;
import com.example.tinternshipbackend.activities.authentication.LoginActivity;
import com.example.tinternshipbackend.activities.like.LikeUser;
import com.example.tinternshipbackend.adapters.EducationArrayAdapter;
import com.example.tinternshipbackend.controllers.education.EducationController;
import com.example.tinternshipbackend.controllers.intern.InternController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.intern.Intern;
import com.example.tinternshipbackend.responses.intern.InternHttp;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

import java.util.ArrayList;

public class RegisterIntern extends AppCompatActivity {
    Spinner educationDropdown;
    Context mContext;
    EducationController educationController;
    InternController internController;
    UserController userController;
    User user;

    ArrayList<Education> allEducations = new ArrayList<Education>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_intern);

        this.educationController = new EducationController(this);
        this.internController = new InternController(this);
        this.userController = new UserController(this);
        this.mContext = this;

        getMe();
        setupDrownDown();
        setupListeners();
    }

    private void setupListeners() {
        Button setupButton = findViewById(R.id.createInternProfileBtn);
        setupButton.setOnClickListener(v -> saveIntern());
    }

    private void setupDrownDown() {
        this.educationDropdown = findViewById(R.id.educationDropdown);
        //get the spinner from the xml.
        educationController.getAllEducations(new HttpResponse<ArrayList<Education>>() {
            @Override
            public void onSuccess(ArrayList<Education> data) {
                allEducations.addAll(data);
                EducationArrayAdapter adapter = new EducationArrayAdapter(getApplicationContext(), allEducations);

                educationDropdown.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                Log.d("EDUCATIONS", error);
                ToastUtil.showLongToast(mContext, "Failed to fetch educations");
            }
        });
    }

    private void getMe() {
        userController.getMe(new HttpResponse<User>() {
            @Override
            public void onSuccess(User data) {
                user = data;
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
    }

    private void saveIntern() {
        String name = ((EditText) findViewById(R.id.internName)).getText().toString();
        int age = Integer.parseInt(((EditText) findViewById(R.id.internAge)).getText().toString());
        String aboutMySelf = ((EditText) findViewById(R.id.aboutMySelf)).getText().toString();
        String internPhoneNumber = ((EditText) findViewById(R.id.internPhoneNumber)).getText().toString();
        Education selectedEducation = allEducations.get(educationDropdown.getSelectedItemPosition());

        Intern intern = new Intern(user.getId(), selectedEducation.getId(), name, age, aboutMySelf, internPhoneNumber);
        this.internController.saveIntern(intern, new HttpResponse<InternHttp>() {
            @Override
            public void onSuccess(InternHttp data) {
                Intent mainActivity = new Intent(mContext, LikeUser.class);
                startActivity(mainActivity);
                ToastUtil.showLongToast(mContext, "Intern information saved!");
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to save as an intern");
            }
        });


    }
}