package com.tablabs.hrms.entity;

import com.tablabs.hrms.enums.PriorityType;
import com.tablabs.hrms.enums.TasksStatsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskId;
    private String taskName;
    private String description;
    private Long assignee;
    private LocalDate deadline;
    TasksStatsType tasksStatsType;
    PriorityType priority;
}
