package com.tablabs.hrms.models.DTO;

import com.tablabs.hrms.enums.EmployeeLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Setter
@Getter
@AllArgsConstructor
public class EmployeeDTO {
    private String employeeId;

    private String firstname;

    private String lastname;

    private String image;
    private String email;

    private String designation;

    private String contact;

    private EmployeeLocation location;
}
