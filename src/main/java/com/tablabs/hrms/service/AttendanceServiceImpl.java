package com.tablabs.hrms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Attendance;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.response.AttendanceAlongEmployeeDto;

import com.tablabs.hrms.models.response.ResponseWithPageDetails;
import com.tablabs.hrms.repository.AttendanceRepository;
import com.tablabs.hrms.repository.EmployeesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tablabs.hrms.util.JsonObjectFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceServiceImpl {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeesRepository employeesRepository;


    @Transactional(readOnly = true)
    public ResponseEntity<?> getAttendanceOfPrticularDate(String date,Integer page, Integer size, String sortBy, String sortDir) throws JsonProcessingException {
        try {
            Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Page<Attendance> attendances = attendanceRepository.findByDate(date, PageRequest.of(page,size,sort));
            if (attendances.isEmpty()) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("There is no employee present that day!!");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            //*****

            List<AttendanceAlongEmployeeDto> collect = attendances.stream().map(attendance -> {
                String employeeId = attendance.getEmployeeId();
                Employees employee = employeesRepository.findByEmployeeId(employeeId);
                AttendanceAlongEmployeeDto attendanceAlongEmployeeDto = new AttendanceAlongEmployeeDto();
                attendanceAlongEmployeeDto.setEmployeeName(employee.getFirstname().concat(" " + employee.getLastname()));
                attendanceAlongEmployeeDto.setEmpImage(employee.getImage());
                attendanceAlongEmployeeDto.setMarkedAs(attendance.getMarkedAs());
                attendanceAlongEmployeeDto.setWorkingHours(attendance.getInTime() + "-" + attendance.getOutTime());
                attendanceAlongEmployeeDto.setCheckIn(attendance.getInTime().toString());
                attendanceAlongEmployeeDto.setCheckout(attendance.getOutTime().toString());
                attendanceAlongEmployeeDto.setDesignation(employee.getDesignation());
                return attendanceAlongEmployeeDto;

            }).collect(Collectors.toList());

            //set page
            ResponseWithPageDetails responseWithPageDetails = new ResponseWithPageDetails();
            responseWithPageDetails.setDetailsWithPage(collect);
            responseWithPageDetails.setPageNumber(attendances.getNumber());
            responseWithPageDetails.setPageSize(attendances.getSize());
            responseWithPageDetails.setTotalElements(attendances.getTotalElements());
            responseWithPageDetails.setLastPage(attendances.isLast());
            responseWithPageDetails.setTotalPages(attendances.getTotalPages());


            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully fetch attendance on particular date!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(responseWithPageDetails);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }catch (Exception e){
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }
}
