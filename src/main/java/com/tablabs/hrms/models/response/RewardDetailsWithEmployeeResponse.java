package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardDetailsWithEmployeeResponse {
    private Integer noOfAwards;
    private Department department;
    private Integer noOfEmployees;
    private List<GetListOfRewardWithEmployeeDetails> rewardsWithEmployeeDetails;
}
