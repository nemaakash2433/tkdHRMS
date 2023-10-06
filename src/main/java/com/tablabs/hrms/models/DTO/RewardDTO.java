package com.tablabs.hrms.models.DTO;

import javax.persistence.Column;


public class RewardDTO {
    private String award;
    private String employeeId;
    private boolean isEmployeeOfTheMonth = false;

    public RewardDTO() {
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isEmployeeOfTheMonth() {
        return isEmployeeOfTheMonth;
    }

    public void setEmployeeOfTheMonth(boolean employeeOfTheMonth) {
        isEmployeeOfTheMonth = employeeOfTheMonth;
    }


    public RewardDTO(String award, String employeeId, boolean isEmployeeOfTheMonth) {
        this.award = award;
        this.employeeId = employeeId;
        this.isEmployeeOfTheMonth = isEmployeeOfTheMonth;
    }
}
