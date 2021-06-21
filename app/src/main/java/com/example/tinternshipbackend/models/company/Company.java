package com.example.tinternshipbackend.models.company;

import java.io.Serializable;

public class Company implements Serializable {
    String id;
    String userId;
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
}
