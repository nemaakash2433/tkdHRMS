package com.tablabs.hrms.models.responseDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hrms.app.domain.Empemploymentinfo} entity.
 */
public class EmpemploymentinfoDTO implements Serializable {

    private Long id;

    private String employmentStatus;

    private String employmentType;

    private String employeeId;

    private LocalDate dateOfJoining;

    private String designation;

    private String department;

    private String grade;

    private String reportingManager;

    private String location;

    private String probationPeriod;

    private LocalDate confirmationDate;

    private String noticePeriod;

    private String attendanceSchedule;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProbationPeriod() {
        return probationPeriod;
    }

    public void setProbationPeriod(String probationPeriod) {
        this.probationPeriod = probationPeriod;
    }

    public LocalDate getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDate confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getAttendanceSchedule() {
        return attendanceSchedule;
    }

    public void setAttendanceSchedule(String attendanceSchedule) {
        this.attendanceSchedule = attendanceSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmpemploymentinfoDTO empemploymentinfoDTO = (EmpemploymentinfoDTO) o;
        if (empemploymentinfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empemploymentinfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmpemploymentinfoDTO{" +
            "id=" + getId() +
            ", employmentStatus='" + getEmploymentStatus() + "'" +
            ", employmentType='" + getEmploymentType() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            ", dateOfJoining='" + getDateOfJoining() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", department='" + getDepartment() + "'" +
            ", grade='" + getGrade() + "'" +
            ", reportingManager='" + getReportingManager() + "'" +
            ", location='" + getLocation() + "'" +
            ", probationPeriod='" + getProbationPeriod() + "'" +
            ", confirmationDate='" + getConfirmationDate() + "'" +
            ", noticePeriod='" + getNoticePeriod() + "'" +
            ", attendanceSchedule='" + getAttendanceSchedule() + "'" +
            "}";
    }
}
