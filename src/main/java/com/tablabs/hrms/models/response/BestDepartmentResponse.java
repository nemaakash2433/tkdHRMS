package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class BestDepartmentResponse {
    private Object noOfAwards;
    private String deparmentName;
    private Department departmentDetails;
    private List<TopPerformanceEmployeeDetailsResponse> rewardDetails;

    public BestDepartmentResponse() {
    }

    public Object getNoOfAwards() {
        return noOfAwards;
    }

    public void setNoOfAwards(Object noOfAwards) {
        this.noOfAwards = noOfAwards;
    }

    public String getDeparmentName() {
        return deparmentName;
    }

    public void setDeparmentName(String deparmentName) {
        this.deparmentName = deparmentName;
    }

    public Department getDepartmentDetails() {
        return departmentDetails;
    }

    public void setDepartmentDetails(Department departmentDetails) {
        this.departmentDetails = departmentDetails;
    }

    public List<TopPerformanceEmployeeDetailsResponse> getRewardDetails() {
        return rewardDetails;
    }

    public void setRewardDetails(List<TopPerformanceEmployeeDetailsResponse> rewardDetails) {
        this.rewardDetails = rewardDetails;
    }

    public BestDepartmentResponse(Object noOfAwards, String deparmentName, Department departmentDetails, List<TopPerformanceEmployeeDetailsResponse> rewardDetails) {
        this.noOfAwards = noOfAwards;
        this.deparmentName = deparmentName;
        this.departmentDetails = departmentDetails;
        this.rewardDetails = rewardDetails;
    }
}
