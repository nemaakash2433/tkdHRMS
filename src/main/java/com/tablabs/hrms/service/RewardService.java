package com.tablabs.hrms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Department;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Reward;
import com.tablabs.hrms.models.DTO.RewardDTO;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.response.*;
import com.tablabs.hrms.repository.DepartmentRepositroy;
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

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private DepartmentRepositroy departmentRepositroy;

    public ResponseEntity<?> createReward(RewardDTO rewardDTO) throws JsonProcessingException {
        try {

            Reward reward = new Reward();
            if (!employeesRepository.existsByEmployeeId(rewardDTO.getEmployeeId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Please give valid employee id!!"));
            }
            final String NEW_FORMAT = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
            String format = sdf.format(new Date());
            reward.setOnWhichDate(format);
            reward.setEmployeeId(rewardDTO.getEmployeeId());
            reward.setAward(rewardDTO.getAward());
            reward.setEmployeeOfTheMonth(rewardDTO.isEmployeeOfTheMonth());
            if (rewardDTO.isEmployeeOfTheMonth()) {
                final String alreadyHave = "yyyy/MM";
                SimpleDateFormat sdf1 = new SimpleDateFormat(alreadyHave);
                String format1 = sdf1.format(new Date());
                List<Reward> byMonthAndDate = rewardRepository.findByOnWhichDateStartingWith(format1);
                if (!byMonthAndDate.isEmpty()) {
                    List<Reward> collect = byMonthAndDate.stream().filter(reward1 -> reward1.isEmployeeOfTheMonth()).collect(Collectors.toList());
                    if (!collect.isEmpty()) {
                        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                        jsonobjectFormat.setMessage("Already have an employee of the month!!");
                        jsonobjectFormat.setSuccess(false);
                        jsonobjectFormat.setData(collect);
                        ObjectMapper Obj = new ObjectMapper();
                        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                        return ResponseEntity.ok().body(customExceptionStr);
                    }
                }
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
            if (reward.isEmployeeOfTheMonth()) {
                final String alreadyHave = "yyyy/MM";
                SimpleDateFormat sdf1 = new SimpleDateFormat(alreadyHave);
                String format1 = sdf1.format(new Date());
                List<Reward> byMonthAndDate = rewardRepository.findByOnWhichDateStartingWith(format1);
                if (!byMonthAndDate.isEmpty()) {
                    List<Reward> collect = byMonthAndDate.stream().filter(reward1 -> reward1.isEmployeeOfTheMonth()).collect(Collectors.toList());
                    if (!collect.isEmpty()) {
                        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                        jsonobjectFormat.setMessage("Already have an employee of the month!!");
                        jsonobjectFormat.setSuccess(false);
                        jsonobjectFormat.setData(collect);
                        ObjectMapper Obj = new ObjectMapper();
                        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                        return ResponseEntity.ok().body(customExceptionStr);
                    }
                }
            }
            if (!rewardRepository.existsById(reward.getId())) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Reward id is not valid!!");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            Reward oldRewardData = rewardRepository.findById(reward.getId()).get();
            if (!oldRewardData.getEmployeeId().equals(reward.getEmployeeId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Employee Id cannot be changed once assigned!!"));
            }
            if (reward.getAward() != null) {
                oldRewardData.setAward(reward.getAward());
            }
            if (reward.getOnWhichDate() != null) {
                final String NEW_FORMAT = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
                String format = sdf.format(new Date());
                oldRewardData.setOnWhichDate(format);
            }
            oldRewardData.setEmployeeOfTheMonth(reward.isEmployeeOfTheMonth());
            Reward result = rewardRepository.save(oldRewardData);

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully update Data!!");
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

    public ResponseEntity<?> getByRewardId(Long id) throws JsonProcessingException {
        try {
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
        try {
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

    public ResponseEntity<?> getAllRewardsWithEmployeeDetails(Integer page, Integer size, String sortBy, String sortDir) throws JsonProcessingException {
        try {
            Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Page<Reward> contant = rewardRepository.findAll(PageRequest.of(page, size, sort));
            List<GetAllRewardWithEmployeeDetailResponse> collect = contant.getContent().stream().map(reward -> {
                String employeeId = reward.getEmployeeId();
                Employees employee = employeesRepository.findByEmployeeId(employeeId);
                GetAllRewardWithEmployeeDetailResponse withEmployeeDetailResponse = new GetAllRewardWithEmployeeDetailResponse(reward.getAward(), employee);
                return withEmployeeDetailResponse;
            }).collect(Collectors.toList());
            //page set
            ResponseWithPageDetails responseWithPageDetails = new ResponseWithPageDetails();
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

    public ResponseEntity<?> getRewardByEmployeeId(String emplId) throws JsonProcessingException {
        try {
            if (!employeesRepository.existsByEmployeeId(emplId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "This employee is not our record!!"));
            } else if (!rewardRepository.existsByEmployeeId(emplId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "There is no reward with this employee!!"));
            }
            Reward byEmployeeId = rewardRepository.findByEmployeeId(emplId);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully get the data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(byEmployeeId);
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

    public ResponseEntity<?> topPerformanceEmployee(String yyyyMonth, Integer page, Integer size) throws JsonProcessingException {
        try {
//            Page<Reward> top5MaxRating = rewardRepository.findByOrderByRatingDesc(PageRequest.of(page, size));
            Page<Reward> top5MaxRating = rewardRepository.findByOnWhichDateStartingWithOrderByOnWhichDateDesc(yyyyMonth, PageRequest.of(page, size));
            if (!top5MaxRating.isEmpty()) {

                List<TopPerformanceEmployeeDetailsResponse> collect = top5MaxRating.stream().map(reward -> {
                    String employeeId = reward.getEmployeeId();
                    Employees byEmployeeId = employeesRepository.findByEmployeeId(employeeId);
                    TopPerformanceEmployeeDetailsResponse detailsResponse = new TopPerformanceEmployeeDetailsResponse(byEmployeeId, reward);
                    return detailsResponse;
                }).collect(Collectors.toList());
                Collections.reverse(collect);
                //page set with response
                ResponseWithPageDetails departmentWithPageResponse = new ResponseWithPageDetails();
                departmentWithPageResponse.setDetailsWithPage(collect);
                departmentWithPageResponse.setPageNumber(top5MaxRating.getNumber());
                departmentWithPageResponse.setPageSize(top5MaxRating.getSize());
                departmentWithPageResponse.setTotalElements(top5MaxRating.getTotalElements());
                departmentWithPageResponse.setLastPage(top5MaxRating.isLast());
                departmentWithPageResponse.setTotalPages(top5MaxRating.getTotalPages());

                //set json response
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Successfully fetch Data!!");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(departmentWithPageResponse);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("No one award the employee to this month!!");
            jsonobjectFormat.setSuccess(false);
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


    public ResponseEntity<?> getDetailEmployeeOfTheMonth(String month) throws JsonProcessingException {
        try {
            List<Reward> byMonthAndDate = rewardRepository.findByOnWhichDateStartingWith(month);
            if (!byMonthAndDate.isEmpty()) {
                List<Reward> collect = byMonthAndDate.stream().filter(reward -> reward.isEmployeeOfTheMonth()).collect(Collectors.toList());
                if (!collect.isEmpty()) {
                    Collections.reverse(collect);
                    Reward reward = collect.get(0);
                    String employeeId = reward.getEmployeeId();
                    Employees employeeDetails = employeesRepository.findByEmployeeId(employeeId);
                    Optional<Department> department = departmentRepositroy.findById(employeeDetails.getDepartmentId());
                    List<Reward> getAllRewardByEmployeeId = rewardRepository.findAllByEmployeeId(employeeId);
                    EmployeeDetailsResponse detailsResponse = new EmployeeDetailsResponse();
                    detailsResponse.setEmployeeId(employeeId);
                    detailsResponse.setFirstname(employeeDetails.getFirstname());
                    detailsResponse.setLastname(employeeDetails.getLastname());
                    detailsResponse.setEmail(employeeDetails.getEmail());
                    detailsResponse.setContact(employeeDetails.getContact());
                    detailsResponse.setDesignation(employeeDetails.getDesignation());
                    detailsResponse.setImage(employeeDetails.getImage());
                    detailsResponse.setReward(getAllRewardByEmployeeId);
                    if (department.isPresent()) {
                        detailsResponse.setDepartmentDetails(department.get());
                    }
                    JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                    jsonobjectFormat.setMessage("Successfully fetch the employee of the month!!");
                    jsonobjectFormat.setSuccess(true);
                    jsonobjectFormat.setData(detailsResponse);
                    ObjectMapper Obj = new ObjectMapper();
                    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                    return ResponseEntity.ok().body(customExceptionStr);


                }
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Its is top performes employee but It is not allocated employee of the month!!");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(byMonthAndDate);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("There are no employee of this month : " + month);
            jsonobjectFormat.setSuccess(false);
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


    //issue it is not taking unique employee id
    public ResponseEntity<?> getNoOfAwardsWithDepartmentAndEmployeeDetails(String yyyyMonth, Integer page, Integer size) throws JsonProcessingException {
        try {
            Page<Reward> top5MaxRating = rewardRepository.findByOnWhichDateStartingWithOrderByOnWhichDateDesc(yyyyMonth, PageRequest.of(page, size));
            if (!top5MaxRating.getContent().isEmpty()) {
                List<AwardDetailsWithEmployeeResponse> collect = top5MaxRating.stream().map(reward -> {
                    Employees byEmployeeId = employeesRepository.findByEmployeeId(reward.getEmployeeId());
                    Long departmentId = byEmployeeId.getDepartmentId();
                    Optional<Department> department = departmentRepositroy.findById(departmentId);

                    AwardDetailsWithEmployeeResponse withEmployeeResponse = new AwardDetailsWithEmployeeResponse();
                    int countAwards = rewardRepository.countByEmployeeId(reward.getEmployeeId());
                    List<Reward> rewardList = rewardRepository.findAllByEmployeeId(reward.getEmployeeId());

                    withEmployeeResponse.setEmployeeId(reward.getEmployeeId());
                    withEmployeeResponse.setFirstname(byEmployeeId.getFirstname());
                    withEmployeeResponse.setLastname(byEmployeeId.getLastname());
                    withEmployeeResponse.setAwards(countAwards);
                    withEmployeeResponse.setRewardList(rewardList);
                    withEmployeeResponse.setContact(byEmployeeId.getContact());
                    withEmployeeResponse.setEmail(byEmployeeId.getEmail());
                    withEmployeeResponse.setDesignation(byEmployeeId.getDesignation());
                    withEmployeeResponse.setImage(byEmployeeId.getImage());
                    if (department.isPresent()) {
                        withEmployeeResponse.setDepartment(department.get());
                    }
                    return withEmployeeResponse;

                }).collect(Collectors.toList());
//
                Set<String> uniqueEmployeeId = new HashSet<>();
                List<AwardDetailsWithEmployeeResponse> collect1 = collect.stream().filter(collectUniqueElement -> !uniqueEmployeeId.add(collectUniqueElement.getEmployeeId())).collect(Collectors.toList());
                Collections.reverse(collect1);
                //page set with response
                ResponseWithPageDetails departmentWithPageResponse = new ResponseWithPageDetails();
                departmentWithPageResponse.setDetailsWithPage(collect1);
                departmentWithPageResponse.setPageNumber(top5MaxRating.getNumber());
                departmentWithPageResponse.setPageSize(top5MaxRating.getSize());
                departmentWithPageResponse.setTotalElements(top5MaxRating.getTotalElements());
                departmentWithPageResponse.setLastPage(top5MaxRating.isLast());
                departmentWithPageResponse.setTotalPages(top5MaxRating.getTotalPages());

                //set json response
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Successfully fetch Data!!");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(departmentWithPageResponse);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("No one award the employee to this month!!");
            jsonobjectFormat.setSuccess(false);
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

    public ResponseEntity getBestDepartmentPerformance() throws JsonProcessingException {
        try {

            Object[] highestCountField = rewardRepository.findHighestCountField();
            if (highestCountField == null) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Check reward details because NO employee have rewards!!");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            String employeeId = (String) highestCountField[0];
            Object noOfAwards = highestCountField[1];
            Employees byEmployeeId = employeesRepository.findByEmployeeId(employeeId);
            Long departmentId = byEmployeeId.getDepartmentId();


            List<TopPerformanceEmployeeDetailsResponse> employeeDetailsResponses = rewardRepository.findAllByEmployeeId(employeeId).stream().map(reward -> {
                String employeeId1 = reward.getEmployeeId();
                Employees byEmployeeId1 = employeesRepository.findByEmployeeId(employeeId1);
                TopPerformanceEmployeeDetailsResponse detailsResponse = new TopPerformanceEmployeeDetailsResponse(byEmployeeId1, reward);
                return detailsResponse;
            }).collect(Collectors.toList());

            Collections.reverse(employeeDetailsResponses);
            BestDepartmentResponse bestDepartmentResponse = new BestDepartmentResponse();
            bestDepartmentResponse.setNoOfAwards(noOfAwards);
            try {
                Department department = departmentRepositroy.findById(departmentId).get();
                bestDepartmentResponse.setDepartmentDetails(department);
                bestDepartmentResponse.setDeparmentName(department.getName());
            } catch (Exception e) {
                //
            }
            bestDepartmentResponse.setRewardDetails(employeeDetailsResponses);

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully Fetch the Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(bestDepartmentResponse);
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

    public ResponseEntity findBestDepartmentOfTheMonth(String yyyyMonth) throws JsonProcessingException {//findBestDepartmentOfTheMonth
        try {
            Set<String> uniqueEmployeeIds = new HashSet<>();
            List<Reward> startingWith = rewardRepository.findByOnWhichDateStartingWith(yyyyMonth);

            GetListOfRewardWithEmployeeDetails employeeDetailsWithReward = new GetListOfRewardWithEmployeeDetails();
            List<GetListOfRewardWithEmployeeDetails> getListOfRewardWithEmployeeDetails = new ArrayList<>();
            if (!startingWith.isEmpty()) {
                List<GetDetailsOfDepartmentOfTheMonth> collect = startingWith.stream().map(reward -> {
                    String employeeId = reward.getEmployeeId();
                    GetDetailsOfDepartmentOfTheMonth detailsOfDepartmentOfTheMonth = new GetDetailsOfDepartmentOfTheMonth();
                    if (!uniqueEmployeeIds.contains(employeeId)) {
                        uniqueEmployeeIds.add(employeeId);
                        List<Reward> employeeDataOnWhichDate = rewardRepository.findByEmployeeIdAndOnWhichDateStartingWith(employeeId, yyyyMonth);
                        int noOfAwardByThisEmployee = employeeDataOnWhichDate.size();

                        Employees byEmployeeId = employeesRepository.findByEmployeeId(employeeId);
                        Department department = departmentRepositroy.findById(byEmployeeId.getDepartmentId()).get();

                        detailsOfDepartmentOfTheMonth.setDepartmentId(department.getId());
                        detailsOfDepartmentOfTheMonth.setEmployeeId(employeeId);
                        detailsOfDepartmentOfTheMonth.setNoOfAwards(noOfAwardByThisEmployee);

                        employeeDetailsWithReward.setEmployees(byEmployeeId);
                        employeeDetailsWithReward.setRewardList(employeeDataOnWhichDate);
                        getListOfRewardWithEmployeeDetails.add(employeeDetailsWithReward);

                        return detailsOfDepartmentOfTheMonth;
                    } else {
                        return null;
                    }
                }).filter(details -> details != null).collect(Collectors.toList());

                Set<Long> uniqueDepartmentIds = new HashSet<>();
                Map<Long, Integer> map = new HashMap<>();
                List<Map<Long, Integer>> collect1 = collect.stream().map(noOfRewardDetails -> {
                    Long departmentId = noOfRewardDetails.getDepartmentId();
                    if (!map.containsKey(departmentId)) {
                        map.put(departmentId, noOfRewardDetails.getNoOfAwards());
                    } else {
                        Integer integer = map.get(departmentId);
                        map.put(departmentId, integer + noOfRewardDetails.getNoOfAwards());
                    }
                    return map;
                }).collect(Collectors.toList());
                if (!collect1.isEmpty()) {
                    List<Map<Long, Integer>> collect2 = collect1.stream().filter(longIntegerMap -> !uniqueDepartmentIds.add(longIntegerMap.keySet().iterator().next())).collect(Collectors.toList());

                    Optional<Map.Entry<Long, Integer>> max = null;
                    if (collect2.isEmpty()) {
//                        System.out.println("empty");
                        max = collect1.stream()
                                .flatMap(mapValue -> mapValue.entrySet().stream())
                                .max(Comparator.comparing(Map.Entry::getValue));
                    } else {
//                        System.out.println("Not empty");
                        max = collect2.stream()
                                .flatMap(mapValue -> mapValue.entrySet().stream())
                                .max(Comparator.comparing(Map.Entry::getValue));
                    }
//                    try {//check the condition
//                        System.out.println(collect2);//null
//                        System.out.println(max.get());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    if (max.isPresent()) {
                        Map.Entry<Long, Integer> longIntegerEntry = max.get();
                        Long getDepartmentOfTheMonthId = longIntegerEntry.getKey();
                        Integer noOfRewards = longIntegerEntry.getValue();

                        Department department = departmentRepositroy.findById(getDepartmentOfTheMonthId).get();

                        Integer noOfEmployeeAccociateWithDepartment = employeesRepository.countByDepartmentId(department.getId());
                        DepartmentOfTheMonthDetailsResponse departmentOfTheMonthDetailsResponse = new DepartmentOfTheMonthDetailsResponse();
                        departmentOfTheMonthDetailsResponse.setDepartment(department);
                        departmentOfTheMonthDetailsResponse.setNoOfAwards(noOfRewards);
                        departmentOfTheMonthDetailsResponse.setNoOfEmployees(noOfEmployeeAccociateWithDepartment);


                        if (!getListOfRewardWithEmployeeDetails.isEmpty()) {
                            departmentOfTheMonthDetailsResponse.setRewardsWithEmployeeDetails(getListOfRewardWithEmployeeDetails);
                        }

                        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                        jsonobjectFormat.setMessage("Successfully fetch data!!");
                        jsonobjectFormat.setSuccess(true);
                        jsonobjectFormat.setData(departmentOfTheMonthDetailsResponse);
                        ObjectMapper Obj = new ObjectMapper();
                        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                        return ResponseEntity.ok().body(customExceptionStr);
                    } else {
                        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                        jsonobjectFormat.setMessage("Not able to find department of the month!!");
                        jsonobjectFormat.setSuccess(false);
                        jsonobjectFormat.setData("");
                        ObjectMapper Obj = new ObjectMapper();
                        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                        return ResponseEntity.ok().body(customExceptionStr);
                    }
                }
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Employee not have awards in this month!!");
            jsonobjectFormat.setSuccess(false);
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


    public ResponseEntity<?> getDepartmentPerformance(String yyyyMonth) throws JsonProcessingException {
        try {
            Set<String> uniqueEmployeeIds = new HashSet<>();

            List<Reward> startingWith = rewardRepository.findByOnWhichDateStartingWith(yyyyMonth);
            if (startingWith.isEmpty()) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("No employee have awards in this month!!");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            List<GetDetailsOfDepartmentOfTheMonth> collect = startingWith.stream().map(reward -> {
                String employeeId = reward.getEmployeeId();
                GetDetailsOfDepartmentOfTheMonth detailsOfDepartmentOfTheMonth = new GetDetailsOfDepartmentOfTheMonth();
                if (!uniqueEmployeeIds.contains(employeeId)) {
                    uniqueEmployeeIds.add(employeeId);
                    List<Reward> employeeDataOnWhichDate = rewardRepository.findByEmployeeIdAndOnWhichDateStartingWith(employeeId, yyyyMonth);
                    int noOfAwardByThisEmployee = employeeDataOnWhichDate.size();

                    Employees byEmployeeId = employeesRepository.findByEmployeeId(employeeId);
                    Department department = departmentRepositroy.findById(byEmployeeId.getDepartmentId()).get();

                    detailsOfDepartmentOfTheMonth.setDepartmentId(department.getId());
                    detailsOfDepartmentOfTheMonth.setEmployeeId(employeeId);
                    detailsOfDepartmentOfTheMonth.setNoOfAwards(noOfAwardByThisEmployee);

                    return detailsOfDepartmentOfTheMonth;
                } else {
                    return null;
                }
            }).filter(details -> details != null).collect(Collectors.toList());

            Map<Long, Integer> noOfAwardsWithDepartmentDetails = new HashMap<>();
            List<Map<Long, Integer>> collect1 = collect.stream().map(getDetailsOfDepartmentOfTheMonth -> {
                Long departmentId = getDetailsOfDepartmentOfTheMonth.getDepartmentId();
                Integer noOfAwards = getDetailsOfDepartmentOfTheMonth.getNoOfAwards();

                if (!noOfAwardsWithDepartmentDetails.containsKey(departmentId)) {
                    noOfAwardsWithDepartmentDetails.put(departmentId, noOfAwards);
                } else {
                    Integer integer = noOfAwardsWithDepartmentDetails.get(departmentId);
                    noOfAwardsWithDepartmentDetails.put(departmentId, integer + noOfAwards);
                }
                return noOfAwardsWithDepartmentDetails;
            }).collect(Collectors.toList());

            Set<Long> uniqueDepartmentIds = new HashSet<>();
            List<Map<Long, Integer>> collect2 = collect1.stream().filter(longIntegerMap -> uniqueDepartmentIds.add(longIntegerMap.keySet().iterator().next())).collect(Collectors.toList());
            List<Map.Entry<Long, Integer>> collect4 = collect2.stream().flatMap(longIntegerMap -> longIntegerMap.entrySet().stream()).collect(Collectors.toList());

            List<RewardDetailsWithEmployeeResponse> detailsWithEmployeeResponses = collect4.stream().map(mapStringEntry -> {//List<RewardDetailsWithEmployeeResponse> detailsWithEmployeeResponses =
                Long departmentId = mapStringEntry.getKey();
                Integer noOfAwards = mapStringEntry.getValue();
                Department department = departmentRepositroy.findById(departmentId).orElse(null);
                if (department == null) {
                    return null;
                }
                RewardDetailsWithEmployeeResponse detailsResponse = new RewardDetailsWithEmployeeResponse();
                detailsResponse.setDepartment(department);
                detailsResponse.setNoOfAwards(noOfAwards);

                List<Employees> byDepartmentId = employeesRepository.findByDepartmentId(departmentId);
                detailsResponse.setNoOfEmployees(byDepartmentId.size());

                List<GetListOfRewardWithEmployeeDetails> getListOfRewardWithEmployeeDetails = byDepartmentId.stream().map(employees -> {//
                    GetListOfRewardWithEmployeeDetails employeeDetails = new GetListOfRewardWithEmployeeDetails();
                    String employeeId1 = employees.getEmployeeId();
                    List<Reward> whichDateStartingWith = rewardRepository.findByEmployeeIdAndOnWhichDateStartingWith(employeeId1, yyyyMonth);
                    if (!whichDateStartingWith.isEmpty()) {
                        employeeDetails.setRewardList(whichDateStartingWith);
                        Employees byEmployeeId = employeesRepository.findByEmployeeId(employeeId1);
                        if (byDepartmentId == null) {
                            employeeDetails.setEmployees(null);
                        }
                        employeeDetails.setEmployees(byEmployeeId);
                        employeeDetails.setRewardList(whichDateStartingWith);
                        return employeeDetails;
                    } else {
                        return null;
                    }
                }).filter(v -> v != null).collect(Collectors.toList());
                if (!getListOfRewardWithEmployeeDetails.isEmpty()) {
                    detailsResponse.setRewardsWithEmployeeDetails(getListOfRewardWithEmployeeDetails);
                }

                return detailsResponse;

            }).sorted(Comparator.comparing(RewardDetailsWithEmployeeResponse::getNoOfAwards).reversed()).collect(Collectors.toList());

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully fetch data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(detailsWithEmployeeResponses);
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

}
