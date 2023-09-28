package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<Tasks,Long> {

    boolean existsByTaskId(String taskId);
    Tasks findByTaskId(String taskId);
}
