package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Employees;

public class GetAllRewardWithEmployeeDetailResponse {
    private String award;
    private String rating;
    private Employees employees;

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public GetAllRewardWithEmployeeDetailResponse() {
    }

    public GetAllRewardWithEmployeeDetailResponse(String award, String rating, Employees employees) {
        this.award = award;
        this.rating = rating;
        this.employees = employees;
    }
}
