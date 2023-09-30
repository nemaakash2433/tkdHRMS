package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Tasks;
import com.tablabs.hrms.enums.TasksStatsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks,Long> {

//    boolean existsByTaskId(String taskId);
//    Tasks findByTaskId(String taskId);

    List<Tasks> findByTasksStatsType(TasksStatsType tasksStatsType);

    int countByTasksStatsType(TasksStatsType tasksStatsType);
}
