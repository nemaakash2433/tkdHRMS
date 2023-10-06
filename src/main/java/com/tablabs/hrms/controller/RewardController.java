package com.tablabs.hrms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Reward;
import com.tablabs.hrms.models.DTO.RewardDTO;
import com.tablabs.hrms.service.RewardService;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @PostMapping("/reward")
    public ResponseEntity<?> createReward(@RequestBody RewardDTO reward) throws JsonProcessingException {
        return rewardService.createReward(reward);
    }

    @PutMapping("/updateReward")
    public ResponseEntity<?> updateReward(@RequestBody Reward reward) throws JsonProcessingException {
        return rewardService.updateReward(reward);
    }

    @DeleteMapping("deleteByRewardId")
    public ResponseEntity<?> deleteReward(@RequestParam(name = "id") Long id) throws JsonProcessingException {
        return rewardService.deleteByRewardId(id);
    }

    @GetMapping("/getByRewardId")
    public ResponseEntity<?> getRewardById(@RequestParam(name = "id") Long id) throws JsonProcessingException {
        return rewardService.getByRewardId(id);
    }

    @GetMapping("/getAllRewardsWithEmployeeDetails")
    public ResponseEntity<?> getAllRewardAlongWithEmployee(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                           @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                                           @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) throws JsonProcessingException {
        return rewardService.getAllRewardsWithEmployeeDetails(page, size, sortBy, sortDir);
    }

    @GetMapping("/getRewardByEmployeeId")
    public ResponseEntity<?> getRewardByEmployeeId(@RequestParam(name = "employeeId") String emplId) throws JsonProcessingException {
        return rewardService.getRewardByEmployeeId(emplId);
    }

    @GetMapping("/getTopPerformanceEmployee")
    public ResponseEntity<?> getTopPerformanceEmployee(
            @RequestParam(name = "YYYY/month", defaultValue = "currentMonth", required = false) String yyyyMonth,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) throws JsonProcessingException {
        try {
            if (yyyyMonth.isEmpty() || yyyyMonth == null || yyyyMonth.equals("currentMonth")) {
                final String NEW_FORMAT = "yyyy/MM";
                SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
                String yyyyMonthIs = sdf.format(new Date());
                return rewardService.topPerformanceEmployee(yyyyMonthIs, page, size);
            }
            return rewardService.topPerformanceEmployee(yyyyMonth, page, size);
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

    @GetMapping("/getEmployeeOfTheMonthDetails")
    public ResponseEntity<?> getEmployeeOfTheMonthDetail(@RequestParam(name = "YYYY/month", defaultValue = "currentMonth", required = false) String yyyyMonth) throws JsonProcessingException {
        try {
            if (yyyyMonth.isEmpty() || yyyyMonth == null || yyyyMonth.equals("currentMonth")) {
                final String NEW_FORMAT = "yyyy/MM";
                SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
                String yyyyMonthIs = sdf.format(new Date());
                return rewardService.getDetailEmployeeOfTheMonth(yyyyMonthIs);
            }
            return rewardService.getDetailEmployeeOfTheMonth(yyyyMonth);
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

    @GetMapping("/getAllAwardsDetails")
    public ResponseEntity<?> getNoOfAwardsWithDepartmentAndEmployeeDetails(@RequestParam(name = "YYYY/month", defaultValue = "currentMonth", required = false) String yyyyMonth,
                                                                           @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                                           @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) throws JsonProcessingException {
        try {
            if (yyyyMonth.isEmpty() || yyyyMonth == null || yyyyMonth.equals("currentMonth")) {
                final String NEW_FORMAT = "yyyy/MM";
                SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
                String yyyyMonthIs = sdf.format(new Date());
                return rewardService.getNoOfAwardsWithDepartmentAndEmployeeDetails(yyyyMonthIs, page, size);
            }
            return rewardService.getNoOfAwardsWithDepartmentAndEmployeeDetails(yyyyMonth, page, size);
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

    @GetMapping("/getBestDepartment")
    public ResponseEntity<?> getBestDepartment() throws JsonProcessingException {
        return rewardService.findBestDepartment();
    }


}

