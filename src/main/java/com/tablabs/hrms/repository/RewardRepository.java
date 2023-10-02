package com.tablabs.hrms.repository;

import com.tablabs.hrms.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RewardRepository extends JpaRepository<Reward,Long> {
    Reward findByEmployeeId(String emplId);

    boolean existsByEmployeeId(String emplId);
}
