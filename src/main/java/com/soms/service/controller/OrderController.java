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

import com.soms.service.businessService.OrderBusinessService;
import com.soms.service.businessService.ResponseMessage;
import com.soms.service.entity.Order;


@RestController
@RequestMapping("/somsapi/orders")
public class OrderController {


	
	@Autowired
	private OrderBusinessService orderbusinessService;


	//get all orders
	@GetMapping
	public List<Order> getallOrders(){
		return this.orderbusinessService.getAllOrders();
	}
	// get order by id
	@GetMapping("/{id}")
	public Order getOrderbyId(@PathVariable(value = "id") Long id) {
		return this.orderbusinessService.getOrder(id);
	}
	//create order
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Order createOrder(@Valid @RequestBody Order order) {
		return this.orderbusinessService.createOrder(order);
	}
	//update order
	@PutMapping("/{id}")
	public Order updateOrder(@Valid @RequestBody Order order, @PathVariable (value ="id") Long id) {
		return this.orderbusinessService.updateOrder(order, id);
	}
	//delete order by id
	@DeleteMapping("/{id}")
    public  ResponseEntity<ResponseMessage> deleteOrder(@PathVariable(value ="id") Long id){
		this.orderbusinessService.deleteOrders(id);
		return ResponseEntity.ok().body(new ResponseMessage("Order with order id "+id+" Deleted successfully"));
	}
}
