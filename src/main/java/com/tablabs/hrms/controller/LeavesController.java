package com.tablabs.hrms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tablabs.hrms.models.responseDTO.LeaveRequestDto;
import com.tablabs.hrms.models.responseDTO.LeaveRequestsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Leaves;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.repository.LeavesRepository;
import com.tablabs.hrms.util.JsonObjectFormat;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@Api(value = "Leaves controller", description = "used to save,update and fetch Leaves details")
@RequestMapping("/api")
public class LeavesController {

	@Autowired
	LeavesRepository leavesRepository;

	@Autowired
	EmployeesRepository employeesRepository;

	// GetAll
	@GetMapping("/Leaves/getAll")
	public ResponseEntity<?> getAllLeaves() throws JsonProcessingException {

		try {
			List<LeaveRequestsDto> leaves = new ArrayList<>();
			List<Leaves> Garage = leavesRepository.findAll();
			List<Employees> employees = employeesRepository
					.findAllByEmployeeid(Garage.stream().map(Leaves::getEmployeeId).collect(Collectors.toList()));
			Garage.stream().forEach(leave -> {
				Optional<Employees> employee = employees.stream()
						.filter(emp -> emp.getEmployeeId().equals(String.valueOf(leave.getEmployeeId()))).findFirst();
				LeaveRequestsDto leaveRequestDto = new LeaveRequestsDto();
				leaveRequestDto.setEmployeeName(employee.get().getFirstname().concat(employee.get().getLastname()));
				leaveRequestDto.setEmpImage(employee.get().getImage());
				leaveRequestDto.setFromDate(leave.getStartDate());
				leaveRequestDto.setToDate(leave.getEndDate());
				leaveRequestDto.setReason(leave.getReason());
				leaveRequestDto.setLeaveType(leave.getLeaveType());
				leaveRequestDto.setStatus(leave.getStatus());
				leaves.add(leaveRequestDto);
			});

			return ResponseEntity.ok().body(new LeaveRequestDto(leaves));

		} catch (Exception e) {
			
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Leaves can not be Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(jsonobjectFormat);

		}

	}

	// pegination
	/*
	 * @GetMapping("/Leaves/getByPages") public ResponseEntity<String>
	 * getAllLeaves(@RequestParam(value = "page", required = false) Integer
	 * page, @RequestParam(value = "size", required = false) Integer size) throws
	 * JsonProcessingException { if (size == null) size = Integer.valueOf(10); if
	 * (page == null) page = Integer.valueOf(0);
	 * 
	 * PageRequest pageRequest = new PageRequest(page.intValue(), size.intValue());
	 * Pageable pageable =new PageRequest(page, size); Page<Leaves> obj =
	 * (Page<Leaves>) leavesRepository.findAll(pageable); //
	 * System.out.println(" obj " + obj.size()); if (obj.getSize()!=0) { // PageImpl
	 * pageImpls = new PageImpl(obj, (Pageable)pageRequest, obj.size());
	 * JsonObjectFormat jsonObjectFormat = new JsonObjectFormat();
	 * jsonObjectFormat.setMessage("Got all Leaves successfully");
	 * jsonObjectFormat.setSuccess(true); jsonObjectFormat.setData(obj);
	 * ObjectMapper objectMapper = new ObjectMapper(); String str =
	 * objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
	 * jsonObjectFormat); return ResponseEntity.ok().body(str); } JsonObjectFormat
	 * jsonobjectFormat = new JsonObjectFormat();
	 * jsonobjectFormat.setMessage("Leaves not found");
	 * jsonobjectFormat.setSuccess(false); ObjectMapper Obj = new ObjectMapper();
	 * String customExceptionStr =
	 * Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 * return ResponseEntity.ok().body(customExceptionStr); }
	 */

	// GetById
	@GetMapping("/Leaves/getById")
	public ResponseEntity<String> getLeavesById(@RequestParam("id") Long id) throws JsonProcessingException {

		try {

			Optional<Leaves> vehicle = leavesRepository.findById(id);

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Leaves fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(vehicle.get());

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			// TODO: handle exception

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Leaves can not be Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}

	// Save
	@PostMapping("/Leaves/save")
	public ResponseEntity<JsonObjectFormat> saveLeaves(@RequestBody Leaves content) throws JsonProcessingException {

		try {

			Leaves content1 = leavesRepository.save(content);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Leaves saved successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(content1);
			return ResponseEntity.ok().body(jsonobjectFormat);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Leaves not saved successfully " + e.getMessage());
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(jsonobjectFormat);

		}

	}

	// Delete
	@DeleteMapping("/Leaves/deleteById")
	public ResponseEntity<String> deleteLeavesById(@RequestParam("id") Long id) throws JsonProcessingException {

		try {
			Optional<Leaves> service = leavesRepository.findById(id);
			if (service.get() != null) {
				leavesRepository.deleteById(id);
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

				jsonobjectFormat.setMessage("Leaves Deleted successfully");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData("");

				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);

			}

			else {

				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

				jsonobjectFormat.setMessage("Unable to Delete Leaves");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");

				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);

			}

		} catch (Exception e) {
			// TODO: handle exception

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Unable to Delete Leaves");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}

	// This api is used to update particular Scheme by Id in database
	@PutMapping("/Leaves/update")
	public ResponseEntity<String> updateLeaves(@RequestBody Leaves address1) throws JsonProcessingException {

		try {
			Optional<Leaves> address = leavesRepository.findById(address1.getId());

			if (address1.getStatus() != null) {
				address.get().setStatus(address1.getStatus());
			}

			if (address1.getDateApplied() != null) {
				address.get().setDateApplied(address1.getDateApplied());
			}
			if (address1.getReason() != null) {
				address.get().setReason(address1.getReason());
			}

			if (address1.getEmployeeId() != null) {
				address.get().setEmployeeId(address1.getEmployeeId());
			}
			if (address1.getEndDate() != null) {
				address.get().setEndDate(address1.getEndDate());
			}
			if (address1.getNumberOfDays() != null) {
				address.get().setNumberOfDays(address1.getNumberOfDays());
			}

			if (address1.getStartDate() != null) {
				address.get().setStartDate(address1.getStartDate());
			}

			Leaves AddressUser1 = leavesRepository.save(address.get());
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Leaves updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(AddressUser1);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Leaves not found " + e.getMessage());
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData(address1);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}
}