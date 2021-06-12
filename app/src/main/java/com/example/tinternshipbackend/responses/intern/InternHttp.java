package com.example.tinternshipbackend.responses.intern;

import com.example.tinternshipbackend.models.intern.Intern;

// because the API accepts an education id but no object.
public class InternHttp{
    String id;
    String name;
    int age;
    String description;
    String phoneNumber;
    String educationId;

    public InternHttp(Intern intern) {
        this.id = intern.getId();
        this.name = intern.getName();
        this.age = intern.getAge();
        this.description = intern.getDescription();
        this.phoneNumber = intern.getPhoneNumber();
        this.educationId = intern.getEducation().getId();
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

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }
}
