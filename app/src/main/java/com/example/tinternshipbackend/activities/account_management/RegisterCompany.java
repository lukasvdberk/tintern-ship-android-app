package com.example.tinternshipbackend.activities.account_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.activities.MainActivity;
import com.example.tinternshipbackend.controllers.company.CompanyController;
import com.example.tinternshipbackend.controllers.user.UserController;
import com.example.tinternshipbackend.models.Company;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.example.tinternshipbackend.viewUtil.ToastUtil;

public class RegisterCompany extends AppCompatActivity {
    CompanyController companyController;
    UserController userController;

    Context mContext;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int PICK_IMAGE_REQUEST = 9544;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        this.companyController = new CompanyController(this);
        this.userController = new UserController(this);
        this.mContext = this;
        setupListeners();
    }

    private void setupListeners() {
        Button uploadProfileBtn = (Button) findViewById(R.id.uploadPictureBtn);
        Button profileButton = (Button) findViewById(R.id.createProfileBtn);

        uploadProfileBtn.setOnClickListener(v -> {
            selectImage();
        });
        profileButton.setOnClickListener(v -> {
            registerCompany();
        });
    }

    private void registerCompany() {
        // TODO add profile pic
        String companyName = ((EditText) findViewById(R.id.companyName)).getText().toString();
        String aboutTheCompany = ((EditText) findViewById(R.id.aboutTheCompany)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.companyPhoneNumber)).getText().toString();

        Company company = new Company(companyName, aboutTheCompany, phoneNumber);

        this.companyController.saveCompany(company, new HttpResponse<Company>() {
            @Override
            public void onSuccess(Company data) {
                // TODO add redirect
                ToastUtil.showLongToast(mContext, "Company information was saved!");
            }

            @Override
            public void onError(String error) {
                ToastUtil.showLongToast(mContext, "Failed to save company information. Try again later!");
            }
        });
    }

    // TODO refactor to fragment
    private void selectImage() {
        verifyStoragePermissions(RegisterCompany.this);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select profile picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            boolean isUploaded = this.userController.addImageToProfile(getPath(selectedImage));

            if(isUploaded) {
                ToastUtil.showLongToast(mContext, "Successfully uploaded user avatar!");
            } else {
                ToastUtil.showLongToast(mContext, "Failed to upload user avatar");
            }
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

}