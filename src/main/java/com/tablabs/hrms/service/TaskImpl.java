package com.tablabs.hrms.service;

import com.tablabs.hrms.entity.Tasks;
import com.tablabs.hrms.enums.TasksStatsType;
import com.tablabs.hrms.models.response.TasksResponse;
import com.tablabs.hrms.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskImpl {

    @Autowired
    private TasksRepository tasksRepository;



    public ResponseEntity<?> createTask(Tasks tasks){
        tasks.setTasksStatsType(TasksStatsType.Pending);
        Tasks save = tasksRepository.save(tasks);
        return ResponseEntity.ok(save);
    }

    public ResponseEntity<?> updateTask(Tasks tasks){
        if(tasksRepository.existsByTaskId(tasks.getTaskId())){
            Tasks result = tasksRepository.save(tasks);
            return ResponseEntity.ok(new TasksResponse(true,"Successfully updated",result));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task id different!!.Please give valid Id");
    }

    public ResponseEntity<?> getTaskById(String taskId){
        if(tasksRepository.existsByTaskId(taskId)) {
            Tasks byTaskId = tasksRepository.findByTaskId(taskId);
            if (byTaskId != null) {
                return ResponseEntity.ok(new TasksResponse(true, "Successfully retrieve task data", byTaskId));
            }
        }
        return ResponseEntity.ok("There is no record in this taskId");
    }

}
