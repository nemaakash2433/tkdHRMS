package com.tablabs.hrms.models.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMessageAllEmployeeWithDepartmentResponse {
    private boolean result;
    private String message;
    private List<GetEmployeesWithDepartmentResponse> data;
}
