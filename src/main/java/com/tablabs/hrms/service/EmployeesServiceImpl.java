package com.tablabs.hrms.service;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.DTO.EmployeeDTO;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.response.GetEmployeesWithDepartmentResponse;
import com.tablabs.hrms.repository.DepartmentRepositroy;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Employees}.
 */
@Service
@Transactional
public class EmployeesServiceImpl {

    private final Logger log = LoggerFactory.getLogger(EmployeesServiceImpl.class);

    @Autowired
    private EmployeesRepository employeesRepository;


    @Autowired
    private DepartmentRepositroy departmentRepositroy;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> createEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO != null) {
            if (!employeesRepository.existsByEmployeeId(employeeDTO.getEmployeeId())) {
                employeeDTO.setImage("default.png");
                Employees employee = modelMapper.map(employeeDTO, Employees.class);
                Employees result = employeesRepository.save(employee);
                return ResponseEntity.ok().body(new JsonObjectFormat("Successfully retrieve data!!", true, result));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Employee is already reported!!"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public List<?> findAll(Integer page) {
        Page<Employees> result = employeesRepository.findAll(PageRequest.of(page, 5));
        return result.getContent();
    }

    public ResponseEntity<?> findOne(String empId) {

        if (employeesRepository.existsByEmployeeId(empId)) {
            Employees byEmployeeId = employeesRepository.findByEmployeeId(empId);
            if (byEmployeeId != null) {
                return ResponseEntity.ok(byEmployeeId);
            }
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> delete(String empId) {
        if (employeesRepository.existsByEmployeeId(empId)) {
            employeesRepository.deleteByEmployeeId(empId);
            return ResponseEntity.ok().body(new Message(true, "Successfully deleted!!"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Employee id doesn't exists in our record!!"));
    }

    public ResponseEntity updateEmployee(Employees employees, MultipartFile multipartFile) {
        employees.setImage(multipartFile.getOriginalFilename());
        Employees result = employeesRepository.save(employees);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> getEmployeeByIdWithDepartmentDetails(String empId) {

        Employees employees = employeesRepository.findByEmployeeId(empId);
        Long departmentId = employees.getDepartmentId();
        Department department = departmentRepositroy.findById(departmentId).get();

        GetEmployeesWithDepartmentResponse getEmployeesWithDepartmentResponse = new GetEmployeesWithDepartmentResponse(
                employees.getId(),
                employees.getFirstname(),
                employees.getLastname(),
                employees.getImage(),
                employees.getDesignation(),
                employees.getContact(),
                department);
        return ResponseEntity.ok().body(new JsonObjectFormat("Successfully retrieve", true, getEmployeesWithDepartmentResponse));
    }

    public ResponseEntity<?> getAllEmployeeWithDepartmentDetails() {
        log.debug("Get All Employee With DepartmentDetails");
        List<Employees> employees = employeesRepository.findAll();
        List<GetEmployeesWithDepartmentResponse> getEmployeesWithDepartmentResponses = employees.stream().map(employeesWithDepartment -> {
            Department department = departmentRepositroy.findById(employeesWithDepartment.getDepartmentId()).get();
            GetEmployeesWithDepartmentResponse employeesWithDepartmentResponse = new GetEmployeesWithDepartmentResponse(employeesWithDepartment.getId(),
                    employeesWithDepartment.getFirstname(),
                    employeesWithDepartment.getLastname(),
                    employeesWithDepartment.getImage(),
                    employeesWithDepartment.getDesignation(),
                    employeesWithDepartment.getContact(),
                    department);
            return employeesWithDepartmentResponse;

        }).collect(Collectors.toList());
        return ResponseEntity.ok(new JsonObjectFormat("Successfully retrieve data", true, getEmployeesWithDepartmentResponses));
    }
}
