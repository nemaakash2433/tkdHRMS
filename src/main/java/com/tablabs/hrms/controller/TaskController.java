package com.tablabs.hrms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Tasks;
import com.tablabs.hrms.enums.TasksStatsType;
import com.tablabs.hrms.models.response.TaskStatusResponse;
import com.tablabs.hrms.repository.TasksRepository;
import com.tablabs.hrms.service.EmployeesServiceImpl;
import com.tablabs.hrms.service.TaskImpl;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {

    private final Logger log = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskImpl taskImpl;

    @PostMapping("/postTask")
    public ResponseEntity createTask(@RequestBody Tasks tasks) {
        try {
            log.debug("Request for create task : ", tasks.getTaskName());
            return taskImpl.postTask(tasks);
        } catch (Exception e) {
            log.info(" : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody Tasks tasks) {
        try {
            log.debug("Request for update task : {}", tasks.getTaskName());
            return taskImpl.updateTask(tasks);
        } catch (Exception e) {
            log.info(" : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getTasksWithEmployeeDetails")
    public ResponseEntity<?> getTasksWithEmployeeDetails() throws JsonProcessingException {
        return taskImpl.getTasksWithEmployeeDetails();
    }

    @GetMapping("/getTaskById")
    public ResponseEntity getTask(@RequestParam(name = "taskId") Long taskId) {
        try {
            log.debug("Request for get task by taskId : ", taskId);
            return taskImpl.getTaskById(taskId);
        } catch (Exception e) {
            log.info(" : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/taskStatus")
    public ResponseEntity<?> getTasksStatus() throws JsonProcessingException {
        return taskImpl.getTasksStatus();
    }

    @GetMapping("/getAllTasksAscOrDesc")
    public ResponseEntity<?> getAllTasks(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                         @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                         @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) throws JsonProcessingException {
        return taskImpl.getAllTasks(page, size, sortBy, sortDir);
    }

    @GetMapping("/getTasksByStatusWithEmployeeDetails")
    public ResponseEntity<?> getTasksDetailsAlongWithEmployee(@RequestParam(name = "taskStatusType")TasksStatsType tasksStatsType) throws JsonProcessingException {
        return taskImpl.getTasksByStatusWithEmployeeDetails(tasksStatsType);
    }

    @GetMapping("/searchTasks")
    public ResponseEntity<String> searchTasks(@RequestParam String keyword) throws JsonProcessingException{
        return taskImpl.searchTasks(keyword);
    }
}
