package com.tablabs.hrms.models.responseDTO;

import java.time.LocalDate;

public class LeaveRequestsDto {

	private String employeeName;

	private String empImage;

	private String LeaveType;

	private LocalDate fromDate;

	private LocalDate toDate;

	private String reason;

	private String status;

	public LeaveRequestsDto() {
		super();
	}

	public LeaveRequestsDto(String employeeName, String empImage, String leaveType, LocalDate fromDate,
			LocalDate toDate, String reason, String status) {
		super();
		this.employeeName = employeeName;
		this.empImage = empImage;
		LeaveType = leaveType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.reason = reason;
		this.status = status;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmpImage() {
		return empImage;
	}

	public void setEmpImage(String empImage) {
		this.empImage = empImage;
	}

	public String getLeaveType() {
		return LeaveType;
	}

	public void setLeaveType(String leaveType) {
		LeaveType = leaveType;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
