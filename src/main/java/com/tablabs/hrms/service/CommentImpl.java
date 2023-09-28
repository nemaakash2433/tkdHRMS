package com.tablabs.hrms.service;

import com.tablabs.hrms.entity.Comment;
import com.tablabs.hrms.repository.CommentRepository;
import com.tablabs.hrms.repository.EmployeesRepository;
import com.tablabs.hrms.util.JsonObjectFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentImpl {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public ResponseEntity<?> createComment(Comment comment){
        if(employeesRepository.existsByEmployeeId(comment.getEmployeeId())) {
            Comment result = commentRepository.save(comment);
            return ResponseEntity.ok(new JsonObjectFormat("Successfully created",true, result));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee isn't exists in our record!!");
    }

    public ResponseEntity<?> updateComment(Comment comment){
        if(comment.getEmployeeId()==commentRepository.findById(comment.getId()).get().getEmployeeId())
        if(employeesRepository.existsByEmployeeId(comment.getEmployeeId())) {
            Comment result = commentRepository.save(comment);
            return ResponseEntity.ok(new JsonObjectFormat( "Successfully created",true, result));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee isn't exists in our record!!");
    }
}
