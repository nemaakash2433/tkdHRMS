package com.tablabs.hrms.models.response;


import com.tablabs.hrms.entity.Department;
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
public class AwardDetailsWithEmployeeResponse {
    private int awards;
    private String employeeId;

    private String firstname;

    private String lastname;

    private String email;

    private String image;

    private String designation;

    private String contact;
    private List<Reward> rewardList;

    private Department department;

}
