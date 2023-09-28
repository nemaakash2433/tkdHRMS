package com.tablabs.hrms.controller;


import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.service.EmployeesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EmployeesController {

    private final Logger log = LoggerFactory.getLogger(EmployeesController.class);

    private static final String ENTITY_NAME = "employees";


    @Autowired
    private EmployeesServiceImpl employeesService;

    @Autowired
    private EmployeesRepository employeesRepository;


    @PostMapping("/createEmployees")
    public ResponseEntity<?> createEmployees(@Valid @RequestPart(name = "empDetails") Employees employees, @RequestParam(name="image",defaultValue = "default.png",required = false)MultipartFile multipartFile) {
        try {
            log.info("REST request to save Employees : {}", employees);
            return employeesService.createEmployee(employees,multipartFile);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/updateEmployees")
    public ResponseEntity<?> updateEmployees(@Valid @RequestPart(name = "empDetails") Employees employees, @RequestParam(name="image",defaultValue = "default.png",required = false)MultipartFile multipartFile){
        log.debug("REST request to update Employees : {}", employees);
        return employeesService.updateEmployee(employees,multipartFile);
    }

    @PostMapping("/getAllEmployees")
    public List<?> getAllEmployees(@RequestParam(name = "page",defaultValue = "0",required = false)Integer page) {
        log.debug("REST request to get all Employees");
        return employeesService.findAll(page);
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<?> getEmployees(@RequestParam(name = "empId") String empId) {
        log.debug("REST request to get Employees : {}", empId);
        if (!empId.isEmpty()) {
            return employeesService.findOne(empId);
        }
        return ResponseEntity.ok("Please give proper id!!");
    }

    @DeleteMapping("/deleteEmployees/{id}")
    public ResponseEntity<?> deleteEmployees(@PathVariable Long id) {
        log.debug("REST request to delete Employees : {}", id);
        if (id > 0) {
            return employeesService.delete(id);
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
    public ResponseEntity<?> getAllEmployeeWithDepartment(){
        return employeesService.getAllEmployeeWithDepartmentDetails();
    }
}