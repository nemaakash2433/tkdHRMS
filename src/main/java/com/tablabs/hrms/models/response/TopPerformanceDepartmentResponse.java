package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopPerformanceDepartmentResponse {
    private Integer noOfAwards;
    private Department department;
    private Integer noOfEmployees;
    private GetListOfRewardWithEmployeeDetails rewardsWithEmployeeDetails;
}
