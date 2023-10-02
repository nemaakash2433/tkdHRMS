package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Tasks;
import com.tablabs.hrms.enums.TasksStatsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks,Long> {

//    boolean existsByTaskId(String taskId);
//    Tasks findByTaskId(String taskId);

    List<Tasks> findByTasksStatsType(TasksStatsType tasksStatsType);

    int countByTasksStatsType(TasksStatsType tasksStatsType);

    Page<Tasks> findAllByOrderByIdDesc(Pageable pageable);
    @Query("SELECT t FROM Tasks t WHERE " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.taskName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Tasks> findByDescriptionOrTaskNameContainingIgnoreCase(@Param("keyword") String keyword);
}
