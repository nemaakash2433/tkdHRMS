package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public class TopPerformanceDepartmentResponse {
    private Integer noOfAwards;
    private Department department;
    private Integer noOfEmployees;
    private GetListOfRewardWithEmployeeDetails rewardsWithEmployeeDetails;

    public TopPerformanceDepartmentResponse() {
    }

    public Integer getNoOfAwards() {
        return noOfAwards;
    }

    public void setNoOfAwards(Integer noOfAwards) {
        this.noOfAwards = noOfAwards;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getNoOfEmployees() {
        return noOfEmployees;
    }

    public void setNoOfEmployees(Integer noOfEmployees) {
        this.noOfEmployees = noOfEmployees;
    }

    public GetListOfRewardWithEmployeeDetails getRewardsWithEmployeeDetails() {
        return rewardsWithEmployeeDetails;
    }

    public void setRewardsWithEmployeeDetails(GetListOfRewardWithEmployeeDetails rewardsWithEmployeeDetails) {
        this.rewardsWithEmployeeDetails = rewardsWithEmployeeDetails;
    }

    public TopPerformanceDepartmentResponse(Integer noOfAwards, Department department, Integer noOfEmployees, GetListOfRewardWithEmployeeDetails rewardsWithEmployeeDetails) {
        this.noOfAwards = noOfAwards;
        this.department = department;
        this.noOfEmployees = noOfEmployees;
        this.rewardsWithEmployeeDetails = rewardsWithEmployeeDetails;
    }
}
