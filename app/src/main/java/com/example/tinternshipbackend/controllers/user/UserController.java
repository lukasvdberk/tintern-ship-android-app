package com.example.tinternshipbackend.controllers.user;

import android.content.Context;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.responses.like.LikeHttp;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;

import net.gotev.uploadservice.MultipartUploadRequest;

public class UserController {
    Context context;

    public UserController(Context context) {
        this.context = context;
    }

    /**
     * Uploads image to set as user avatar. Will save the avatar to the user who makes the request
     * @return Whether the file was uploaded or not
     */
    public boolean addImageToProfile(String filePath) {
        try {
            String upload = new MultipartUploadRequest(this.context, null, HttpClient.BASE_URL + "users/avatar")
                    .setMethod("POST")
                    .addHeader("Authorization", "Bearer " +  new AuthController(context).getJWTKey())
                    .addFileToUpload(filePath, "avatar")
                    .startUpload();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
    public void getMe(HttpResponse<User> onResponse) {
        new HttpClient<User>(context).get("users/me", onResponse, User.class);
    }
}
