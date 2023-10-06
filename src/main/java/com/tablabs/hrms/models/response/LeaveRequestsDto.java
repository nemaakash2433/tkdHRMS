package com.tablabs.hrms.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestsDto {
	private Long id;

	private String firstName;
	private String lastName;

	private String empImage;

	private String LeaveType;

	private Date fromDate;

	private Date toDate;

	private String reason;

	private String status;
}
