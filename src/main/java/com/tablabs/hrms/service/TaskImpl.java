package com.tablabs.hrms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Tasks;
import com.tablabs.hrms.enums.TasksStatsType;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.response.ResponseWithPageDetails;
import com.tablabs.hrms.models.response.TaskStatusResponse;
import com.tablabs.hrms.models.response.TaskWithEmployeeResponse;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.repository.TasksRepository;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskImpl {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private EmployeesRepository employeesRepository;


    public ResponseEntity<?> createTask(Tasks tasks) {
        String assignee = tasks.getAssignee();
        if (employeesRepository.existsByEmployeeId(assignee)) {
            tasks.setTasksStatsType(TasksStatsType.NewTask);
            Tasks save = tasksRepository.save(tasks);
            return ResponseEntity.ok(new JsonObjectFormat("Successfully retrieve data!!", true, save));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Employee/Assignee id doesn't exists in our recod!!"));


    }

    public ResponseEntity<?> updateTask(Tasks tasks) {
        if (tasksRepository.existsById(tasks.getId())) {
            if (employeesRepository.existsByEmployeeId(tasks.getAssignee())) {
                Tasks taskData = tasksRepository.findById(tasks.getId()).get();
                if (taskData != null) {
                    taskData.setTaskName(tasks.getTaskName());
                    taskData.setAssignee(tasks.getAssignee());
                    taskData.setDeadline(tasks.getDeadline());
                    taskData.setPriority(tasks.getPriority());
                    taskData.setTasksStatsType(tasks.getTasksStatsType());
                    Tasks result = tasksRepository.save(taskData);
                    return ResponseEntity.ok(new JsonObjectFormat("Successfully updated", true, result));
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "No data persent in this id!!"));
            }
            return ResponseEntity.ok(new Message(false, "You cannot change the id!!"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task id different!!.Please give valid Id");
    }

    public ResponseEntity<?> getTaskById(Long id) {
        if (tasksRepository.existsById(id)) {
            Optional<Tasks> byId = tasksRepository.findById(id);
            if (byId.isPresent()) {
                return ResponseEntity.ok(new JsonObjectFormat("Successfully retrieve task data", true,byId.get() ));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"Id doesn't exists in our record!!"));
            }
        }
        return ResponseEntity.ok("There is no record in this taskId");
    }

    public ResponseEntity<?> getTasksStatus() {

        int newTask = tasksRepository.countByTasksStatsType(TasksStatsType.NewTask);
        int pendingTask = tasksRepository.countByTasksStatsType(TasksStatsType.Pending);
        int completed = tasksRepository.countByTasksStatsType(TasksStatsType.Completed);

        TaskStatusResponse taskStatusResponse = new TaskStatusResponse();
        taskStatusResponse.setNewTasks(newTask);
        taskStatusResponse.setPendingTasks(pendingTask);
        taskStatusResponse.setCompletedTasks(completed);

        return ResponseEntity.ok(new JsonObjectFormat("Successfully retrieve tasks data!!", true, taskStatusResponse));

    }

    public ResponseEntity getAllTasks(Integer page, Integer size, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Tasks> tasks = tasksRepository.findAll(PageRequest.of(page, size, sort));
        List<Tasks> tasksContent = tasks.getContent();
        ResponseWithPageDetails responseWithPageDetails = new ResponseWithPageDetails();
        responseWithPageDetails.setDetailsWithPage(tasksContent);
        responseWithPageDetails.setPageNumber(tasks.getNumber());
        responseWithPageDetails.setPageSize(tasks.getSize());
        responseWithPageDetails.setTotalElements(tasks.getTotalElements());
        responseWithPageDetails.setLastPage(tasks.isLast());
        responseWithPageDetails.setTotalPages(tasks.getTotalPages());
        return ResponseEntity.ok(new JsonObjectFormat("Successfully retrieve data!!", true, responseWithPageDetails));

    }


    public ResponseEntity<?> getTasksWithEmployeeDetails() throws JsonProcessingException {
        List<Tasks> taskList = tasksRepository.findAll();
        List<TaskWithEmployeeResponse> taskWithEmployeeResponses = taskList.stream().map(tasks -> {
            String taskName = tasks.getTaskName();
            String assignee = tasks.getAssignee();
            TasksStatsType tasksStatsType = tasks.getTasksStatsType();
            Employees employeeData = employeesRepository.findByEmployeeId(assignee);
            String firstname = employeeData.getFirstname();
            String lastname = employeeData.getLastname();

            TaskWithEmployeeResponse task = new TaskWithEmployeeResponse();
            task.setEmployeeName(firstname + " " + lastname);

            task.setTaskName(taskName);
            task.setTasksStatsType(tasksStatsType);
            task.setEmployeeData(employeeData);

            return task;

        }).collect(Collectors.toList());

        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
        jsonobjectFormat.setMessage("Successfully Fetch Data!!");
        jsonobjectFormat.setSuccess(true);
        jsonobjectFormat.setData(taskWithEmployeeResponses);
        ObjectMapper Obj = new ObjectMapper();
        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
        return ResponseEntity.ok().body(customExceptionStr);
    }
//    public ResponseEntity<?> getTasksByStatusWithEmployeeDetails(TasksStatsType tasksStatsType) throws JsonProcessingException {
//        List<Tasks> taskList = tasksRepository.findAll();
//        List<TaskWithEmployeeResponse> taskWithEmployeeResponses = taskList.stream().map(tasks -> {
//            String taskName = tasks.getTaskName();
//            String assignee = tasks.getAssignee();
//            TasksStatsType tasksStatsType = tasks.getTasksStatsType();
//            Employees employeeData = employeesRepository.findByEmployeeId(assignee);
//            String firstname = employeeData.getFirstname();
//            String lastname = employeeData.getLastname();
//
//            TaskWithEmployeeResponse task = new TaskWithEmployeeResponse();
//            task.setEmployeeName(firstname + " " + lastname);
//
//            task.setTaskName(taskName);
//            task.setTasksStatsType(tasksStatsType);
//            task.setEmployeeData(employeeData);
//
//            return task;
//
//        }).collect(Collectors.toList());
//
//        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
//        jsonobjectFormat.setMessage("Successfully Fetch Data!!");
//        jsonobjectFormat.setSuccess(true);
//        jsonobjectFormat.setData(taskWithEmployeeResponses);
//        ObjectMapper Obj = new ObjectMapper();
//        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
//        return ResponseEntity.ok().body(customExceptionStr);
//    }

}
