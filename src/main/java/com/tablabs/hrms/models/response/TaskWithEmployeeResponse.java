package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.enums.TasksStatsType;

public class TaskWithEmployeeResponse {
    private String taskName;

    private String employeeName;
    private TasksStatsType tasksStatsType;
    private Employees employeeData;

    public TasksStatsType getTasksStatsType() {
        return tasksStatsType;
    }

    public void setTasksStatsType(TasksStatsType tasksStatsType) {
        this.tasksStatsType = tasksStatsType;
    }

    public Employees getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(Employees employeeData) {
        this.employeeData = employeeData;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
