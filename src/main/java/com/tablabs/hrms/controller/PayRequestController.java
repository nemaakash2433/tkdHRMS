package com.tablabs.hrms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tablabs.hrms.errors.EmployeeDoesNotExistException;
import com.tablabs.hrms.models.request.PayRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.PayRequest;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.repository.PayRequestRepository;
import com.tablabs.hrms.util.JsonObjectFormat;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class PayRequestController {

	@Autowired
	private PayRequestRepository payRequestRepository;

	@Autowired
	private EmployeesRepository employeesRepository;

	@GetMapping("/payRequest/getAll")
	public ResponseEntity<String> getAllPayRequests() throws JsonProcessingException {

		try {
			List<PayRequest> payRequest = payRequestRepository.findAll();
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("PayReqests fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(payRequest);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("PayReqests can not be Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
		}
	}

//	// pegination
//	@GetMapping("/payRequest/getByPages")
//	public ResponseEntity<String> getAllVacations(@RequestParam(value = "page", required = false) Integer page,
//			@RequestParam(value = "size", required = false) Integer size) throws JsonProcessingException {
//		if (size == null)
//			size = Integer.valueOf(10);
//		if (page == null)
//			page = Integer.valueOf(0);
//
//		Page<PayRequest> obj = payRequestRepository.findAll(PageRequest.of(page, size));
//		if (obj.getSize() != 0) {
//			JsonObjectFormat jsonObjectFormat = new JsonObjectFormat();
//			jsonObjectFormat.setMessage("Got all PayReqests successfully");
//			jsonObjectFormat.setSuccess(true);
//			jsonObjectFormat.setData(obj.getContent());
//			ObjectMapper objectMapper = new ObjectMapper();
//			String str = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObjectFormat);
//			return ResponseEntity.ok().body(str);
//		}
//		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
//		jsonobjectFormat.setMessage("PayReqests not found");
//		jsonobjectFormat.setSuccess(false);
//		ObjectMapper Obj = new ObjectMapper();
//		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//		return ResponseEntity.ok().body(customExceptionStr);
//	}

	// GetById
	@GetMapping("/payRequest/getById")
	public ResponseEntity<String> getVacationsById(@RequestParam("id") Long id) throws JsonProcessingException {

		try {
			Optional<PayRequest> payRequest = payRequestRepository.findById(id);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("PayReqests fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(payRequest.get());

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("PayReqests can not be Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}

	// Save
	@PostMapping("/payRequest/save")
	public ResponseEntity<String> savepayRequest(@RequestBody PayRequest payRequest) throws JsonProcessingException {

		try {
			if(!payRequestRepository.existsByEmployeeId(payRequest.getEmployeeId())){
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
				jsonobjectFormat.setMessage("Employee is not exists!!  Enter valid employee id...");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
			double netSalaryTotal = payRequest.getGrossSalary() - payRequest.getDeduction();
			String netSalary = String.valueOf(netSalaryTotal);
			String netSalaryNegativeR = netSalary.replace('-', ' ');
			payRequest.setNetSalary(Double.parseDouble(netSalaryNegativeR));
			PayRequest payRequest1 = payRequestRepository.save(payRequest);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("PayReqests saved successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(payRequest1);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("PayReqests not saved successfully " + e.getMessage());
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}

	// Delete
	@DeleteMapping("/payRequest/deleteById")
	public ResponseEntity<String> deleteVacationsById(@RequestParam("id") Long id) throws JsonProcessingException {
		try {
			Optional<PayRequest> service = payRequestRepository.findById(id);
			if (service != null) {
				payRequestRepository.deleteById(id);
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

				jsonobjectFormat.setMessage("PayReqests Deleted successfully");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData("");

				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
			else {
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
				jsonobjectFormat.setMessage("Unable to Delete PayReqests");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");

				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}

		} catch (Exception e) {

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("Unable to Delete Vacations");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}

	// This api is used to update particular Scheme by Id in database
	@PutMapping("/payRequest/update")
	public ResponseEntity<String> updateVacations(@RequestBody PayRequest payRequest) throws JsonProcessingException {

		try {
			if(!payRequestRepository.existsByEmployeeId(payRequest.getEmployeeId())){
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
				jsonobjectFormat.setMessage("Employee is not exists!!  Enter valid employee id...");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");
				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);
			}
			Optional<PayRequest> entity = payRequestRepository.findById(payRequest.getId());

			if (payRequest.getEmployeeId() != null) {
				entity.get().setEmployeeId(payRequest.getEmployeeId());
			}
			if (payRequest.getBenefits() != null) {
				entity.get().setBenefits(payRequest.getBenefits());
			}
			if (payRequest.getBenefits() != null) {
				entity.get().setBenefits(payRequest.getBenefits());
			}
			if (payRequest.getGrossSalary() != null) {
				entity.get().setGrossSalary(payRequest.getGrossSalary());
			}
			if(payRequest.getDeduction()!=null){
				entity.get().setDeduction(payRequest.getDeduction());
			}
			double netSalaryTotal = payRequest.getGrossSalary() - payRequest.getDeduction();
			String netSalary = String.valueOf(netSalaryTotal);
			String netSalaryNegativeR = netSalary.replace('-', ' ');
			entity.get().setNetSalary(Double.parseDouble(netSalaryNegativeR));
			if (payRequest.getStatus()!=null){
				entity.get().setStatus(payRequest.getStatus());
			}

			PayRequest pay = payRequestRepository.save(entity.get());
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("PayReqests updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(pay);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("PayReqests not found " + e.getMessage());
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}


	@GetMapping("/payRequestsAlongWithEmpDetails")
	public ResponseEntity<String> getAllPayRequestsByEmployeeDeatils() throws JsonProcessingException {
		List<PayRequest> payRequests = payRequestRepository.findAll();
		List<String> empIds = payRequests.stream().map(PayRequest::getEmployeeId).collect(Collectors.toList());
		List<PayRequestDto> requestDtos = new ArrayList<>();
		List<Employees> emps = employeesRepository.findAllByEmployeeid(empIds);
		if(!emps.isEmpty()) {
			payRequests.stream().forEach(payRequest -> {
				PayRequestDto payDto = new PayRequestDto();
				Employees employees = emps.stream().filter(e -> e.getEmployeeId().equals(payRequest.getEmployeeId()))
						.findFirst().orElseThrow(() -> new EmployeeDoesNotExistException("Employee not found for PayRequest: ", payRequest.getEmployeeId(), null));
				payDto.setEmployeeName(employees.getFirstname() + " " + employees.getLastname());
				payDto.setEmployeeImage(employees.getImage());
				payDto.setDesignation(employees.getDesignation());
				payDto.setBenefits(payRequest.getBenefits());
				payDto.setGrossSalary(payRequest.getGrossSalary());
				payDto.setDeduction(payRequest.getDeduction());
				payDto.setStatus(payRequest.getStatus());
				requestDtos.add(payDto);
			});
		}

		if (payRequests != null) {
			JsonObjectFormat jsonObjectFormat = new JsonObjectFormat();
			jsonObjectFormat.setMessage("Got all PayReqests successfully");
			jsonObjectFormat.setSuccess(true);
			jsonObjectFormat.setData(requestDtos);
			ObjectMapper objectMapper = new ObjectMapper();
			String str = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObjectFormat);
			return ResponseEntity.ok().body(str);
		}
		JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
		jsonobjectFormat.setMessage("PayReqests not found");
		jsonobjectFormat.setSuccess(false);
		ObjectMapper Obj = new ObjectMapper();
		String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		return ResponseEntity.ok().body(customExceptionStr);
	}
	@GetMapping("/getPayRequestWithDesc")
	public Page<PayRequest> getAllPayRequestWithDesc(@RequestParam(value = "page", required=false)Integer page,@RequestParam(value = "size", required=false)Integer size) {
		if(size!=null){
		}else{
			size=10;
		}
		if(page!=null){
		}else{
			page=0;
		}

		Pageable pageable =PageRequest.of(page, size);
		return payRequestRepository.findAllByOrderByIdDesc(pageable);
	}



}
