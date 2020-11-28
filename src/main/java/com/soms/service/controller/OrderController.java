package com.soms.service.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soms.service.entity.Order;
import com.soms.service.exception.ResourceNotFoundException;
import com.soms.service.repository.OrderRepository;

@RestController
@RequestMapping("/somsapi/orders")
public class OrderController {

	@Autowired
	private OrderRepository orderRepo;


	//get all orders
	@GetMapping
	public List<Order> getallOrders(){
		return this.orderRepo.findAll();
	}
	// get order by id
	@GetMapping("/{id}")
	public Order getOrderbyId(@PathVariable(value = "id") int id) {
		return this.orderRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("order does not exist"));
	}
	//create order
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Order createOrder(@Valid @RequestBody Order order) {
		return this.orderRepo.save(order);
	}
	//update order
	@PutMapping("/{id}")
	public Order updateOrder(@Valid @RequestBody Order order, @PathVariable (value ="id") int id) {
		Order existingOrder = this.orderRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("order does not exist"));
		existingOrder.setStatus(order.getStatus());
		existingOrder.setTotalPrice(order.getTotalPrice());
 		existingOrder.setDate(order.getDate());
		return this.orderRepo.save(existingOrder);
	}
	//delete order by id
	@DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable(value ="id") int id){
		Order existingOrder = this.orderRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("order does not exist"));
		this.orderRepo.delete(existingOrder);
		return ResponseEntity.ok().build();
	}
}
