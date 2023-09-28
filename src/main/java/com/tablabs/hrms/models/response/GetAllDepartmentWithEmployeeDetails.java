package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Employees;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetAllDepartmentWithEmployeeDetails {
    private String departmentName;
    private Integer noOfEmployees;
    private List<Employees> employeesList;
}
