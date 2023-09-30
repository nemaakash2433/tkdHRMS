package com.tablabs.hrms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Attendance;
import com.tablabs.hrms.models.response.AttendanceAlongEmployeeDto;
import com.tablabs.hrms.models.response.AttendanceDto;
import com.tablabs.hrms.repository.AttendanceRepository;

import com.tablabs.hrms.service.AttendanceServiceImpl;
import com.tablabs.hrms.util.JsonObjectFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AttendanceController {

	private final Logger log = LoggerFactory.getLogger(AttendanceController.class);
	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private AttendanceServiceImpl attendanceService;

	@PostMapping("/addAttendance")
	public ResponseEntity<?> createAttendance(@RequestBody Attendance attendance) throws JsonProcessingException {
		log.debug("REST request to save Attendance : {}", attendance);
		try {
			 ResponseEntity<?> addAttendance = attendanceService.addAttendance(attendance);
			 JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
				jsonobjectFormat.setMessage("Attendance Added successfully");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData(addAttendance);

				return ResponseEntity.ok().body(jsonobjectFormat);
		
		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Unable to add Attendance");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(jsonobjectFormat);
		}
		
	}
	@PutMapping("/updateAttendance")
	public ResponseEntity<?> updateAttendance(@RequestBody Attendance attendance) throws URISyntaxException, JsonProcessingException {
		log.debug("REST request to update Attendance : {}", attendance);
		try {
			Optional<Attendance> attendance1 = attendanceRepository.findById(attendance.getId());
			if (attendance1.get() != null) {
				if (attendance.getCardId() != null) {
					attendance1.get().setCardId(attendance.getCardId());
				}
				if (attendance.getDate() != null) {
					attendance1.get().setDate(attendance.getDate());
				}
				if (attendance.getEmployeeId() != null) {
					attendance1.get().setEmployeeId(attendance.getEmployeeId());
				}
				if (attendance.getCheckIn() != null) {
					attendance1.get().setCheckIn(attendance.getCheckIn());
				}
				if (attendance.getCheckOut() != null) {
					attendance1.get().setCheckOut(attendance.getCheckOut());
				}
				if (attendance.getMarkedAs() != null) {
					attendance1.get().setMarkedAs(attendance.getMarkedAs());
				}
				if (attendance.getTime() != null) {
					attendance1.get().setTime(attendance.getTime());
				}
				Attendance attendance2 = attendanceRepository.save(attendance1.get());
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
				jsonobjectFormat.setMessage("Attendance Updated successfully");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData(attendance2);

				return ResponseEntity.ok().body(jsonobjectFormat);
			} else
				throw new RuntimeException("Invalid id");

		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Attendance can not be Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
		
	}

	/**
	 * {@code GET  /attendances} : get all the attendances.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of attendances in body.
	 * @throws JsonProcessingException 
	 */
	@GetMapping("/attendances")
	public ResponseEntity<?> getAllAttendances(@RequestParam(name = "page",defaultValue = "0",required = false)Integer page,
            @RequestParam(name = "size",defaultValue = "10",required = false)Integer size,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "dsc", required = false)String sortDir)throws JsonProcessingException {
		log.debug("REST request to get all Attendances");
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		try {
			Page<Attendance> attenndences = attendanceRepository.findAll(PageRequest.of(page, size,sort));
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Attendance fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(attenndences);
			return ResponseEntity.ok().body(jsonobjectFormat);

		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Attendance can not be Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("/getAttendanceByEmployeeId/{id}")
	public ResponseEntity<List<Attendance>> getAttendance(@PathVariable String id) {
		log.debug("REST request to get Attendance : {}", id);
		List<Attendance> attendance = attendanceRepository.findByEmployeeId(id);
		return ResponseEntity.ok().body(attendance);
	}

	@DeleteMapping("/deleteAttendances/{id}")
	public ResponseEntity<?> deleteAttendance(@PathVariable Long id) throws JsonProcessingException {
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
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

	@GetMapping("/getAttendanceByParticularDate/{date}")
	public ResponseEntity<?> getAttendanceOfPrticularDate(
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		log.debug("REST request to get Attendance : {}", date);

		if (date == null) {
			return ResponseEntity.badRequest().body("Unable to fetch Attendance on Particular Date");
		}
		List<AttendanceAlongEmployeeDto> attendanceOfPrticularDate = attendanceService
				.getAttendanceOfPrticularDate(date);

		return ResponseEntity.ok().body(new AttendanceDto(attendanceOfPrticularDate));
	}

}
