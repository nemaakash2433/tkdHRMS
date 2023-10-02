package com.tablabs.hrms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tablabs.hrms.enums.PayRequestStatus;

@Entity
@Table(name="pay_requests")
public class PayRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "gross_salary")
	private Double grossSalary;

	@Column(name = "deduction")
	private Double deduction;

	@Column(name = "net_salary")
	private Double netSalary;

	@Column(name = "benefits")
	private String[] benefits;

	@Column(name = "status")
	private PayRequestStatus status=PayRequestStatus.UnPaid;

	public PayRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayRequest(Long id, String employeeId, Double grossSalary, Double deduction, Double netSalary,
					  String[] benefits, PayRequestStatus status) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.grossSalary = grossSalary;
		this.deduction = deduction;
		this.netSalary = netSalary;
		this.benefits = benefits;
		this.status = status;
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

	public Double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public Double getDeduction() {
		return deduction;
	}

	public void setDeduction(Double deduction) {
		this.deduction = deduction;
	}

	public Double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(Double netSalary) {
		this.netSalary = netSalary;
	}

	public String[] getBenefits() {
		return benefits;
	}

	public void setBenefits(String[] benefits) {
		this.benefits = benefits;
	}

	public PayRequestStatus getStatus() {
		return status;
	}

	public void setStatus(PayRequestStatus status) {
		this.status = status;
	}

}
