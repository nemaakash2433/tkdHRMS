package com.tablabs.hrms.controller;


import com.tablabs.hrms.entity.Attendance;
import com.tablabs.hrms.models.responseDTO.AttendanceAlongEmployeeDto;
import com.tablabs.hrms.models.responseDTO.AttendanceDto;
import com.tablabs.hrms.repository.AttendanceRepository;

import com.tablabs.hrms.service.AttendanceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AttendanceController {

    private final Logger log = LoggerFactory.getLogger(AttendanceController.class);
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceServiceImpl attendanceService;


    @PostMapping("/addAttendance")
    public ResponseEntity<?> createAttendance(@RequestBody Attendance attendance) {
        log.debug("REST request to save Attendance : {}", attendance);
        return attendanceService.addAttendance(attendance);
    }


    @PutMapping("/updateAttendance")
    public ResponseEntity<Attendance> updateAttendance(@RequestBody Attendance attendance) throws URISyntaxException {
        log.debug("REST request to update Attendance : {}", attendance);
        if (attendance.getId() == null) {
            throw new RuntimeException("Invalid id");
        }
        attendance.setDate(attendance.getDate().plusDays(1));
        Attendance attendance2 = attendanceRepository.save(attendance);
        return ResponseEntity.ok()
                .body(attendance2);
    }

    /**
     * {@code GET  /attendances} : get all the attendances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendances in body.
     */
    @GetMapping("/attendances")
    public List<Attendance> getAllAttendances() {
        log.debug("REST request to get all Attendances");
        return attendanceRepository.findAll();
    }


    @GetMapping("/getAttendanceByEmployeeId/{id}")
    public ResponseEntity<List<Attendance>> getAttendance(@PathVariable String id) {
        log.debug("REST request to get Attendance : {}", id);
        List<Attendance> attendance = attendanceRepository.findByEmployeeId(id);
        return ResponseEntity.ok().body(attendance);
    }

    @DeleteMapping("/deleteAttendances/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isPresent()) {
            attendanceRepository.deleteById(id);
            return ResponseEntity.ok().body("Attendance Deleted Successfully");
        }
        return ResponseEntity.ok().body("Invalid Id");
    }


    @GetMapping("/getAttendanceByParticularDate/{date}")
    public ResponseEntity<?> getAttendanceOfPrticularDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.debug("REST request to get Attendance : {}", date);

        List<AttendanceAlongEmployeeDto> attendanceOfPrticularDate = attendanceService.getAttendanceOfPrticularDate(date);
        if (attendanceOfPrticularDate.isEmpty()) {
            return ResponseEntity.ok().body("Attendance is not updated on Particular Date");
        }
        return ResponseEntity.ok().body(new AttendanceDto(attendanceOfPrticularDate));
    }


}

