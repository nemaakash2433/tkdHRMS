package com.tablabs.hrms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tablabs.hrms.models.response.LeaveDashboard;
import com.tablabs.hrms.models.response.LeaveRequestsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Leaves;
import com.tablabs.hrms.enums.EmployeeLocation;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.repository.LeavesRepository;
import com.tablabs.hrms.util.JsonObjectFormat;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class LeavesController {

    @Autowired
    LeavesRepository leavesRepository;

    @Autowired
    EmployeesRepository employeesRepository;

    // GetById
    @GetMapping("/Leaves/getById")
    public ResponseEntity<?> getLeavesById(@RequestParam("id") Long id) throws JsonProcessingException {
        try {
            Optional<Leaves> leaves = leavesRepository.findById(id);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves fetch successfully");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(leaves.get());
            return ResponseEntity.ok().body(leaves.get());

        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves can not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    // Save
    @PostMapping("/Leaves/save")
    public ResponseEntity<?> saveLeaves(@RequestBody Leaves content) throws JsonProcessingException {

        try {

            Leaves content1 = leavesRepository.save(content);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves saved successfully");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(content1);
            return ResponseEntity.ok().body(jsonobjectFormat);

        } catch (Exception e) {
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves not saved successfully " + e.getMessage());
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    // Delete
    @DeleteMapping("/Leaves/deleteById")
    public ResponseEntity<?> deleteLeavesById(@RequestParam("id") Long id) {
        Optional<Leaves> service = leavesRepository.findById(id);
        if (service.isPresent()) {
            leavesRepository.deleteById(service.get().getId());
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves Deleted successfully");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData("");
            return ResponseEntity.ok().body(jsonobjectFormat);
        } else {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Unable to Delete Leaves");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            return ResponseEntity.ok().body(jsonobjectFormat);
        }
    }

    // This api is used to update particular Scheme by Id in database
    @PutMapping("/Leaves/update")
    public ResponseEntity<?> updateLeaves(@RequestBody Leaves address1) throws JsonProcessingException {

        try {
            Optional<Leaves> address = leavesRepository.findById(address1.getId());

            if (address1.getStatus() != null) {
                address.get().setStatus(address1.getStatus());
            }
            if (address1.getDateApplied() != null) {
                address.get().setDateApplied(address1.getDateApplied());
            }
            if (address1.getReason() != null) {
                address.get().setReason(address1.getReason());
            }
            if (address1.getEmployeeId() != null) {
                address.get().setEmployeeId(address1.getEmployeeId());
            }
            if (address1.getEndDate() != null) {
                address.get().setEndDate(address1.getEndDate());
            }
            if (address1.getNumberOfDays() != null) {
                address.get().setNumberOfDays(address1.getNumberOfDays());
            }
            if (address1.getStartDate() != null) {
                address.get().setStartDate(address1.getStartDate());
            }
            Leaves AddressUser1 = leavesRepository.save(address.get());
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves updated successfully");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(AddressUser1);
            return ResponseEntity.ok().body(jsonobjectFormat);

        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves not found " + e.getMessage());
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData(address1);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);

        }

    }

    @GetMapping("/Leaves/getAll")
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        try {
            List<Leaves> leaves = leavesRepository.findAll();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves fetch successfully");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(leaves);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves can not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    @GetMapping("/getAllLeavesWithDescAndPages")
    public ResponseEntity<?> getAllLeavesWithDesc(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "size", required = false) Integer size) throws JsonProcessingException {
        if (size != null) {
        } else {
            size = 10;
        }
        if (page != null) {
        } else {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Leaves> leavesFromDb = leavesRepository.findAllByOrderByIdDesc(pageable);
            List<LeaveRequestsDto> leaves = new ArrayList<>();

            List<Employees> employees = employeesRepository.findAllByEmployeeid(
                    (leavesFromDb).stream().map(Leaves::getEmployeeId).collect(Collectors.toList()));
            (leavesFromDb).stream().forEach(leave -> {
                Optional<Employees> employee = employees.stream()
                        .filter(emp -> emp.getEmployeeId().equals(String.valueOf(leave.getEmployeeId()))).findFirst();
                LeaveRequestsDto leaveRequestDto = new LeaveRequestsDto();
                leaveRequestDto.setId(leave.getId());
                leaveRequestDto.setFirstName(employee.get().getFirstname());
                leaveRequestDto.setLastName((employee.get().getLastname()));
                leaveRequestDto.setEmpImage(employee.get().getImage());
                leaveRequestDto.setFromDate(leave.getStartDate());
                leaveRequestDto.setToDate(leave.getEndDate());
                leaveRequestDto.setReason(leave.getReason());
                leaveRequestDto.setLeaveType(leave.getLeaveType());
                leaveRequestDto.setStatus(leave.getStatus());
                leaves.add(leaveRequestDto);
            });
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves fetch successfully");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(leaves);
            ObjectMapper Obj = new ObjectMapper();
            return ResponseEntity.ok().body(leaves);
        } catch (Exception e) {
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves can not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }

    }

    @GetMapping("/searchLeaves")
    public ResponseEntity<String> searchLeaves(@RequestParam String keyword) throws JsonProcessingException {
        try {
            List<Leaves> employees = leavesRepository.findByStatusOrLeaveTypeOrReasonContainingIgnoreCase(keyword);
            if(employees.isEmpty()){
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Leaves are not be Found!");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("Leaves fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(employees);

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

    @GetMapping("/Leaves/Dashborad")
    public ResponseEntity<?> getLeavesDahborad() throws JsonProcessingException {
        try {
            List<Leaves> leavesFromDb = leavesRepository.findAll();

            List<Employees> employees = employeesRepository.findAll();

            Long onsiteCount = employees.stream().filter(e -> e.getEmployeeLocation() == EmployeeLocation.ONSITE)
                    .count();

            Long remoteCount = employees.stream().filter(e -> e.getEmployeeLocation() == EmployeeLocation.REMOTE)
                    .count();
            Long totalEmployeesCount = employees.stream().count();
            Long leavesCount = leavesFromDb.stream().filter(leave -> (leave.getStartDate().toInstant().isBefore(new Date().toInstant()))
                            && leave.getEndDate().toInstant().isAfter((new Date()).toInstant())
                            || (leave.getStartDate()==(new Date()) || leave.getEndDate()==(new Date())))
                    .count();

            LeaveDashboard leaveRequestDto = new LeaveDashboard();
            leaveRequestDto.setOnsite(onsiteCount);
            leaveRequestDto.setRemote(remoteCount);
            leaveRequestDto.setLeavesCount(leavesCount);
            leaveRequestDto.setTotalEmpCount(totalEmployeesCount);

            return ResponseEntity.ok().body(leaveRequestDto);

        } catch (Exception e) {
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Leaves can not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);

        }

    }
}