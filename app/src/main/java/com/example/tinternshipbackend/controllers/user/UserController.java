package com.example.tinternshipbackend.controllers.user;

import android.content.Context;
import com.example.tinternshipbackend.controllers.authentication.AuthController;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
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
        // TODO replace with actual request
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
}
