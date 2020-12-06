package com.soms.service.businessService;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.soms.service.entity.OrderLoad;
import com.soms.service.repository.OrderLoadRepository;
@Component
public class OrderInLoadService {
	
	@Autowired
	OrderLoadRepository orderRepo;
	
	 public void save(MultipartFile file) throws NumberFormatException, ParseException {
		    try {
		      List<OrderLoad> orders = CSVHelper.csVtoOrders(file.getInputStream());
		      orderRepo.saveAll(orders);
		    } catch (IOException e) {
		      throw new RuntimeException("fail to store csv data: " + e.getMessage());
		    }
		  }
	
	
	

}
