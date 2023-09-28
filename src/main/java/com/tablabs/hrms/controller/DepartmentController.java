package com.tablabs.hrms.controller;

import com.tablabs.hrms.entity.Department;
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
public class DepartmentController {
    @Autowired
    private DepartmentImp departmentImp;

    private final Logger log = LoggerFactory.getLogger(DepartmentController.class);


    @PostMapping("/createDepartment")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody Department department){
        return departmentImp.createDepartment(department);
    }
    @GetMapping("/getDepartmentById")
    public ResponseEntity<?> getDepartmentById(@RequestParam(name = "departmentId")Long departmentId){
        return departmentImp.getDepartmentById(departmentId);
    }

    @GetMapping("/getAllDepartment")
    public ResponseEntity<?> getAllDepartment(@RequestParam(name = "page",defaultValue = "0",required = false)Integer page){
        return departmentImp.getAllDepartment(page);
    }
    @PutMapping("/updateDepartment")
    public ResponseEntity<?> updateDepartment(@RequestBody Department department,@RequestParam(name = "departmentId") Long departmentId){
        return departmentImp.updateDepartment(department,departmentId);
    }

    @DeleteMapping("/deleteDepartment")
    public ResponseEntity<?> deleteDepartment(@RequestParam(name = "departmentId") Long departmentId){
        try {
            return departmentImp.deleteDepartmentById(departmentId);
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
    public ResponseEntity<?> getAllDepartmentWithEmployeeDetails(@RequestParam(name = "page",defaultValue = "0",required = false)Integer page){
        return departmentImp.testapi();
    }

}
