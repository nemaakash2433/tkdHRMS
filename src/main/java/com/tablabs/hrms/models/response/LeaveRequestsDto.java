package com.tablabs.hrms.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestsDto {
	private Long id;

	private String employeeName;

	private String empImage;

	private String LeaveType;

	private LocalDate fromDate;

	private LocalDate toDate;

	private String reason;

	private String status;
}
