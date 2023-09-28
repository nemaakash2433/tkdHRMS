package com.tablabs.hrms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.tablabs.hrms.entity.LeavesType;
import com.tablabs.hrms.repository.LeavesTypeRepository;
import com.tablabs.hrms.util.JsonObjectFormat;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@Api(value = "LeavesType controller",description = "used to save,update and fetch LeavesType details")
@RequestMapping("/api")
public class LeavesTypeController {

	@Autowired
	LeavesTypeRepository leavesTypeRepository;
	
	//GetAll
	@GetMapping("/LeavesType/getAll")
	public ResponseEntity<String> getAllLeavesType() throws JsonProcessingException {

		try {
			List<LeavesType> Garage = (List<LeavesType>) leavesTypeRepository.findAll();
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("LeavesType fetch successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(Garage);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			// TODO: handle exception

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("LeavesType can not be Found");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}
	
	//pegination
	/*
	 * @GetMapping("/LeavesType/getByPages") public ResponseEntity<String>
	 * getAllLeavesType(@RequestParam(value = "page", required = false) Integer
	 * page, @RequestParam(value = "size", required = false) Integer size) throws
	 * JsonProcessingException { if (size == null) size = Integer.valueOf(10); if
	 * (page == null) page = Integer.valueOf(0); PageRequest pageRequest = new
	 * PageRequest(page.intValue(), size.intValue()); Pageable pageable =new
	 * PageRequest(page, size); Page<LeavesType> obj = (Page<LeavesType>)
	 * leavesTypeRepository.findAll(pageable); // System.out.println(" obj " +
	 * obj.size()); if (obj.getSize()!=0) { // PageImpl pageImpls = new
	 * PageImpl(obj, (Pageable)pageRequest, obj.size()); JsonObjectFormat
	 * jsonObjectFormat = new JsonObjectFormat();
	 * jsonObjectFormat.setMessage("Got all LeavesType successfully");
	 * jsonObjectFormat.setSuccess(true); jsonObjectFormat.setData(obj);
	 * ObjectMapper objectMapper = new ObjectMapper(); String str =
	 * objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
	 * jsonObjectFormat); return ResponseEntity.ok().body(str); } JsonObjectFormat
	 * jsonobjectFormat = new JsonObjectFormat();
	 * jsonobjectFormat.setMessage("LeavesType not found");
	 * jsonobjectFormat.setSuccess(false); ObjectMapper Obj = new ObjectMapper();
	 * String customExceptionStr =
	 * Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
	 * return ResponseEntity.ok().body(customExceptionStr); }
	 */
	
	
	//GetById
	@GetMapping("/LeavesType/getById")
	public ResponseEntity<String> getLeavesTypeById(@RequestParam("id") Long id) throws JsonProcessingException{
			
		try {
			 
			Optional<LeavesType> vehicle = leavesTypeRepository.findById(id);
			 
			JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("LeavesType fetch successfully");
			 jsonobjectFormat.setSuccess(true);
			 jsonobjectFormat.setData(vehicle.get());
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
			
		}catch (Exception e) {
			// TODO: handle exception
			
			 JsonObjectFormat jsonobjectFormat=new JsonObjectFormat();
			 
			 jsonobjectFormat.setMessage("LeavesType can not be Found");
			 jsonobjectFormat.setSuccess(false);
			 jsonobjectFormat.setData("");
	         
			 ObjectMapper Obj = new ObjectMapper(); 
	         String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
		 	 return ResponseEntity.ok().body(customExceptionStr);
			
		}
		
	}
	
	//Save
	@PostMapping("/LeavesType/save")
	public ResponseEntity<String> saveLeavesType(@RequestBody LeavesType content) throws JsonProcessingException {

		try {
			
			LeavesType content1 = leavesTypeRepository.save(content);
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("LeavesType saved successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(content1);

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("LeavesType not saved successfully "+e.getMessage());
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}
		

	}
	
	
	//Delete
	@DeleteMapping("/LeavesType/deleteById")
	public ResponseEntity<String> deleteLeavesTypeById(@RequestParam("id") Long id) throws JsonProcessingException {

		try {
			Optional<LeavesType> service = leavesTypeRepository.findById(id);
			if (service.get() != null) {
				leavesTypeRepository.deleteById(id);
				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

				jsonobjectFormat.setMessage("LeavesType Deleted successfully");
				jsonobjectFormat.setSuccess(true);
				jsonobjectFormat.setData("");

				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);

			}

			else {

				JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

				jsonobjectFormat.setMessage("Unable to Delete LeavesType");
				jsonobjectFormat.setSuccess(false);
				jsonobjectFormat.setData("");

				ObjectMapper Obj = new ObjectMapper();
				String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
				return ResponseEntity.ok().body(customExceptionStr);

			}

		} catch (Exception e) {
			// TODO: handle exception

			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

			jsonobjectFormat.setMessage("Unable to Delete LeavesType");
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData("");

			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}
	
	
	//This api is used to update particular Scheme by Id in database
	@PutMapping("/LeavesType/update")
	public ResponseEntity<String> updateLeavesType(@RequestBody LeavesType address1) throws JsonProcessingException {

		try {
			Optional<LeavesType> address = leavesTypeRepository.findById(address1.getId());

			if (address1.getActive()!= null) {
				address.get().setActive(address1.getActive());
			}
			
			if (address1.getDescription()!= null) {
				address.get().setDescription(address1.getDescription());
			}
			if (address1.getNumberOfLeves()!= null) {
				address.get().setNumberOfLeves(address1.getNumberOfLeves());
			}
			
			if (address1.getType()!= null) {
				address.get().setType(address1.getType());
			}
			
		
			LeavesType AddressUser1 = leavesTypeRepository.save(address.get());
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("LeavesType updated successfully");
			jsonobjectFormat.setSuccess(true);
			jsonobjectFormat.setData(AddressUser1);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		} catch (Exception e) {
			JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
			jsonobjectFormat.setMessage("LeavesType not found "+e.getMessage());
			jsonobjectFormat.setSuccess(false);
			jsonobjectFormat.setData(address1);
			ObjectMapper Obj = new ObjectMapper();
			String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
			return ResponseEntity.ok().body(customExceptionStr);

		}

	}
}