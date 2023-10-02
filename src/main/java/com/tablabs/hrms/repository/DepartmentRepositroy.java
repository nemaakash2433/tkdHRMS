package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepositroy extends JpaRepository<Department,Long> {
    boolean existsByName(String name);
    @Query("SELECT d FROM Department d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.employeeId) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.contact) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Department> findByNameContainingIgnoreCaseOrEmployeeIdContainingIgnoreCaseOrContactContainingIgnoreCase(@Param("keyword") String keyword);

}
