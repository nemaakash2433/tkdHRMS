package com.tablabs.hrms.errors;

import java.util.List;

public class EmployeeDoesNotExistException extends RuntimeException{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	  private String details;
	  private List<Long> employeeIds;
	  
	  protected EmployeeDoesNotExistException() {}

	public EmployeeDoesNotExistException(String message, String details, List<Long> employeeIds) {
		super();
		this.message = message;
		this.details = details;
		this.employeeIds = employeeIds;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<Long> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(List<Long> employeeIds) {
		this.employeeIds = employeeIds;
	}

	
	
}
