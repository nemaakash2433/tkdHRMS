package com.tablabs.hrms.entity;


import com.tablabs.hrms.enums.EmployeeLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * A Employees.
 */

@Entity
@Table(name = "employees")
public class Employees {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employeeid",unique = true)
    private String employeeId;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name="image")
    private String image;

    @Column(name = "designation")
    private String designation;

    @Column(name = "contact")
    private String contact;

    @Column(name = "employee_location")
    private EmployeeLocation employeeLocation=EmployeeLocation.ONSITE;

	@Column(name = "department_Id")
//    @NotNull(message = "select id")
	private Long departmentId;

    @OneToOne(cascade = CascadeType.ALL)
    private Boarding boardingDeatails;

    public Employees() {
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public EmployeeLocation getEmployeeLocation() {
        return employeeLocation;
    }

    public void setEmployeeLocation(EmployeeLocation employeeLocation) {
        this.employeeLocation = employeeLocation;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Boarding getBoardingDeatails() {
        return boardingDeatails;
    }

    public void setBoardingDeatails(Boarding boardingDeatails) {
        this.boardingDeatails = boardingDeatails;
    }

    public Employees(Long id, String employeeId, String firstname, String lastname, String image, String designation, String contact, EmployeeLocation employeeLocation, Long departmentId, Boarding boardingDeatails) {
        this.id = id;
        this.employeeId = employeeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
        this.designation = designation;
        this.contact = contact;
        this.employeeLocation = employeeLocation;
        this.departmentId = departmentId;
        this.boardingDeatails = boardingDeatails;
    }
}
