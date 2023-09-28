package com.tablabs.hrms.models.request;

import com.tablabs.hrms.enums.PayRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayRequestDto {
	
	private String employeeName;
	
	private String employeeImage;
	
	private String Designation;

	private Double grossSalary;

	private Double deduction;

	private Double netSalary;

	private String[] benefits;

	private PayRequestStatus status = PayRequestStatus.UnPaid;
}
