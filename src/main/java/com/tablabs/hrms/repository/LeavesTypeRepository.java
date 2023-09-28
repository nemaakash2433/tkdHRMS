package com.tablabs.hrms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tablabs.hrms.entity.LeavesType;

@Repository
public interface LeavesTypeRepository extends JpaRepository<LeavesType, Long>{

}
