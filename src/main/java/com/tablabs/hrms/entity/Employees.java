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
import java.util.Date;
import java.util.Set;

/**
 * A Employees.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employees {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employeeid", unique = true)
    private String employeeId;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "image")
    private String image;

    @Column(name = "designation")
    private String designation;

    @Column(name = "contact", unique = true)
    private String contact;

    @Column(name = "employee_location")
    private EmployeeLocation employeeLocation = EmployeeLocation.ONSITE;

    @Column(name = "department_Id")
//    @NotNull(message = "select id")
    private Long departmentId;

    private boolean isActive;


    @OneToOne(cascade = CascadeType.ALL)
    private Boarding boardingDetails;
}
