package com.tablabs.hrms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tablabs.hrms.entity.Leaves;

import java.util.List;

@Repository
public interface LeavesRepository extends JpaRepository<Leaves, Long>{
    Page<Leaves> findAllByOrderByIdDesc(Pageable pageable);
    @Query("SELECT l FROM Leaves l WHERE " +
            "LOWER(l.status) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.leaveType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.reason) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Leaves> findByStatusOrLeaveTypeOrReasonContainingIgnoreCase(@Param("keyword") String keyword);
}
