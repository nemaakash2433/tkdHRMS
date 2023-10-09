package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Employees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Employees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
    //
//	Optional<Employees> findById(Long id);
//
//	boolean existsById(Long id);
//
//	void deleteById(Long id);
    @Query("SELECT e FROM Employees e " +
            "WHERE e.firstname LIKE %:keyword% " +
            "OR e.lastname LIKE %:keyword% " +
            "OR e.designation LIKE %:keyword%")
    List<Employees> findByKeyword(@Param("keyword") String keyword);

    List<Employees> findByDepartmentId(Long departmentId);

    Employees findByEmployeeId(String employeeId);

    boolean existsByEmployeeId(String empId);

    void deleteByEmployeeId(String empId);

    @Query("SELECT e FROM Employees e WHERE e.employeeId IN :empIds")
    List<Employees> findAllByEmployeeid(List<String> empIds);

    Page<Employees> findAllByOrderByIdDesc(Pageable pageable);

    Integer countByDepartmentId(Long dpId);

}
