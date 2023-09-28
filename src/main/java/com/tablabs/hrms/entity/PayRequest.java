package com.tablabs.hrms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tablabs.hrms.enums.PayRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
