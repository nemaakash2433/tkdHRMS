
package com.tablabs.hrms.entity;


import javax.persistence.*;

/**
 * A Leaveapproval.
 */
@Entity
@Table(name = "leaveapproval")
public class Leaveapproval{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "manager_id")
    private Long managerId;
  
	@Column(name = "leaves_id")
    private Long leavesId;

	public Leaveapproval() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Leaveapproval(Long id, Long managerId, Long leavesId) {
		super();
		this.id = id;
		this.managerId = managerId;
		this.leavesId = leavesId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getLeavesId() {
		return leavesId;
	}

	public void setLeavesId(Long leavesId) {
		this.leavesId = leavesId;
	}

 
}
