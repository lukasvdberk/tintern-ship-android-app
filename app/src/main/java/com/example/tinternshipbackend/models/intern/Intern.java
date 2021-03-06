package com.example.tinternshipbackend.models.intern;

import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.services.httpBackendCommunicator.HttpClient;

public class Intern {
    String id;
    String userId;
    String educationId;
    String avatarUrl;
    String name;
    int age;
    String description;
    String phoneNumber;
    public Intern(String userId, String educationId, String name, int age, String description, String phoneNumber) {
        this.userId = userId;
        this.educationId = educationId;
        this.name = name;
        this.age = age;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.educationId = educationId;
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

    public int getAge() {
        return age;
    }

    public String getAgeAsAString() {
        return Integer.toString(age);
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId() {
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
