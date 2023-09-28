package com.tablabs.hrms.controller;

import com.tablabs.hrms.entity.Tasks;
import com.tablabs.hrms.repository.TasksRepository;
import com.tablabs.hrms.service.EmployeesServiceImpl;
import com.tablabs.hrms.service.TaskImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final Logger log = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskImpl taskImpl;

    @PostMapping("/createTask")
    public ResponseEntity createTask(@RequestBody Tasks tasks){
        try {
            log.debug("Request for create task : ",tasks.getTaskName());
            return taskImpl.createTask(tasks);
        }catch (Exception e){
            log.info(" : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody Tasks tasks){
        try{
            log.debug("Request for update task : {}",tasks.getTaskName());
            return taskImpl.updateTask(tasks);
        }catch (Exception e){
            log.info(" : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getTaskById")
    public ResponseEntity getTask(@RequestParam(name = "taskId")String taskId){
        try {
            log.debug("Request for get task by taskId : ",taskId);
            return taskImpl.getTaskById(taskId);
        }catch (Exception e){
            log.info(" : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
