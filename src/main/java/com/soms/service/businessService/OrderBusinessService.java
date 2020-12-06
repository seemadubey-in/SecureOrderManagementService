package com.soms.service.businessService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soms.service.entity.Order;
import com.soms.service.exception.RequestStructureNotValid;
import com.soms.service.exception.ResourceNotFoundException;
import com.soms.service.repository.OrderRepository;
@Component
public class OrderBusinessService  {

	@Autowired
    private OrderRepository orderRepo;
	
	public List<Order> getAllOrders(){
		return orderRepo.findAll();
		
	}
	
	public Order getOrder(Long id) {
		return orderRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("order does not exist"));
	}
	
	public Order createOrder(Order order) {
		if(order.getId()!=null || order.getDate()!=null) {
			throw new RequestStructureNotValid("Invalid request just provide status and totalPrice");
		}
		order.setDate(new Date());
		return orderRepo.save(order);
	}
	public Order updateOrder(Order order, Long id) {
		Order existingOrder = this.orderRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("order does not exist"));
		existingOrder.setStatus(order.getStatus());
		existingOrder.setTotalPrice(order.getTotalPrice());
		return this.orderRepo.save(existingOrder);
	}
	public String deleteOrders(Long id) {
		Order existingOrder = this.orderRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("order does not exist"));
		this.orderRepo.delete(existingOrder);
		return "Deletion done";
	}
	
}
