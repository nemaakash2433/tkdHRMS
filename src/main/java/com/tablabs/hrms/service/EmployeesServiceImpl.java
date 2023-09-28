package com.tablabs.hrms.service;

import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.responseDTO.EmployeeResponse;
import com.tablabs.hrms.models.responseDTO.GetEmployeesWithDepartmentResponse;
import com.tablabs.hrms.models.responseDTO.GetMessageAllEmployeeWithDepartmentResponse;
import com.tablabs.hrms.models.responseDTO.GetMessageEmployeeWithDepartment;
import com.tablabs.hrms.repository.DepartmentRepositroy;
import com.tablabs.hrms.repository.EmployeesRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Employees}.
 */
@Service
@Transactional
public class EmployeesServiceImpl {

    private final Logger log = LoggerFactory.getLogger(EmployeesServiceImpl.class);

    private static final String ENTITY_NAME = "employee";

    @Autowired
    private EmployeesRepository employeesRepository;


    @Autowired
    private DepartmentRepositroy departmentRepositroy;
    @Autowired
    private DepartmentImp departmentImp;

    public ResponseEntity<?> createEmployee(Employees employees, MultipartFile multipartFile) {
        log.info("Request for create employee : {}",employees.getId());
        if(employees!=null) {
            if(departmentRepositroy.existsById(employees.getDepartmentId())) {
                employees.setImage(multipartFile.getOriginalFilename());
                Employees result = employeesRepository.save(employees);
                EmployeeResponse employeeResponse = new EmployeeResponse();
                employeeResponse.setMessage("Successfully retrieve data");
                employeeResponse.setResult(true);
                employeeResponse.setData(result);
                return ResponseEntity.ok().body(employeeResponse);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"Department Id doesn't exists our record!!"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    public List<?> findAll(Integer page) {
        Page<Employees> result = employeesRepository.findAll(PageRequest.of(page,5));
        return result.getContent();
    }

    public ResponseEntity<?> findOne(String empId) {

        if(employeesRepository.existsByEmployeeId(empId)) {
            Employees byEmployeeId = employeesRepository.findByEmployeeId(empId);
            if (byEmployeeId!=null) {
                return ResponseEntity.ok(byEmployeeId);
            }
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> delete(Long id) {
        if (employeesRepository.existsById(id)) {
            employeesRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity updateEmployee(Employees employees,MultipartFile multipartFile) {
        employees.setImage(multipartFile.getOriginalFilename());
        Employees result = employeesRepository.save(employees);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> getEmployeeByIdWithDepartmentDetails(String empId){

        Employees employees = employeesRepository.findByEmployeeId(empId);
        Long departmentId = employees.getDepartmentId();
        Department department = departmentRepositroy.findById(departmentId).get();

        GetEmployeesWithDepartmentResponse getEmployeesWithDepartmentResponse=new GetEmployeesWithDepartmentResponse(
                employees.getId(),
                employees.getFirstname(),
                employees.getLastname(),
                employees.getImage(),
                employees.getDesignation(),
                employees.getContact(),
                department);
        return ResponseEntity.ok().body(new GetMessageEmployeeWithDepartment(true,"Successfully retrieve",getEmployeesWithDepartmentResponse));
    }
    public ResponseEntity<?> getAllEmployeeWithDepartmentDetails(){
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
        return ResponseEntity.ok(new GetMessageAllEmployeeWithDepartmentResponse(true,"Successfully retrieve data",getEmployeesWithDepartmentResponses));
    }
}
