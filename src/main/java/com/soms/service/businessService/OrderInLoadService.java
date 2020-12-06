package com.soms.service.businessService;


import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.soms.service.entity.Order;
import com.soms.service.repository.OrderRepository;
@Component
public class OrderInLoadService {
	
	@Autowired
	OrderRepository orderRepo;
	
	 public void save(MultipartFile file) throws NumberFormatException, ParseException {
		    try {
		      List<Order> orders = CSVHelper.csVtoOrders(file.getInputStream());
		      for(Order order : orders) {
		    	  order.setDate(new Date());
		      }
		      orderRepo.saveAll(orders);
		    } catch (IOException e) {
		      throw new RuntimeException("fail to store csv data: " + e.getMessage());
		    }
		  }
	
	
	 public void deleteAllRecords() {
		 orderRepo.deleteAll();
	 }

}
