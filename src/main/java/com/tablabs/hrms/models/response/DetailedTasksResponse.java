package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Comment;
import com.tablabs.hrms.entity.Employees;

import java.time.LocalDateTime;
import java.util.*;

public class DetailedTasksResponse {
    private Long id;
    private String task;
    private Date deadLine;
    private Employees assignee;
    private List<CommentWithCommentersResponse> comments;

    public DetailedTasksResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Employees getAssignee() {
        return assignee;
    }

    public void setAssignee(Employees assignee) {
        this.assignee = assignee;
    }

    public List<CommentWithCommentersResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentWithCommentersResponse> comments) {
        this.comments = comments;
    }

    public DetailedTasksResponse(Long id, String task, Date deadLine, Employees assignee, List<CommentWithCommentersResponse> comments) {
        this.id = id;
        this.task = task;
        this.deadLine = deadLine;
        this.assignee = assignee;
        this.comments = comments;
    }
}
