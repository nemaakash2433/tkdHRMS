package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Comment;
import com.tablabs.hrms.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByEmployeeId(String emplId);
    Page<Comment> findAllByOrderByIdDesc(Pageable pageable);
}
