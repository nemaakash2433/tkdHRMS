package com.tablabs.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tablabs.hrms.entity.PayRequest;

public interface PayRequestRepository extends JpaRepository<PayRequest, Long>{
    boolean existsByEmployeeId(String empId);

}
