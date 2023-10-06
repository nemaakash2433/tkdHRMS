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

    private String head;

    @Column(name = "emp_id")
    private String employeeId;

    @Column(name = "contact")
    private String contact;

    private boolean isActive;

    public Department() {
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
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Department(Long id, String name, String head, String employeeId, String contact, boolean isActive) {
        this.id = id;
        this.name = name;
        this.head = head;
        this.employeeId = employeeId;
        this.contact = contact;
        this.isActive = isActive;
    }
}
