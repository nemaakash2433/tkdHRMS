package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TopPerformanceEmployeeDetailsResponse {
    private Employees employeeDetails;
    private Reward awardDetails;

}
