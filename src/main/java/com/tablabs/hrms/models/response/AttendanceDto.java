package com.tablabs.hrms.models.response;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDto {

	private List<AttendanceAlongEmployeeDto> attendance = new ArrayList<>();

	public AttendanceDto() {
		super();
	}

	public AttendanceDto(List<AttendanceAlongEmployeeDto> attendance) {
		super();
		this.attendance = attendance;
	}

	public List<AttendanceAlongEmployeeDto> getAttendance() {
		return attendance;
	}

	public void setAttendance(List<AttendanceAlongEmployeeDto> attendance) {
		this.attendance = attendance;
	}

}
