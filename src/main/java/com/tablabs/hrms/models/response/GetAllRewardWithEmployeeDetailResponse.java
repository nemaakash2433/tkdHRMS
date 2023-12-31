package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Employees;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllRewardWithEmployeeDetailResponse {
    private String award;
//    private double rating;
    private Employees employees;
}
