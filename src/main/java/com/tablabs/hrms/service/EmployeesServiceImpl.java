package com.tablabs.hrms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.DTO.EmployeeDTO;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.response.EmployeesWithDepartmentResponse;
import com.tablabs.hrms.models.response.GetEmployeesWithDepartmentResponse;
import com.tablabs.hrms.models.response.ResponseWithPageDetails;
import com.tablabs.hrms.repository.DepartmentRepositroy;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Employees}.
 */
@Service
public class EmployeesServiceImpl {

    private final Logger log = LoggerFactory.getLogger(EmployeesServiceImpl.class);

    @Autowired
    private EmployeesRepository employeesRepository;


    @Autowired
    private DepartmentRepositroy departmentRepositroy;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<?> createEmployee(Employees employees) throws JsonProcessingException {
        try {
            log.info("Request for create employee : {}", employees.getId());
            if (employees != null) {
                if (!employeesRepository.existsByEmployeeId(employees.getEmployeeId())) {
                    Employees result = employeesRepository.save(employees);
                    JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                    jsonobjectFormat.setMessage("Employee saved successfully");
                    jsonobjectFormat.setSuccess(true);
                    jsonobjectFormat.setData(result);
                    ObjectMapper Obj = new ObjectMapper();
                    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(jsonobjectFormat);
                    return ResponseEntity.ok().body(customExceptionStr);
                }
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Employee already present in our database");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Enter all details...");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }


    public ResponseEntity<?> findAll(Integer page, Integer size) throws JsonProcessingException {
        try {
            Page<Employees> result = employeesRepository.findAll(PageRequest.of(page, size));
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully fetch Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(result);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("no data found!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity<?> getEmployeeById(String empId) throws JsonProcessingException {
        try {
            if (employeesRepository.existsByEmployeeId(empId)) {
                Employees byEmployeeId = employeesRepository.findByEmployeeId(empId);
                if (byEmployeeId != null) {
                    EmployeeDTO employeeDTO = new EmployeeDTO(byEmployeeId.getEmployeeId(),
                            byEmployeeId.getFirstname(), byEmployeeId.getLastname(),
                            byEmployeeId.getImage(), byEmployeeId.getEmail(),byEmployeeId.getDesignation(),
                            byEmployeeId.getContact(), byEmployeeId.getEmployeeLocation());
                    JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                    jsonobjectFormat.setMessage("Successfully fetch data!!");
                    jsonobjectFormat.setSuccess(true);
                    jsonobjectFormat.setData(employeeDTO);
                    ObjectMapper Obj = new ObjectMapper();
                    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                    return ResponseEntity.ok().body(customExceptionStr);
                }
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Employee id doesn't exists in our record!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("no data found!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity<?> getEmployeeByIdWithBoardingDetails(String emplId) throws JsonProcessingException {
        try {
            if (employeesRepository.existsByEmployeeId(emplId)) {
                Employees byEmployeeId = employeesRepository.findByEmployeeId(emplId);
                if (byEmployeeId != null) {
                    JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                    jsonobjectFormat.setMessage("Successfully fetch data!!");
                    jsonobjectFormat.setSuccess(true);
                    jsonobjectFormat.setData(byEmployeeId);
                    ObjectMapper Obj = new ObjectMapper();
                    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                    return ResponseEntity.ok().body(customExceptionStr);
                }
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Employee id doesn't exists in our record!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity<?> delete(String empId) throws JsonProcessingException {
        try {
            if (employeesRepository.existsByEmployeeId(empId)) {
                Employees byEmployeeId = employeesRepository.findByEmployeeId(empId);
                byEmployeeId.setActive(false);
                employeesRepository.save(byEmployeeId);
                return ResponseEntity.ok().body(new Message(true, "Successfully deleted!!"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Employee id doesn't exists in our record!!"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity updateEmployee(Employees employees) throws JsonProcessingException {
        try {

            String employeeId = employees.getEmployeeId();
            if (!employeesRepository.existsByEmployeeId(employeeId)) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Given employeeId is not valid for update!!");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }

            Employees byEmployeeId = employeesRepository.findByEmployeeId(employeeId);

            byEmployeeId.setFirstname(employees.getFirstname());
            byEmployeeId.setLastname(employees.getLastname());
            byEmployeeId.setContact(employees.getContact());
            byEmployeeId.setImage(employees.getImage());
            byEmployeeId.setEmployeeLocation(employees.getEmployeeLocation());
            byEmployeeId.setEmail(employees.getEmail());
            byEmployeeId.setDesignation(employees.getDesignation());

            Employees result = employeesRepository.save(byEmployeeId);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully updated!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(result);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity<?> getEmployeeByIdWithDepartmentDetails(String empId) throws JsonProcessingException {
        try {
            if (!employeesRepository.existsByEmployeeId(empId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Employee id doesn't exists our record!!"));
            }
            Employees employees = employeesRepository.findByEmployeeId(empId);
            Long departmentId = employees.getDepartmentId();
            Department department = departmentRepositroy.findById(departmentId).get();

            GetEmployeesWithDepartmentResponse employeesWithDepartmentResponse = new GetEmployeesWithDepartmentResponse();
            employeesWithDepartmentResponse.setLastName(employees.getLastname());
            employeesWithDepartmentResponse.setFirstName(employees.getFirstname());
            employeesWithDepartmentResponse.setContact(employees.getContact());
            employeesWithDepartmentResponse.setDepartment(department);
            employeesWithDepartmentResponse.setImage(employees.getImage());
            employeesWithDepartmentResponse.setEmail(employees.getEmail());
            employeesWithDepartmentResponse.setDesignation(employees.getDesignation());
            employeesWithDepartmentResponse.setEmployeeId(employees.getEmployeeId());

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Data fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(employeesWithDepartmentResponse);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }


    public ResponseEntity<String> getAllEmployeeWithDepartment() throws JsonProcessingException {
        try {
            log.debug("Get All Employee With DepartmentDetails");
            List<Employees> employees = employeesRepository.findAll();
            List<GetEmployeesWithDepartmentResponse> getEmployeesWithDepartmentResponses = employees.stream()
                    .map(employeesWithDepartment -> {
                        Department department = departmentRepositroy.findById(employeesWithDepartment.getDepartmentId())
                                .get();
                        GetEmployeesWithDepartmentResponse employeesWithDepartmentResponse = new GetEmployeesWithDepartmentResponse();
                        employeesWithDepartmentResponse.setFirstName(employeesWithDepartment.getFirstname());
                        employeesWithDepartmentResponse.setLastName(employeesWithDepartment.getLastname());
                        employeesWithDepartmentResponse.setEmail(employeesWithDepartmentResponse.getEmail());
                        employeesWithDepartmentResponse.setContact(employeesWithDepartment.getContact());
                        employeesWithDepartmentResponse.setDepartment(department);
                        employeesWithDepartmentResponse.setImage(employeesWithDepartment.getImage());
                        employeesWithDepartmentResponse.setDesignation(employeesWithDepartment.getDesignation());
                        employeesWithDepartmentResponse.setEmployeeId(employeesWithDepartment.getEmployeeId());
                        return employeesWithDepartmentResponse;

                    }).collect(Collectors.toList());
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Data fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(getEmployeesWithDepartmentResponses);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity<String> searchEmployees(String keyword) throws JsonProcessingException {
        try {
            List<Employees> employees = employeesRepository.findByKeyword(keyword);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("Employees fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(employees);

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("Employees are not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity<?> getAllEmployeesWithDesc(Integer page, Integer size) throws JsonProcessingException {
        try {

            Pageable pageable = PageRequest.of(page, size);
            log.debug("REST request to get all Attendances");
            Page<Employees> allByOrderByIdDesc = employeesRepository.findAllByOrderByIdDesc(pageable);
            List<EmployeesWithDepartmentResponse> employeesWithDepartmentResponses = allByOrderByIdDesc.stream().map(employees -> {
                Long departmentId = employees.getDepartmentId();
                EmployeesWithDepartmentResponse withDepartmentResponse = new EmployeesWithDepartmentResponse();
                if (departmentRepositroy.existsById(departmentId)) {
                    withDepartmentResponse.setEmployeeDetails(employees);
                    withDepartmentResponse.setDepartmentDetails(departmentRepositroy.findById(departmentId).get());
                    return withDepartmentResponse;
                }
                return withDepartmentResponse;
            }).collect(Collectors.toList());

            //set page
            ResponseWithPageDetails responseWithPageDetails = new ResponseWithPageDetails();
            responseWithPageDetails.setDetailsWithPage(employeesWithDepartmentResponses);
            responseWithPageDetails.setPageNumber(allByOrderByIdDesc.getNumber());
            responseWithPageDetails.setPageSize(allByOrderByIdDesc.getSize());
            responseWithPageDetails.setTotalElements(allByOrderByIdDesc.getTotalElements());
            responseWithPageDetails.setLastPage(allByOrderByIdDesc.isLast());
            responseWithPageDetails.setTotalPages(allByOrderByIdDesc.getTotalPages());

            //set response
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully fetch Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(responseWithPageDetails);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

}
