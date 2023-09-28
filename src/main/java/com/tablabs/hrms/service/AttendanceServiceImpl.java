package com.tablabs.hrms.service;

import com.tablabs.hrms.entity.Attendance;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.response.AttendanceAlongEmployeeDto;
import com.tablabs.hrms.repository.AttendanceRepository;
import com.tablabs.hrms.repository.EmployeesRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceServiceImpl{
	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private EmployeesRepository employeesRepository;


	public ResponseEntity<?> addAttendance(Attendance attendance) {
		try {
			if(employeesRepository.existsByEmployeeId(attendance.getEmployeeId())) {
				Attendance attendance1 = attendanceRepository.save(attendance);
				return ResponseEntity.ok(attendance1);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"This employee id its not exists in our record!!"));
		}catch (Exception e){
			e.printStackTrace();
			return ResponseEntity.ok(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public List<AttendanceAlongEmployeeDto> getAttendanceOfPrticularDate(LocalDate date) {
		List<Attendance> attendances = attendanceRepository.findByDate(date);
		List<String> empIds = attendances.stream().map(a -> a.getEmployeeId().toString()).collect(Collectors.toList());
		List<AttendanceAlongEmployeeDto> attendanceAlongWithEmployess = new ArrayList<>();

		List<Employees> emps = employeesRepository.findAllByEmployeeid(empIds);

		attendances.stream().forEach(attendance -> {

			AttendanceAlongEmployeeDto attendanceAlongEmployeeDto = new AttendanceAlongEmployeeDto();
			Employees employee = emps.stream()
					.filter(emp -> emp.getEmployeeId().equals(String.valueOf(attendance.getEmployeeId()))).findFirst()
					.get();
			attendanceAlongEmployeeDto.setEmployeeName(employee.getFirstname().concat(" "+employee.getLastname()));
			attendanceAlongEmployeeDto.setEmpImage(employee.getImage());
			attendanceAlongEmployeeDto.setMarkedAs(attendance.getMarkedAs());
			attendanceAlongEmployeeDto.setWorkingHours(attendance.getCheckIn() + "-" + attendance.getCheckOut());
			attendanceAlongEmployeeDto.setCheckIn(attendance.getTime().toString());
			attendanceAlongEmployeeDto.setCheckout(attendance.getTime().toString());
			attendanceAlongEmployeeDto.setDesignation(employee.getDesignation());
			attendanceAlongWithEmployess.add(attendanceAlongEmployeeDto);
		});
		if (attendanceAlongWithEmployess.isEmpty()) {
			return null;
		}
		return attendanceAlongWithEmployess;
	}
}
