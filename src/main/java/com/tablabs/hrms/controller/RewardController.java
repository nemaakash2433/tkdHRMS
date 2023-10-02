package com.tablabs.hrms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tablabs.hrms.entity.Reward;
import com.tablabs.hrms.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/api")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @PostMapping("/reward")
    public ResponseEntity<?> createReward(@RequestBody Reward reward) throws JsonProcessingException {
        return rewardService.createReward(reward);
    }

    @PutMapping("/updateReward")
    public ResponseEntity<?> updateReward(@RequestBody Reward reward) throws JsonProcessingException {
        return rewardService.updateReward(reward);
    }

    @DeleteMapping("deleteByRewardId")
    public ResponseEntity<?> deleteReward(@RequestParam(name = "id")Long id) throws JsonProcessingException {
        return rewardService.deleteByRewardId(id);
    }

    @GetMapping("/getByRewardId")
    public ResponseEntity<?> getRewardById(@RequestParam(name = "id")Long id) throws JsonProcessingException {
        return rewardService.getByRewardId(id);
    }

    @GetMapping("/getAllRewardsWithEmployeeDetails")
    public ResponseEntity<?> getAllRewardAlongWithEmployee(@RequestParam(name = "page",defaultValue = "0",required = false)Integer page,
                                                           @RequestParam(name = "size",defaultValue = "10",required = false)Integer size,
                                                           @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) throws JsonProcessingException {
        return rewardService.getAllRewardsWithEmployeeDetails(page, size, sortBy, sortDir);
    }

    @GetMapping("/getRewardByEmployeeId")
    public ResponseEntity<?> getRewardByEmployeeId(@RequestParam(name="employeeId")String emplId) throws JsonProcessingException {
        return rewardService.getRewardByEmployeeId(emplId);
    }
}

