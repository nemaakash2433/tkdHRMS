package com.tablabs.hrms.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.DTO.EmployeeDTO;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.service.EmployeesServiceImpl;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class EmployeesController {

    private final Logger log = LoggerFactory.getLogger(EmployeesController.class);

    private static final String ENTITY_NAME = "employees";


    @Autowired
    private EmployeesServiceImpl employeesService;

    @Autowired
    private EmployeesRepository employeesRepository;


    @PostMapping("/createEmployees")
    public ResponseEntity<?> createEmployees(@RequestBody Employees employees) {
        try {
            log.info("REST request to save Employees : {}", employees);
            return employeesService.createEmployee(employees);
//            employees.setImage("default.png");
//            Employees result = employeesRepository.save(employees);
//            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
//            jsonobjectFormat.setMessage("Employee save successfully");
//            jsonobjectFormat.setSuccess(true);
//            jsonobjectFormat.setData(result);
//            ObjectMapper Obj = new ObjectMapper();
//            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter()
//                    .writeValueAsString(jsonobjectFormat);
//            return ResponseEntity.ok().body(customExceptionStr);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/updateEmployees")
    public ResponseEntity<?> updateEmployees(@Valid @RequestPart(name = "empDetails") Employees employees) throws JsonProcessingException {
        log.debug("REST request to update Employees : {}", employees);
        return employeesService.updateEmployee(employees);
    }


    @PostMapping("/getAllEmployees")//ase
    public ResponseEntity<?> getAllEmployees(@RequestParam(name = "page",defaultValue = "0",required = false)Integer page,@RequestParam(name = "size",defaultValue = "10",required = false)Integer size) throws JsonProcessingException {
        log.debug("REST request to get all Employees");
        return employeesService.findAll(page,size);
    }
    @GetMapping("/getAllEmployeesWithDesc")//desc
    public ResponseEntity<?> getAllEmployeesWithDesc(@RequestParam(value = "page", required=false)Integer page, @RequestParam(value = "size", required=false)Integer size) throws JsonProcessingException {
        try {

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
            Page<Employees> allByOrderByIdDesc = employeesRepository.findAllByOrderByIdDesc(pageable);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully fetch Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(allByOrderByIdDesc);
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


    @GetMapping("/getEmployees")
    public ResponseEntity<?> getEmployees(@RequestParam(name = "empId") String empId) throws JsonProcessingException {
        log.debug("REST request to get Employees : {}", empId);
        if (!empId.isEmpty()) {
            return employeesService.findOne(empId);
        }
        return ResponseEntity.ok("Please give proper id!!");
    }

    @DeleteMapping("/deleteEmployees")
    public ResponseEntity<?> deleteEmployees(@RequestParam(name = "employeeId") String empId) {
        log.debug("REST request to delete Employees : {}", empId);
        if (!empId.isEmpty()) {
            return employeesService.delete(empId);
        }
        return ResponseEntity.ok("Please give proper id!!");
    }

    @GetMapping("getEmployeeByIdWithDepartmentDetails")
    public ResponseEntity<?> getEmployeeByIdWithDepartmentDetails(@RequestParam(name = "empId")String empId){
        try {
            return employeesService.getEmployeeByIdWithDepartmentDetails(empId);
        }catch (Exception e){
            log.info(" : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("getAllEmployeeWithDepartment")
    public ResponseEntity<?> getAllEmployeeWithDepartment() throws JsonProcessingException {
        return employeesService.getAllEmployeeWithDepartment();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEmployees(@RequestParam String keyword) throws JsonProcessingException {
        return employeesService.searchEmployees(keyword);
    }
}
