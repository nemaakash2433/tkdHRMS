package com.tablabs.hrms.service;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.responseDTO.GetAllDepartmentWithEmployeeDetails;
import com.tablabs.hrms.models.responseDTO.GetEmployeesByDepartmentId;
import com.tablabs.hrms.repository.DepartmentRepositroy;
import com.tablabs.hrms.repository.EmployeesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentImp {
    private final Logger log = LoggerFactory.getLogger(DepartmentImp.class);
    @Autowired
    private DepartmentRepositroy getDepartmentRep;
    @Autowired
    private EmployeesRepository employeesRepository;

    public ResponseEntity<?> createDepartment(Department department) {
        log.debug("Request to create department : {}", department.getName());
        if (getDepartmentRep.existsByName(department.getName())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Message(false,"Department name is already reported!!"));
        }
        Department save = getDepartmentRep.save(department);
        return ResponseEntity.ok(save);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getDepartmentById(Long departmentId) {
        Department department = getDepartmentRep.findById(departmentId).get();
        if (department != null) {
            return ResponseEntity.ok(department);
        }
        return new ResponseEntity("There is no data by this id!!", HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllDepartment(Integer page) {
        Page<Department> getDepartmentRepAll = getDepartmentRep.findAll(PageRequest.of(page, 5));
        List<Department> content = getDepartmentRepAll.getContent();
        return ResponseEntity.ok(content);
    }

    public ResponseEntity<?> deleteDepartmentById(Long departmentId) {
        log.debug("Request for delete department : {}", departmentId);
        if (getDepartmentRep.existsById(departmentId)) {
            getDepartmentRep.deleteById(departmentId);
            List<Employees> byDepartmentId = employeesRepository.findByDepartmentId(departmentId);
            if (!byDepartmentId.isEmpty()) {
                byDepartmentId.stream().forEach(employees -> employeesRepository.deleteById(employees.getId()));
                return ResponseEntity.ok().body("Successfully deleted");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please give valid id!!");
    }

    public ResponseEntity<?> updateDepartment(Department department, Long departmentId) {
        Optional<Department> getDepartmentRepById = getDepartmentRep.findById(departmentId);
        if (getDepartmentRepById.isPresent()) {
            Department save = getDepartmentRep.save(department);
            return ResponseEntity.ok(save);
        }
        return new ResponseEntity<>("Id doesnot exist", HttpStatus.NOT_ACCEPTABLE);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllEmployeeByDepartmentId(Long departmentId) {
        log.debug("get all employee by department id : {}", departmentId);
        if (getDepartmentRep.existsById(departmentId)) {
            List<Employees> getEmployeesByDepartmentId = employeesRepository.findByDepartmentId(departmentId);
            Department department = getDepartmentRep.findById(departmentId).get();
            GetEmployeesByDepartmentId employeesByDepartmentId = new GetEmployeesByDepartmentId(department.getName(),
                    department.getHead(),
                    department.getContact(),
                    getEmployeesByDepartmentId);
            return ResponseEntity.ok(employeesByDepartmentId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Please give valid id!!"));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllDepartmentWithEmployeeDetails(Integer page) {
        Page<Department> getDepartmentRepAll = getDepartmentRep.findAll(PageRequest.of(page, 5));
        List<GetAllDepartmentWithEmployeeDetails> collect = getDepartmentRepAll.stream().map(department -> {
            Long id = department.getId();
            String departmentName = department.getName();
            List<Employees> employees = employeesRepository.findByDepartmentId(id);
            GetAllDepartmentWithEmployeeDetails getAllDepartmentWithEmployeeDetails = new GetAllDepartmentWithEmployeeDetails(departmentName, employees.size(), employees);
            return getAllDepartmentWithEmployeeDetails;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @Transactional(readOnly = true)
    public ResponseEntity testapi() {
        List<Department> getDepartmentRepAll = getDepartmentRep.findAll();
        List<GetAllDepartmentWithEmployeeDetails> collect = getDepartmentRepAll.stream().map(department -> {
            Long id = department.getId();
            String departmentName = department.getName();
            List<Employees> employees = employeesRepository.findByDepartmentId(id);
            GetAllDepartmentWithEmployeeDetails getAllDepartmentWithEmployeeDetails = new GetAllDepartmentWithEmployeeDetails(departmentName, employees.size(), employees);
            return getAllDepartmentWithEmployeeDetails;
        }).filter(emp -> !emp.getEmployeesList().isEmpty()).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }


}