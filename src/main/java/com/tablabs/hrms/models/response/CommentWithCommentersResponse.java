package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Employees;

import java.time.LocalDateTime;
import java.util.Date;

public class CommentWithCommentersResponse {
    private Long id;
    private Employees commentor;
    private String comment;
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employees getCommentor() {
        return commentor;
    }

    public void setCommentor(Employees commentor) {
        this.commentor = commentor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public CommentWithCommentersResponse() {
    }

    public CommentWithCommentersResponse(Long id, Employees commentor, String comment, Date time) {
        this.id = id;
        this.commentor = commentor;
        this.comment = comment;
        this.time = time;
    }
}
