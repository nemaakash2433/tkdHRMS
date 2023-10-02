package com.tablabs.hrms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tablabs.hrms.entity.Comment;
import com.tablabs.hrms.entity.Employees;
import com.tablabs.hrms.entity.Tasks;
import com.tablabs.hrms.models.DTO.CommentDTO;
import com.tablabs.hrms.models.Message;
import com.tablabs.hrms.models.response.CommentWithCommentersResponse;
import com.tablabs.hrms.models.response.DetailedTasksResponse;
import com.tablabs.hrms.models.response.ResponseWithPageDetails;
import com.tablabs.hrms.repository.CommentRepository;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.repository.TasksRepository;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentImpl {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private TasksRepository tasksRepository;

    public ResponseEntity<?> createComment(Comment comment) throws JsonProcessingException {
        try {
            if (employeesRepository.existsByEmployeeId(comment.getEmployeeId())) {

                comment.setTime(new Date());
//                Comment comment = modelMapper.map(commentDTO, Comment.class);
                Comment result = commentRepository.save(comment);
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("Successfully created the comment!!");
                jsonobjectFormat.setSuccess(true);
                jsonobjectFormat.setData(result);
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "Employee Id doesn't exists in our record!!"));
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

    public ResponseEntity<?> updateComment(CommentDTO commentDTO) throws JsonProcessingException {
        try {
            if (commentDTO.getEmployeeId() == commentRepository.findById(commentDTO.getId()).get().getEmployeeId())
                if (employeesRepository.existsByEmployeeId(commentDTO.getEmployeeId())) {
                    Comment comment = modelMapper.map(commentDTO, Comment.class);
                    Comment result = commentRepository.save(comment);
                    JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                    jsonobjectFormat.setMessage("Successfully update the comment!!");
                    jsonobjectFormat.setSuccess(true);
                    jsonobjectFormat.setData(result);
                    ObjectMapper Obj = new ObjectMapper();
                    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                    return ResponseEntity.ok().body(customExceptionStr);
                }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee isn't exists in our record!!");
        } catch (Exception e) {
            return ResponseEntity.ok(new Message(false, "Something went wrong!! : " + e.getMessage()));
        }
    }

    public ResponseEntity<?> deleteCommentById(Long id) {
        try {
            if (commentRepository.existsById(id)) {
                commentRepository.deleteById(id);
                return ResponseEntity.ok(new Message(true, "Successfully deleted!!"));
            }
            return ResponseEntity.ok(new Message(false, "Id doesn't exists our record!!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message(false, "Something went wrong!!"));
        }
    }

    public ResponseEntity<?> getAllComments(int page, int size) throws JsonProcessingException {
        try {
            //            Pageable pageable =new PageRequest(page, size);
            Page<Comment> dataWithPage = commentRepository.findAll(PageRequest.of(page, size));
            ResponseWithPageDetails departmentWithPageResponse = new ResponseWithPageDetails();
            departmentWithPageResponse.setDetailsWithPage(dataWithPage.getContent());
            departmentWithPageResponse.setPageNumber(dataWithPage.getNumber());
            departmentWithPageResponse.setPageSize(dataWithPage.getSize());
            departmentWithPageResponse.setTotalElements(dataWithPage.getTotalElements());
            departmentWithPageResponse.setLastPage(dataWithPage.isLast());
            departmentWithPageResponse.setTotalPages(dataWithPage.getTotalPages());

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully Fetch Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(departmentWithPageResponse);
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

    public ResponseEntity<?> getAllCommentsByDesc(int page, int size) {
        try {
            //            Pageable pageable =new PageRequest(page, size);
            Page<Comment> dataWithPage = commentRepository.findAllByOrderByIdDesc(PageRequest.of(page, size));
            ResponseWithPageDetails departmentWithPageResponse = new ResponseWithPageDetails();
            departmentWithPageResponse.setDetailsWithPage(dataWithPage.getContent());
            departmentWithPageResponse.setPageNumber(dataWithPage.getNumber());
            departmentWithPageResponse.setPageSize(dataWithPage.getSize());
            departmentWithPageResponse.setTotalElements(dataWithPage.getTotalElements());
            departmentWithPageResponse.setLastPage(dataWithPage.isLast());
            departmentWithPageResponse.setTotalPages(dataWithPage.getTotalPages());

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully Fetch Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(departmentWithPageResponse);
            ObjectMapper Obj = new ObjectMapper();
            String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
            return ResponseEntity.ok().body(customExceptionStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> getCommentByEmployeeId(String emplId) throws JsonProcessingException {
        try {
            if (employeesRepository.existsByEmployeeId(emplId)) {
                List<Comment> byEmployeeId = commentRepository.findByEmployeeId(emplId);
                if (!byEmployeeId.isEmpty()) {
                    JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                    jsonobjectFormat.setMessage("Comment successfully fetch associate with this employeeId");
                    jsonobjectFormat.setSuccess(true);
                    jsonobjectFormat.setData(byEmployeeId);
                    ObjectMapper Obj = new ObjectMapper();
                    String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                    return ResponseEntity.ok().body(customExceptionStr);
                }
                JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
                jsonobjectFormat.setMessage("There is no comment associate with this employeeId");
                jsonobjectFormat.setSuccess(false);
                jsonobjectFormat.setData("");
                ObjectMapper Obj = new ObjectMapper();
                String customExceptionStr = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobjectFormat);
                return ResponseEntity.ok().body(customExceptionStr);
            }
            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Employee id doesn't exists in our record!!");
            jsonobjectFormat.setSuccess(false);
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


    public ResponseEntity<?> getTaskByIdWithCommenter(Long id) throws JsonProcessingException {

        try {
            if(!tasksRepository.existsById(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"Task id is not exists in our record!!"));
            }
            Tasks tasks = tasksRepository.findById(id).get();
            List<Comment> commentsByTaskId = commentRepository.findByTaskId(id);

            List<CommentWithCommentersResponse> commentWithCommentersResponses = commentsByTaskId.stream().map(comment -> {
                CommentWithCommentersResponse commentersResponse = new CommentWithCommentersResponse();
                String employeeId = comment.getEmployeeId();
                Employees byEmployeeId = employeesRepository.findByEmployeeId(employeeId);
                commentersResponse.setId(comment.getId());
                commentersResponse.setCommentor(byEmployeeId);
                commentersResponse.setTime(comment.getTime());
                commentersResponse.setComment(comment.getComment());
                return commentersResponse;
            }).collect(Collectors.toList());
            String employeeId = tasks.getAssignee();
            Employees assignee = employeesRepository.findByEmployeeId(employeeId);
            DetailedTasksResponse detailedTasksResponse = new DetailedTasksResponse();
            detailedTasksResponse.setId(tasks.getId());
            detailedTasksResponse.setDeadLine(tasks.getDeadline());
            detailedTasksResponse.setTask(tasks.getTaskName());
            detailedTasksResponse.setAssignee(assignee);
            detailedTasksResponse.setComments(commentWithCommentersResponses);

            JsonObjectFormat jsonobjectFormat = new JsonObjectFormat();
            jsonobjectFormat.setMessage("Successfully Fetch Data!!");
            jsonobjectFormat.setSuccess(true);
            jsonobjectFormat.setData(detailedTasksResponse);
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


}
