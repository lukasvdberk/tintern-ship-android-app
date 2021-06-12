package com.example.tinternshipbackend.controllers.firebase;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.tinternshipbackend.responses.firebase.FirebaseDeviceToken;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirebaseNotificationController {
    Context context;

    public FirebaseNotificationController(Context context) {
        this.context = context;
    }

    /**
     * Saves the current user his firebase token to the backend.
     * This will be used for push notifications
     * @param onResponse
     */
    public void saveNotificationToken(HttpResponse<Boolean> onResponse) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            onResponse.onError("failed to register firebase token");
                        } else {
                            // Get new FCM registration token
                            String token = task.getResult();

                            FirebaseDeviceToken firebaseDeviceToken = new FirebaseDeviceToken(token);
                            saveTokenToBackend(firebaseDeviceToken, new HttpResponse<FirebaseDeviceToken>() {
                                @Override
                                public void onSuccess(FirebaseDeviceToken data) {
                                    onResponse.onSuccess(true);
                                }

                                @Override
                                public void onError(String error) {
                                    onResponse.onError("failed to register firebase token");
                                }
                            });
                        }
                    }
                });
    }

    private void saveTokenToBackend(FirebaseDeviceToken token, HttpResponse<FirebaseDeviceToken> onResponse) {
        new HttpClient<FirebaseDeviceToken>(context).post("firebase/user", token, onResponse, FirebaseDeviceToken.class);
    }
}
