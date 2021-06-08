package com.example.tinternshipbackend.models.company;

import com.example.tinternshipbackend.models.Education;

public class CompanyProject {
    String description;
    Education education;
    Company company;

    public CompanyProject(String description, Education education, Company company) {
        this.description = description;
        this.education = education;
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
