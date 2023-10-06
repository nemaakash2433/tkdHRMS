package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsResponse {

    private String employeeId;

    private String firstname;

    private String lastname;

    private String email;

    private String image;

    private String designation;

    private String contact;

    private List<Reward> reward;

    private Department departmentDetails;
}
