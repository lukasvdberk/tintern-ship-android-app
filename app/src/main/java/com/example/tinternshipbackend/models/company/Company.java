package com.example.tinternshipbackend.models.company;

import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;

import java.io.Serializable;

public class Company implements Serializable {
    String id;
    String userId;
    String avatarUrl;
    String name;
    String description;
    String phoneNumber;

    public Company(String userId, String name, String description, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        if(avatarUrl == null) {
            return "https://file.coffee/u/1woImEMROR3EAC.png";
        }

        if (!avatarUrl.startsWith("http")) {
            return HttpClient.BASE_URL + avatarUrl;
        }
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
