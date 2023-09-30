package com.tablabs.hrms.models.DTO;


import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String employeeId;
    private String comment;
    private LocalDateTime time;


    public CommentDTO(Long id, String employeeId, String comment, LocalDateTime time) {
        this.id = id;
        this.employeeId = employeeId;
        this.comment = comment;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
