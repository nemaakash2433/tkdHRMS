package com.tablabs.hrms.entity;


import javax.persistence.*;

/**
 * A Department.
 */

@Entity
@Table(name = "department")
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "emp_id")
    private String employeeId;

    @Column(name = "contact")
    private String contact;

    public Department() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Department(Long id, String name, String head, String contact) {
        super();
        this.id = id;
        this.name = name;
        this.employeeId = head;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return employeeId;
    }

    public void setHead(String head) {
        this.employeeId = head;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }



}
