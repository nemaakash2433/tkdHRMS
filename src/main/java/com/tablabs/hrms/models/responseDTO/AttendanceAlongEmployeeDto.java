package com.tablabs.hrms.models.responseDTO;

public class AttendanceAlongEmployeeDto {

	private String employeeName;

	private String empImage;

	private String designation;

	private String workingHours;

	private String checkIn;

	private String checkout;

	private String markedAs;

	public AttendanceAlongEmployeeDto() {
		super();
	}

	public AttendanceAlongEmployeeDto(String employeeName, String empImage, String designation, String workingHours,
			String checkIn, String checkout, String markedAs) {
		super();
		this.employeeName = employeeName;
		this.empImage = empImage;
		this.designation = designation;
		this.workingHours = workingHours;
		this.checkIn = checkIn;
		this.checkout = checkout;
		this.markedAs = markedAs;
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public String getMarkedAs() {
		return markedAs;
	}

	public void setMarkedAs(String markedAs) {
		this.markedAs = markedAs;
	}

}
