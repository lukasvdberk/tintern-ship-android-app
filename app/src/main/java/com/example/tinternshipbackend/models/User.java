package com.example.tinternshipbackend.models;

public class User {
    String id;
    String email;
    String password;

    boolean isCompany;
    boolean isIntern;

    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String id, String email, String password, boolean isCompany, boolean isIntern) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isCompany = isCompany;
        this.isIntern = isIntern;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }

    public boolean isIntern() {
        return isIntern;
    }

    public void setIntern(boolean intern) {
        isIntern = intern;
    }
}
