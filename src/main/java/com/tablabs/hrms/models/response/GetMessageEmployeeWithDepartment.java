package com.tablabs.hrms.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMessageEmployeeWithDepartment {
    private boolean result;
    private String message;
    private GetEmployeesWithDepartmentResponse employeeDetails;
}
