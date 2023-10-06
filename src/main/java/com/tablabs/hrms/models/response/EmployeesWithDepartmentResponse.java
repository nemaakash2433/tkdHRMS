package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class EmployeesWithDepartmentResponse {
    private Employees employeeDetails;
    private Department departmentDetails;

    public Employees getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(Employees employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public EmployeesWithDepartmentResponse() {
    }

    public Department getDepartmentDetails() {
        return departmentDetails;
    }

    public void setDepartmentDetails(Department departmentDetails) {
        this.departmentDetails = departmentDetails;
    }

    public EmployeesWithDepartmentResponse(Employees employeeDetails, Department departmentDetails) {
        this.employeeDetails = employeeDetails;
        this.departmentDetails = departmentDetails;
    }
}
