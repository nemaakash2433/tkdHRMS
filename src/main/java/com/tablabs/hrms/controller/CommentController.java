package com.tablabs.hrms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tablabs.hrms.entity.Comment;
import com.tablabs.hrms.models.DTO.CommentDTO;
import com.tablabs.hrms.service.CommentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommentController {
    @Autowired
    private CommentImpl commentImpl;

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) throws JsonProcessingException {
        return commentImpl.createComment(comment);
    }

    @PutMapping("/updateComment")
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO) throws JsonProcessingException {
        return commentImpl.updateComment(commentDTO);
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<?> getAllComment(@RequestParam(name="page",defaultValue = "0",required = false)Integer page,
                                           @RequestParam(name = "size",defaultValue = "10",required = false)Integer size) throws JsonProcessingException {
        return commentImpl.getAllComments(page,size);
    }


    @DeleteMapping("/deleteComment")
    public ResponseEntity<?> deleteCommentById(@RequestParam(name = "commentId")Long commentId){
        return commentImpl.deleteCommentById(commentId);
    }


    @GetMapping("/getAllCommentByEmployeeId")
    public ResponseEntity<?> getAllCommentByEmployeeId(@RequestParam(name = "employeeId")String employeeId) throws JsonProcessingException {
        return commentImpl.getCommentByEmployeeId(employeeId);
    }

    @GetMapping("/getAllCommentsByDesc")
    public ResponseEntity<?> getAllCommentsByDesc(@RequestParam(name="page",defaultValue = "0",required = false)Integer page,
                                                  @RequestParam(name = "size",defaultValue = "10",required = false)Integer size) throws JsonProcessingException {
        return commentImpl.getAllCommentsByDesc(page,size);
    }

    @GetMapping("/getTaskByIdWithCommenter")
    public ResponseEntity<?> getTaskByIdWithCommenterResponse(@RequestParam(name = "id")Long id) throws JsonProcessingException {
        return commentImpl.getTaskByIdWithCommenter(id);
    }




}
