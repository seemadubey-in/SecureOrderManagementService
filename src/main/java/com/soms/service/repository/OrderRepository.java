package com.soms.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soms.service.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
