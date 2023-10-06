package com.tablabs.hrms.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * A Leaves.
 */
@Entity
@Table(name = "leaves")
public class Leaves {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "date_applied")
	private Date dateApplied;

	@Column(name = "reason")
	private String reason;

	@Column(name = "status")
	private String status;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "number_of_days")
	private Long numberOfDays;

	@Column(name = "leave_type")
	private String leaveType;

	public Leaves() {
		super();
	}

	public Leaves(Long id, String employeeId, Date dateApplied, String reason, String status, Date startDate,
				  Date endDate, Long numberOfDays, String leaveType) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.dateApplied = dateApplied;
		this.reason = reason;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberOfDays = numberOfDays;
		this.leaveType = leaveType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(Long numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

}
