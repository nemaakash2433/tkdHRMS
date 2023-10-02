package com.tablabs.hrms.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class TaskStatusResponse {
    private int newTasks;
    private int pendingTasks;
    private int completedTasks;

    public TaskStatusResponse() {
    }

    public int getNewTasks() {
        return newTasks;
    }

    public void setNewTasks(int newTasks) {
        this.newTasks = newTasks;
    }

    public int getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(int pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public TaskStatusResponse(int newTasks, int pendingTasks, int completedTasks) {
        this.newTasks = newTasks;
        this.pendingTasks = pendingTasks;
        this.completedTasks = completedTasks;
    }
}
