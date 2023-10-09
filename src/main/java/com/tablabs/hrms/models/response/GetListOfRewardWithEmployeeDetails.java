package com.tablabs.hrms.models.response;

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
public class GetListOfRewardWithEmployeeDetails {
    private Employees employees;
    private List<Reward> rewardList;
}
