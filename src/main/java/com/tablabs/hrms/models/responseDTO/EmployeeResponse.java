package com.tablabs.hrms.models.responseDTO;

import com.tablabs.hrms.entity.Employees;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private String message;
    private boolean result;
    private Employees data;
}
