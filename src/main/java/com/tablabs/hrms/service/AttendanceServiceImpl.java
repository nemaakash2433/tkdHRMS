package com.tablabs.hrms.service;

import com.tablabs.hrms.entity.Attendance;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.response.AttendanceAlongEmployeeDto;

import com.tablabs.hrms.repository.AttendanceRepository;
import com.tablabs.hrms.repository.EmployeesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceServiceImpl{
	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private EmployeesRepository employeesRepository;



	@Transactional(readOnly = true)
	public List<AttendanceAlongEmployeeDto> getAttendanceOfPrticularDate(String date) {
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
			attendanceAlongEmployeeDto.setWorkingHours(attendance.getInTime() + "-" + attendance.getOutTime());
			attendanceAlongEmployeeDto.setCheckIn(attendance.getInTime().toString());
			attendanceAlongEmployeeDto.setCheckout(attendance.getOutTime().toString());
			attendanceAlongEmployeeDto.setDesignation(employee.getDesignation());
			attendanceAlongWithEmployess.add(attendanceAlongEmployeeDto);
		});
		if (attendanceAlongWithEmployess.isEmpty()) {
			return null;
		}
		return attendanceAlongWithEmployess;
	}
}
