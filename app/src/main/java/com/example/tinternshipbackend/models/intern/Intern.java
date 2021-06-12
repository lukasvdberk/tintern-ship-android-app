package com.example.tinternshipbackend.models.intern;

import com.example.tinternshipbackend.models.Education;

public class Intern {
    String id;
    String name;
    int age;
    String description;
    String phoneNumber;
    Education education;
    public Intern(String name, int age, String description, String phoneNumber, Education education) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.education = education;
    }

    public Intern(String id, String name, int age, String description, String phoneNumber, Education education) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.education = education;
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

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }
}
