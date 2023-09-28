
package com.tablabs.hrms.entity;

import javax.persistence.*;

/**
 * A LeavesType.
 */
@Entity
@Table(name = "leaves_type")
public class LeavesType{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_leaves")
    private Integer numberOfLeves;

    @Column(name = "active")
    private Boolean active;
    
	public LeavesType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeavesType(Long id, String type, String description, Integer numberOfLeves, Boolean active) {
		super();
		this.id = id;
		this.type = type;
		this.description = description;
		this.numberOfLeves = numberOfLeves;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumberOfLeves() {
		return numberOfLeves;
	}

	public void setNumberOfLeves(Integer numberOfLeves) {
		this.numberOfLeves = numberOfLeves;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
