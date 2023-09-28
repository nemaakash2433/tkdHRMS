package com.tablabs.hrms.models.response;

import com.tablabs.hrms.entity.Tasks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TasksResponse {
    private boolean result;
    private String message;
    private Tasks data;
}
