package com.tablabs.hrms.models.responseDTO;

import com.tablabs.hrms.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetEmployeesWithDepartmentResponse {


    private Long id;
    private String firstName;
    private String lastName;
    private String image;
    private String designation;

    private String contact;
    private Department department;

}
