package com.tablabs.hrms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.response.GetAllDepartmentWithEmployeeDetails;
import com.tablabs.hrms.models.response.GetEmployeesByDepartmentId;
import com.tablabs.hrms.models.response.ResponseWithPageDetails;
import com.tablabs.hrms.repository.DepartmentRepositroy;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class DepartmentImp {
    private final Logger log = LoggerFactory.getLogger(DepartmentImp.class);
    @Autowired
    private DepartmentRepositroy departmentRepositroy;
    @Autowired
    private EmployeesRepository employeesRepository;

    public ResponseEntity<?> createDepartment(Department department) {
        log.debug("Request to create department : {}", department.getName());
        if (departmentRepositroy.existsByName(department.getName())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Message(false, "Department name is already reported!!"));
        }
        Department save = departmentRepositroy.save(department);
        return ResponseEntity.ok(new JsonObjectFormat("Successfully saved data in record!!", true, save));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getDepartmentById(Long departmentId) {
        Department department = departmentRepositroy.findById(departmentId).get();
        if (department != null) {
            return ResponseEntity.ok(new JsonObjectFormat("Successfully retrieve data!!", true, department));
        }
        return new ResponseEntity("There is no data by this id!!", HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllDepartment(Integer page, Integer size, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Department> getDepartmentRepAll = departmentRepositroy.findAll(PageRequest.of(page, size, sort));

        List<Department> content = getDepartmentRepAll.getContent();
        ResponseWithPageDetails departmentWithPageResponse = new ResponseWithPageDetails();
        departmentWithPageResponse.setDetailsWithPage(content);
        departmentWithPageResponse.setPageNumber(getDepartmentRepAll.getNumber());
        departmentWithPageResponse.setPageSize(getDepartmentRepAll.getSize());
        departmentWithPageResponse.setTotalElements(getDepartmentRepAll.getTotalElements());
        departmentWithPageResponse.setLastPage(getDepartmentRepAll.isLast());
        departmentWithPageResponse.setTotalPages(getDepartmentRepAll.getTotalPages());


        return ResponseEntity.ok(new JsonObjectFormat("Successfully retrieve the data!!", true, departmentWithPageResponse));
    }

    public ResponseEntity<?> deleteDepartment(Long departmentId) {
        try {
            log.debug("Request for delete department : {}", departmentId);
            if (departmentRepositroy.existsById(departmentId)) {
                departmentRepositroy.deleteById(departmentId);
                List<Employees> byDepartmentId = employeesRepository.findByDepartmentId(departmentId);
                if (!byDepartmentId.isEmpty()) {
                    byDepartmentId.stream().forEach(employees -> employeesRepository.deleteById(employees.getId()));

                }
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("department Deleted successfully");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData("");
                return ResponseEntity.ok().body(jsonobjectFormat);

            } else {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("enter valid id..");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                return ResponseEntity.ok().body(jsonobjectFormat);

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("record not Deleted successfully");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            return ResponseEntity.ok().body(jsonobjectFormat);
        }
    }


    public ResponseEntity<?> updateDepartment(Department department) throws JsonProcessingException {
        try {
            if (!departmentRepositroy.existsById(department.getId())) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Department id is not valid!!");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            Optional<Department> getDepartmentRepById = departmentRepositroy.findById(department.getId());
            if (getDepartmentRepById.isPresent()) {
                Department save = departmentRepositroy.save(department);
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Department Updated successfully");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(save);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }

            return new ResponseEntity<>("Data is not present", HttpStatus.NOT_ACCEPTABLE);
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

    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllEmployeeByDepartmentId(Long departmentId) throws JsonProcessingException {
        try {
            log.debug("get all employee by department id : {}", departmentId);

            if (departmentRepositroy.existsById(departmentId)) {
                List<Employees> getEmployeesByDepartmentId = employeesRepository.findByDepartmentId(departmentId);
                Department department = departmentRepositroy.findById(departmentId).get();
                GetEmployeesByDepartmentId employeesByDepartmentId = new GetEmployeesByDepartmentId(
                        department.getName(), department.getHead(), department.getContact(),
                        getEmployeesByDepartmentId);
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Department fetch successfully");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(getEmployeesByDepartmentId);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Enter valid Department id...");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("department can not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

//    @Transactional(readOnly = true)//its shows empty employees detail in department wise
//    public ResponseEntity<?> getAllDepartmentWithEmployeeDetails(Integer page) {
//        Page<Department> getDepartmentRepAll = getDepartmentRep.findAll(PageRequest.of(page, 5));
//        List<GetAllDepartmentWithEmployeeDetails> collect = getDepartmentRepAll.stream().map(department -> {
//            Long id = department.getId();
//            String departmentName = department.getName();
//            List<Employees> employees = employeesRepository.findByDepartmentId(id);
//            GetAllDepartmentWithEmployeeDetails getAllDepartmentWithEmployeeDetails = new GetAllDepartmentWithEmployeeDetails(departmentName, employees.size(), employees);
//            return getAllDepartmentWithEmployeeDetails;
//        }).collect(Collectors.toList());
//
//        DepartmentWithPageResponse departmentWithPageResponse=new DepartmentWithPageResponse();
//        departmentWithPageResponse.setDetailsDepartmentWithEmployee(collect);
//        departmentWithPageResponse.setPageNumber(getDepartmentRepAll.getNumber());
//        departmentWithPageResponse.setTotalElements(getDepartmentRepAll.getTotalElements()+1);
//        departmentWithPageResponse.setPageSize(getDepartmentRepAll.getSize());
//        departmentWithPageResponse.setLastPage(getDepartmentRepAll.isLast());
//        departmentWithPageResponse.setTotalPages(getDepartmentRepAll.getTotalPages());
//        return ResponseEntity.ok(departmentWithPageResponse);
//    }

    @Transactional(readOnly = true)//not show empty employees detail in department wise
    public ResponseEntity getAllDepartmentWithEmployeeDetails(Integer page, Integer size, String sortBy, String sortDir) throws JsonProcessingException {
        try {
            Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Page<Department> getDepartmentRepAll = departmentRepositroy.findAll(PageRequest.of(page, size, sort));

            List<GetAllDepartmentWithEmployeeDetails> collect = getDepartmentRepAll.stream().map(department -> {
                Long id = department.getId();
                String departmentName = department.getName();
                List<Employees> employees = employeesRepository.findByDepartmentId(id);
                GetAllDepartmentWithEmployeeDetails getAllDepartmentWithEmployeeDetails = new GetAllDepartmentWithEmployeeDetails(departmentName, employees.size(), employees);
                return getAllDepartmentWithEmployeeDetails;
            }).filter(emp -> !emp.getEmployeesList().isEmpty()).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                //page set with response
                ResponseWithPageDetails departmentWithPageResponse = new ResponseWithPageDetails();
                departmentWithPageResponse.setDetailsWithPage(collect);
                departmentWithPageResponse.setPageNumber(getDepartmentRepAll.getNumber());
                departmentWithPageResponse.setPageSize(getDepartmentRepAll.getSize());
                departmentWithPageResponse.setTotalElements(getDepartmentRepAll.getTotalElements());
                departmentWithPageResponse.setLastPage(getDepartmentRepAll.isLast());
                departmentWithPageResponse.setTotalPages(getDepartmentRepAll.getTotalPages());

                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Successfully fetch Department Data");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(departmentWithPageResponse);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("There is no employee associate any of the department");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
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

    public ResponseEntity<String> searchDepartment(String keyword) throws JsonProcessingException {
        try {
            List<Department> employees = departmentRepositroy.findByNameContainingIgnoreCaseOrEmployeeIdContainingIgnoreCaseOrContactContainingIgnoreCase(keyword);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("Department fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(employees);

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Department are not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }


}
