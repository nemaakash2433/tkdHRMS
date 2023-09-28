package com.tablabs.hrms.repository;


import java.time.LocalDate;
import java.util.List;

import com.tablabs.hrms.entity.Attendance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Attendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	
	
//	List<Attendance> findById(Integer id);

	List<Attendance> findByEmployeeId(String emplId);

//	List<Attendance> findByEmployeeId(Integer id);
//
//	List<Attendance> findByEmployeeIdAndDateBetween(long longValue, LocalDate valueOf, LocalDate valueOf2);
//
//	List<Attendance> findByDateBetween(LocalDate valueOf, LocalDate valueOf2);

	List<Attendance> findByDate(LocalDate date);

}
