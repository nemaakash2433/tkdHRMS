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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskImpl {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private EmployeesRepository employeesRepository;


    public ResponseEntity<String> postTask(Tasks tasks) throws JsonProcessingException {
        try {
            String assignee = tasks.getAssignee();
            if (employeesRepository.findByEmployeeId(assignee) != null) {
                tasks.setTasksStatsType(TasksStatsType.NewTask);
                Tasks save = tasksRepository.save(tasks);

                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Tasks saved successfully");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(save);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            } else {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Enter valid employee id");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData(" ");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("no data found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }

    public ResponseEntity<?> updateTask(Tasks tasks) throws JsonProcessingException {
        try {
            if (tasksRepository.existsById(tasks.getId())) {
                if (employeesRepository.existsByEmployeeId(tasks.getAssignee())) {
                    Tasks taskData = tasksRepository.findById(tasks.getId()).get();
                    if (taskData.getId() == tasks.getId()) {
                        taskData.setTaskName(tasks.getTaskName());
                        taskData.setAssignee(tasks.getAssignee());
                        taskData.setDeadline(tasks.getDeadline());
                        taskData.setPriority(tasks.getPriority());
                        taskData.setTasksStatsType(tasks.getTasksStatsType());
                        Tasks result = tasksRepository.save(taskData);
                        JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                        jsonobjectFormat.setMessage("Successfully update the task!!");
                        jsonobjectFormat.setSuccess(true);
                        jsonobjectFormat.setData(result);
                        ObjectMapper Obj = new ObjectMapper();
                        String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                        return ResponseEntity.ok().body(customExceptionStr);
                    }
                }
                return ResponseEntity.ok(new Message(false, "You cannot change the id!!"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task id different!!.Please give valid Id");
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

    public ResponseEntity<?> getTaskById(Long id) throws JsonProcessingException {
        if (tasksRepository.existsById(id)) {
            Optional<Tasks> byId = tasksRepository.findById(id);
            if (byId.isPresent()) {
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Successfully fetch the status!!");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(byId.get());
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Id doesn't exists in our record!!"));
            }
        }
        return ResponseEntity.ok("There is no record in this taskId");
    }

    public ResponseEntity<?> getTasksStatus() throws JsonProcessingException {
        try {
            int newTask = tasksRepository.countByTasksStatsType(TasksStatsType.NewTask);
            int pendingTask = tasksRepository.countByTasksStatsType(TasksStatsType.Pending);
            int completed = tasksRepository.countByTasksStatsType(TasksStatsType.Completed);

            TaskStatusResponse taskStatusResponse = new TaskStatusResponse();
            taskStatusResponse.setNewTasks(newTask);
            taskStatusResponse.setPendingTasks(pendingTask);
            taskStatusResponse.setCompletedTasks(completed);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully fetch the status!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(taskStatusResponse);
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

    public ResponseEntity getAllTasks(Integer page, Integer size, String sortBy, String sortDir) throws JsonProcessingException {
        try {
            Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Page<Tasks> tasks = tasksRepository.findAll(PageRequest.of(page, size, sort));
            List<Tasks> tasksContent = tasks.getContent();
            //set page
            ResponseWithPageDetails responseWithPageDetails = new ResponseWithPageDetails();
            responseWithPageDetails.setDetailsWithPage(tasksContent);
            responseWithPageDetails.setPageNumber(tasks.getNumber());
            responseWithPageDetails.setPageSize(tasks.getSize());
            responseWithPageDetails.setTotalElements(tasks.getTotalElements());
            responseWithPageDetails.setLastPage(tasks.isLast());
            responseWithPageDetails.setTotalPages(tasks.getTotalPages());

            //set response
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully fetch the status!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(responseWithPageDetails);
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


    public ResponseEntity<?> getTasksWithEmployeeDetails() throws JsonProcessingException {
        try {
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

    public ResponseEntity<?> getTasksByStatusWithEmployeeDetails(TasksStatsType tasksStatsType) throws JsonProcessingException {
        try {
            List<Tasks> taskList = tasksRepository.findByTasksStatsType(tasksStatsType);
            if (!taskList.isEmpty()) {
                List<TaskWithEmployeeResponse> taskWithEmployeeResponses = taskList.stream().map(tasks -> {
                    String taskName = tasks.getTaskName();
                    String assignee = tasks.getAssignee();
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
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("There is no " + tasksStatsType + " !!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Something went wrong!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData("");
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }

    }

    public ResponseEntity<String> searchTasks(String keyword) throws JsonProcessingException {
        try {
            List<Tasks> employees= tasksRepository.findByDescriptionOrTaskNameContainingIgnoreCase(keyword);
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("Tasks fetch successfuly");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(employees);

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();

            jsonobjectFormat.setMessage("Tasks are not be Found");
            jsonobjectFormat.setSuccess(false);
            jsonobjectFormat.setData("");

            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        }
    }
}
