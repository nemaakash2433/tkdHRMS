
package com.tablabs.hrms.entity;


import javax.persistence.*;

/**
 * A PayRollSalary.
 */
@Entity
@Table(name = "pay_roll_salary")
public class PayRollSalary{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "value_date")
    private String valueDate;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_name")
    private String employeeName;

	public PayRollSalary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayRollSalary(Long id, Double amount, String valueDate, Long employeeId, String employeeName) {
		super();
		this.id = id;
		this.amount = amount;
		this.valueDate = valueDate;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}
