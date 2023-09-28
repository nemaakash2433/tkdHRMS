package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Employees;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetEmployeesByDepartmentId {
    private String departmentName;
    private String departmentHead;
    private String departmentContact;
    private List<Employees> employeesList;
}
