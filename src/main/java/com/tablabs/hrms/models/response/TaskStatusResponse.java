package com.tablabs.hrms.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TaskStatusResponse {
    private int newTasks;
    private int pendingTasks;
    private int completedTasks;
}
