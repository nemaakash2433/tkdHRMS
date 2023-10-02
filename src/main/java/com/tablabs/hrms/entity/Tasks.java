package com.tablabs.hrms.entity;

import com.tablabs.hrms.enums.PriorityType;
import com.tablabs.hrms.enums.TasksStatsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "tasks")
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String taskId;
    private String taskName;
    private String description;
    private String assignee;
    private Date deadline;
    TasksStatsType tasksStatsType;
    PriorityType priority;

    private String document="nothing";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public TasksStatsType getTasksStatsType() {
        return tasksStatsType;
    }

    public void setTasksStatsType(TasksStatsType tasksStatsType) {
        this.tasksStatsType = tasksStatsType;
    }

    public PriorityType getPriority() {
        return priority;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Tasks() {
    }

    public Tasks(Long id, String taskName, String description, String assignee, Date deadline, TasksStatsType tasksStatsType, PriorityType priority, String document) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.assignee = assignee;
        this.deadline = deadline;
        this.tasksStatsType = tasksStatsType;
        this.priority = priority;
        this.document = document;
    }
}
