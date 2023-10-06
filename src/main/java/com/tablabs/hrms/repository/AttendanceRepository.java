package com.tablabs.hrms.repository;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tablabs.hrms.entity.Attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Attendance entity.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {


//	List<Attendance> findById(Integer id);

	List<Attendance> findByEmployeeId(String emplId);

//	List<Attendance> findByEmployeeId(Integer id);
//
//	List<Attendance> findByEmployeeIdAndDateBetween(long longValue, LocalDate valueOf, LocalDate valueOf2);
//
//	List<Attendance> findByDateBetween(LocalDate valueOf, LocalDate valueOf2);

	@Query("SELECT a FROM Attendance a WHERE a.date = :date")
	Page<Attendance> findByDate(@Param("date") String date,Pageable pageable);

	Optional<Attendance> findById(Long id);

	void deleteById(Long id);

	Page<Attendance> findAllByOrderByIdDesc(Pageable pageable);

	@Query("SELECT a FROM Attendance a WHERE LOWER(a.location) LIKE %:keyword% OR LOWER(a.markedAs) LIKE %:keyword%")
	List<Attendance> findByLocationContainingIgnoreCaseOrmarkedAsContainingIgnoreCase(@Param("keyword") String keyword);



}
