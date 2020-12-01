package com.soms.service.businessService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soms.service.entity.Order;
import com.soms.service.exception.ResourceNotFoundException;
import com.soms.service.repository.OrderRepository;
@Component
public class OrderBusinessService  {

	@Autowired
    private OrderRepository orderRepo;
	
	public List<Order> getAllOrders(){
		return orderRepo.findAll();
		
	}
	
	public Order getOrder(int id) {
		return orderRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("order does not exist"));
	}
	
	public Order createOrder(Order order) {
		return orderRepo.save(order);
	}
	public String deleteOrders(Order order) {
		orderRepo.delete(order);
		return "Deletion done";
	}
	
}
