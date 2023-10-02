package com.tablabs.hrms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.service.DepartmentImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DepartmentController {
    @Autowired
    private DepartmentImp departmentImp;

    private final Logger log = LoggerFactory.getLogger(DepartmentController.class);


    @PostMapping("/createDepartment")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody Department department){
        try {
            return departmentImp.createDepartment(department);
        }catch (Exception e){
            return ResponseEntity.ok(new Message(false,e.getMessage()));
        }
    }
    @GetMapping("/getDepartmentById")
    public ResponseEntity<?> getDepartmentById(@RequestParam(name = "departmentId")Long departmentId){
        return departmentImp.getDepartmentById(departmentId);
    }

    @GetMapping("/getAllDepartment")
    public ResponseEntity<?> getAllDepartment(@RequestParam(name = "page",defaultValue = "0",required = false)Integer page,
                                              @RequestParam(name = "size",defaultValue = "10",required = false)Integer size,
                                              @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                              @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return departmentImp.getAllDepartment(page,size,sortBy,sortDir);
    }
    @PutMapping("/updateDepartment")
    public ResponseEntity<?> updateDepartment(@RequestBody Department department) throws JsonProcessingException {
        return departmentImp.updateDepartment(department);
    }

    @DeleteMapping("/deleteDepartment")
    public ResponseEntity<?> deleteDepartment(@RequestParam(name = "departmentId") Long departmentId){
        try {
            return departmentImp.deleteDepartment(departmentId);
        }catch (Exception e){
            log.info(" ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("getAllEmployeeByDepartmentId")
    public ResponseEntity<?> getEmployeesByDepartmentId(@Valid @RequestParam(name = "departmentId")Long departmentId){
        try {
            return departmentImp.getAllEmployeeByDepartmentId(departmentId);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllDepartmentWithEmployeeDetails")
    public ResponseEntity<?> getAllDepartmentWithEmployeeDetails(@RequestParam(name = "page",defaultValue = "0",required = false)Integer page,
                                                                 @RequestParam(name = "size",defaultValue = "10",required = false)Integer size,
                                                                 @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                                 @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        try {
            return departmentImp.getAllDepartmentWithEmployeeDetails(page, size, sortBy, sortDir);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"something went error : "+e.getMessage()));
        }
    }
    @GetMapping("/searchDepartment")
    public ResponseEntity<?> searchDepartmentByKeyword(@RequestParam String keyword) throws JsonProcessingException {
        return departmentImp.searchDepartment(keyword);
    }

}
