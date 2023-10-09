package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentOfTheMonthDetailsResponse {
    private Integer noOfAwards;
    private Department department;
    private Integer noOfEmployees;
    private List<GetListOfRewardWithEmployeeDetails> rewardsWithEmployeeDetails;
}
