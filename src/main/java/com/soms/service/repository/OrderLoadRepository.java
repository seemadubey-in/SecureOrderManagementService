package com.soms.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soms.service.entity.OrderLoad;
@Repository
public interface OrderLoadRepository extends JpaRepository<OrderLoad, Long>{

}
