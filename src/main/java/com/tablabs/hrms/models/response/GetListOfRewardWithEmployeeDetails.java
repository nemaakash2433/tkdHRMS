package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public class GetListOfRewardWithEmployeeDetails {
    private Employees employees;
    private List<Reward> rewardList;

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public List<Reward> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<Reward> rewardList) {
        this.rewardList = rewardList;
    }

    public GetListOfRewardWithEmployeeDetails() {
    }

    public GetListOfRewardWithEmployeeDetails(Employees employees, List<Reward> rewardList) {
        this.employees = employees;
        this.rewardList = rewardList;
    }
}
