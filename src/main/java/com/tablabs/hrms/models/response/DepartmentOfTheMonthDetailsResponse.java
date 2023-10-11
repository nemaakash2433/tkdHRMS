package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public class DepartmentOfTheMonthDetailsResponse {
    private Integer noOfAwards;
    private Department department;
    private Integer noOfEmployees;
    private List<GetListOfRewardWithEmployeeDetails> rewardsWithEmployeeDetails;

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

    public List<GetListOfRewardWithEmployeeDetails> getRewardsWithEmployeeDetails() {
        return rewardsWithEmployeeDetails;
    }

    public void setRewardsWithEmployeeDetails(List<GetListOfRewardWithEmployeeDetails> rewardsWithEmployeeDetails) {
        this.rewardsWithEmployeeDetails = rewardsWithEmployeeDetails;
    }

    public DepartmentOfTheMonthDetailsResponse() {
    }

    public DepartmentOfTheMonthDetailsResponse(Integer noOfAwards, Department department, Integer noOfEmployees, List<GetListOfRewardWithEmployeeDetails> rewardsWithEmployeeDetails) {
        this.noOfAwards = noOfAwards;
        this.department = department;
        this.noOfEmployees = noOfEmployees;
        this.rewardsWithEmployeeDetails = rewardsWithEmployeeDetails;
    }
}
