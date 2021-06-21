package com.example.tinternshipbackend.models.company;

import com.example.tinternshipbackend.models.Education;

public class CompanyProject {
    String id;
    String description;
    String educationId;
    String companyId;

    public CompanyProject(String description, String educationId, String companyId) {
        this.description = description;
        this.educationId = educationId;
        this.companyId = companyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String  getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompany(String companyId) {
        this.companyId = companyId;
    }
}
