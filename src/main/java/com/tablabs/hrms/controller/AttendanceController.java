package com.tablabs.hrms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Attendance;

import com.tablabs.hrms.models.response.AttendanceAlongEmployeeDto;
import com.tablabs.hrms.repository.AttendanceRepository;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.service.AttendanceServiceImpl;
import com.tablabs.hrms.util.JsonObjectFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttendanceController {

    private final Logger log = LoggerFactory.getLogger(AttendanceController.class);
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceServiceImpl attendanceService;

    @Autowired
    EmployeesRepository employeesRepository;

    @PostMapping("/postAttendance")
    public ResponseEntity<String> postAttendance(@RequestBody Attendance attendance) throws JsonProcessingException {
        try {
            if (employeesRepository.findByEmployeeId(attendance.getEmployeeId()) != null) {
                Attendance save = attendanceRepository.save(attendance);

                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Attendance saved successfully");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(save);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            } else {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Enter valid employee id");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData(" ");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("no data found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    @PutMapping("/updateAttendance")
    public ResponseEntity<String> updateAttendance(@RequestBody Attendance attendance)
            throws URISyntaxException, JsonProcessingException {
        log.debug("REST request to update Attendance : {}", attendance);
        if (attendance.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        attendance.setDate(attendance.getDate());
        Attendance attendance2 = attendanceRepository.save(attendance);
        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
        jsonobjectFormat.setMessage("Attendence updated successfully");
        jsonobjectFormat.setSuccess(true);
        jsonobjectFormat.setData(attendance2);
        ObjectMapper Obj = new ObjectMapper();
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
        return ResponseEntity.ok().body(customExceptionStr);
    }

    /**
     * {@code GET  /attendances} : get all the attendances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of attendances in body.
     * @throws JsonProcessingException
     */
    @GetMapping("/attendances")
    public ResponseEntity<String> getAllAttendances() throws JsonProcessingException {
        log.debug("REST request to get all Attendances");
        List<Attendance> attendances = attendanceRepository.findAll();
        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
        jsonobjectFormat.setMessage("Attendance fetch successfully");
        jsonobjectFormat.setSuccess(true);
        jsonobjectFormat.setData(attendances);
        ObjectMapper Obj = new ObjectMapper();
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
        return ResponseEntity.ok().body(customExceptionStr);
    }

    @GetMapping("/getAttendanceByEmployeeId/{id}")
    public ResponseEntity<String> getAttendance(@PathVariable String id) throws JsonProcessingException {
        log.debug("REST request to get Attendance : {}", id);
        if (employeesRepository.existsByEmployeeId(id)) {
            List<Attendance> attendance = attendanceRepository.findByEmployeeId(id);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Attendance fetch successfully");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(attendance);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
        jsonobjectFormat.setMessage("Employee id doesn't exists in our record!!");
        jsonobjectFormat.setSuccess(false);
        jsonobjectFormat.setData("");
        ObjectMapper Obj = new ObjectMapper();
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
        return ResponseEntity.ok().body(customExceptionStr);
    }

    @DeleteMapping("/deleteAttendances/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);

        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isPresent()) {
            attendanceRepository.deleteById(attendance.get().getId());
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Attendance Deleted successfully");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData("");
            return ResponseEntity.ok().body(jsonobjectFormat);
        } else {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Unable to Delete Attendance");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            return ResponseEntity.ok().body(jsonobjectFormat);
        }
    }

    @GetMapping("/getAttendanceByParticularDate/{date}")
    public ResponseEntity<?> getAttendanceOfPrticularDate(
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") String date,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) throws JsonProcessingException {
        log.debug("REST request to get Attendance : {}", date);
        return attendanceService.getAttendanceOfPrticularDate(date,page,size,sortBy,sortDir);
    }

    @GetMapping("/getTotalWorkingHourByEmpId/{employeeId}")
    public ResponseEntity<String> getTotalWorkingHourByEmpId(@PathVariable String employeeId)
            throws JsonProcessingException {
        try {
            List<Attendance> employeeAttendance = attendanceRepository.findByEmployeeId(employeeId);
            long totalWorkingMilliseconds = 0;
            for (Attendance attendance : employeeAttendance) {
                Date inTime = attendance.getInTime();
                Date outTime = attendance.getOutTime();
                if (inTime != null && outTime != null) {
                    long workingMilliseconds = outTime.getTime() - inTime.getTime();
                    totalWorkingMilliseconds += workingMilliseconds;
                }
            }
            // Convert milliseconds to hours and minutes
            long totalWorkingMinutes = totalWorkingMilliseconds / (60 * 1000);
            long hours = totalWorkingMinutes / 60;
            long minutes = totalWorkingMinutes % 60;

            String response = hours + " hours " + minutes + " minutes";
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Employees working hours fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(response);

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("data are not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);

        }
    }

    @GetMapping("/getTotalWorkingHourByDate/{dateStr}")
    public ResponseEntity<JsonObjectFormat> getTotalWorkingHourByDate(@PathVariable String dateStr,@RequestParam(defaultValue = "0",required = false)Integer page)
            throws ParseException, JsonProcessingException {

        try {
            Page<Attendance> attendances = attendanceRepository.findByDate(dateStr,PageRequest.of(page,10));
            long totalWorkingMilliseconds = 0;
            for (Attendance attendance : attendances) {
                Date inTime = attendance.getInTime();
                Date outTime = attendance.getOutTime();
                if (inTime != null && outTime != null) {
                    long workingMilliseconds = outTime.getTime() - inTime.getTime();
                    totalWorkingMilliseconds += workingMilliseconds;
                }
            }
            // Convert milliseconds to hours and minutes
            long totalWorkingMinutes = totalWorkingMilliseconds / (60 * 1000);
            long hours = totalWorkingMinutes / 60;
            long minutes = totalWorkingMinutes % 60;

            String response = hours + " hours " + minutes + " minutes";
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Employees working hours fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(response);

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(jsonobjectFormat);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("data are not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(jsonobjectFormat);
        }
    }

    @GetMapping("/getAllTotalWorkingHour")
    public ResponseEntity<JsonObjectFormat> getAllTotalWorkingHour() throws ParseException, JsonProcessingException {
        try {
            List<Attendance> attendances = attendanceRepository.findAll();
            long totalWorkingMilliseconds = 0;
            for (Attendance attendance : attendances) {
                Date inTime = attendance.getInTime();
                Date outTime = attendance.getOutTime();
                if (inTime != null && outTime != null) {
                    long workingMilliseconds = outTime.getTime() - inTime.getTime();
                    totalWorkingMilliseconds += workingMilliseconds;
                }
            }
            // Convert milliseconds to hours and minutes
            long totalWorkingMinutes = totalWorkingMilliseconds / (60 * 1000);
            long hours = totalWorkingMinutes / 60;
            long minutes = totalWorkingMinutes % 60;

            String response = hours + " hours " + minutes + " minutes";
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Employees working hours fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(response);

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(jsonobjectFormat);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("data are not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(jsonobjectFormat);

        }
    }

    @GetMapping("/getAllAttendanceWithDesc")
    public ResponseEntity<String> getAllAttendanceWithDesc(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) throws JsonProcessingException {
        if (size != null) {
        } else {
            size = 10;
        }
        if (page != null) {
        } else {
            page = 0;
        }

        Pageable pageable = PageRequest.of(page, size);
        log.debug("REST request to get all Attendances");
        Page<Attendance> allByOrderByIdDesc = attendanceRepository.findAllByOrderByIdDesc(pageable);
        if (allByOrderByIdDesc.isEmpty()) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("There is no data!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
        jsonobjectFormat.setMessage("Successfully fetch attendance on particular date!");
        jsonobjectFormat.setSuccess(true);
        jsonobjectFormat.setData(allByOrderByIdDesc);
        ObjectMapper Obj = new ObjectMapper();
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
        return ResponseEntity.ok().body(customExceptionStr);
    }

    @GetMapping("/searchAttendance")
    public ResponseEntity<String> searchAttendance(@RequestParam("keyword") String keyword) throws JsonProcessingException {
        try {
            List<Attendance> employees = attendanceRepository.findByLocationContainingIgnoreCaseOrmarkedAsContainingIgnoreCase(keyword);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("Attendance fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(employees);

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("Attendance are not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }
}
