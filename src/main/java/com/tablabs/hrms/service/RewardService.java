package com.tablabs.hrms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Reward;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.response.GetAllRewardWithEmployeeDetailResponse;
import com.tablabs.hrms.models.response.ResponseWithPageDetails;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.repository.RewardRepository;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private EmployeesRepository employeesRepository;

    public ResponseEntity<?> createReward(Reward reward) throws JsonProcessingException {
        try {
            if(!employeesRepository.existsByEmployeeId(reward.getEmployeeId())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"Please give valid employee id!!"));
            }
            Reward result = rewardRepository.save(reward);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully save Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(result);
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

    public ResponseEntity<?> updateReward(Reward reward) throws JsonProcessingException {
        try {
            if(!rewardRepository.existsById(reward.getId())){
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Reward id is not valid!!");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            Reward oldRewardData = rewardRepository.findById(reward.getId()).get();
            if(!oldRewardData.getEmployeeId().equals(reward.getEmployeeId())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"Employee Id cannot be changed once assigned!!"));
            }
            Reward result = rewardRepository.save(reward);

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully update Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(result);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }catch (Exception e){
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity<?> getByRewardId(Long id) throws JsonProcessingException {
        try{
            if (!rewardRepository.existsById(id)) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Reward id not exists!!");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            Reward reward = rewardRepository.findById(id).get();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully Fetch Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(reward);
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
    public ResponseEntity<?> deleteByRewardId(Long id) throws JsonProcessingException {
        try{
            if (!rewardRepository.existsById(id)) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Reward id not exists!!");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            rewardRepository.deleteById(id);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully Deleted!!");
            jsonobjectFormat.setSuccess(true);
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

    public ResponseEntity<?> getAllRewardsWithEmployeeDetails(Integer page,Integer size,String sortBy, String sortDir) throws JsonProcessingException {
        try{
            Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Page<Reward> contant = rewardRepository.findAll(PageRequest.of(page, size, sort));
            List<GetAllRewardWithEmployeeDetailResponse> collect = contant.getContent().stream().map(reward -> {
                String employeeId = reward.getEmployeeId();
                Employees employee = employeesRepository.findByEmployeeId(employeeId);
                GetAllRewardWithEmployeeDetailResponse withEmployeeDetailResponse = new GetAllRewardWithEmployeeDetailResponse(reward.getAward(),
                        reward.getRating(), employee);
                return withEmployeeDetailResponse;
            }).collect(Collectors.toList());
            //page set
            ResponseWithPageDetails responseWithPageDetails=new ResponseWithPageDetails();
            responseWithPageDetails.setDetailsWithPage(collect);
            responseWithPageDetails.setPageNumber(contant.getNumber());
            responseWithPageDetails.setPageSize(contant.getSize());
            responseWithPageDetails.setTotalElements(contant.getTotalElements());
            responseWithPageDetails.setLastPage(contant.isLast());
            responseWithPageDetails.setTotalPages(contant.getTotalPages());

            //response set
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully fetch the data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(responseWithPageDetails);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }catch (Exception e){
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

    public ResponseEntity<?> getRewardByEmployeeId(String emplId) throws JsonProcessingException {
        try{
            if(!employeesRepository.existsByEmployeeId(emplId)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"This employee is not our record!!"));
            } else if (!rewardRepository.existsByEmployeeId(emplId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"There is no reward with this employee!!"));
            }
            Reward byEmployeeId = rewardRepository.findByEmployeeId(emplId);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully get the data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(byEmployeeId);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }catch (Exception e){
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
