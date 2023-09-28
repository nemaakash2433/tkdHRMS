package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositroy extends JpaRepository<Department,Long> {
    boolean existsByName(String name);
}
