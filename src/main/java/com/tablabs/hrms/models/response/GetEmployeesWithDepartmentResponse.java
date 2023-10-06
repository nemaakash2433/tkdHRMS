package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class GetEmployeesWithDepartmentResponse {


    private String employeeId;
    private String firstName;
    private String lastName;
    private String image;
    private String contact;
    private String email;

    private String designation;
    private Department department;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }



    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public GetEmployeesWithDepartmentResponse() {
    }

    public GetEmployeesWithDepartmentResponse(String employeeId, String firstName, String lastName, String image, String contact, String email, Department department) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.contact = contact;
        this.email = email;
        this.department = department;
    }
}
