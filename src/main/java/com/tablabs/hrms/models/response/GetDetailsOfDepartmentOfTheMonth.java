package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class GetDetailsOfDepartmentOfTheMonth {
    private Integer noOfAwards;
    private String employeeId;
    private Long departmentId;

    public Integer getNoOfAwards() {
        return noOfAwards;
    }

    public void setNoOfAwards(Integer noOfAwards) {
        this.noOfAwards = noOfAwards;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public GetDetailsOfDepartmentOfTheMonth() {
    }

    public GetDetailsOfDepartmentOfTheMonth(Integer noOfAwards, String employeeId, Long departmentId) {
        this.noOfAwards = noOfAwards;
        this.employeeId = employeeId;
        this.departmentId = departmentId;
    }
}
