package com.soms.service;
/*
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.soms.service.businessService.OrderBusinessService;
import com.soms.service.entity.Order;
import com.soms.service.repository.OrderRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
class SecureOrderManagementServiceApplicationTests {
	
	@Autowired
	MockMvc mockmvc;
	
	@MockBean
	private OrderRepository orderRepo;
	
	@MockBean
	private OrderBusinessService orderbusinessService;
	
	 private List<Order> orderList;
	 
	 String mockOrderJson = "{\r\n" + 
	 		"     \"id\" :1,\r\n" + 
	 		"    \"status\": \"C\",\r\n" + 
	 		"    \"totalPrice\": 56.99,\r\n" + 
	 		"    \"date\": \"2013-01-01T00:00:00.000+0000\"\r\n" + 
	 		"}";
	    
	    @BeforeEach                           
	    void setUp() {                               
	       this.orderList = new ArrayList<>();  
	       Order order1 = new Order();
	       order1.setId(1);
	       order1.setStatus("N");
	       order1.setDate(new Date());
	       order1.setTotalPrice(new BigDecimal("3.22"));
	      
	       orderList.add(order1);
	 
	    }

	@Test
	void contextLoads() throws Exception {
		 
		Mockito.when(orderbusinessService.getAllOrders()).thenReturn(orderList);
		
		MvcResult mvcResult = mockmvc.perform(MockMvcRequestBuilders.get("/somsapi/orders").accept(MediaType.APPLICATION_JSON)).andReturn();
		
		System.out.println(mvcResult.getResponse());
		Mockito.verify(orderbusinessService).getAllOrders();
	}
	
	@Test
	void getAllOrdersTest() throws Exception {
		 
		Mockito.when(orderbusinessService.getAllOrders()).thenReturn(orderList);
		
		//MvcResult mvcResult = mockmvc.perform(MockMvcRequestBuilders.get("/somsapi/orders").accept(MediaType.APPLICATION_JSON)).andReturn();
		
		//System.out.println(mvcResult.getResponse());
		mockmvc.perform(get("/somsapi/orders")).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(orderList.size())));
		Mockito.verify(orderbusinessService).getAllOrders();
	}
	
	@Test
	void getSingleOderTest() throws Exception {
		MvcResult mvcResult = mockmvc.perform(MockMvcRequestBuilders.get("/somsapi/orders/1").accept(MediaType.APPLICATION_JSON)).andReturn();
		
		System.out.println(mvcResult.getResponse());
		
		Mockito.verify(orderRepo).findById(1);
	}
	
	@Test
	void createOderTest() throws Exception {
		Order order2 = new Order();
		order2.setId(2);
		order2.setStatus("C");
		order2.setDate(new Date());
		order2.setTotalPrice(new BigDecimal("3.22"));
	    Mockito.when(
	    		orderRepo.save(
						Mockito.any(Order.class))).thenReturn(order2);
	    RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/somsapi/orders")
				.accept(MediaType.APPLICATION_JSON).content(mockOrderJson)
				.contentType(MediaType.APPLICATION_JSON);

	    MvcResult result = mockmvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}
	
	@Test
    public void deleteOrderTest() throws Exception {
		Order order3 = new Order();
		order3.setId(2);
		order3.setStatus("N");
		order3.setDate(new Date());
		order3.setTotalPrice(new BigDecimal("3.22")); 
		Mockito.when(
	    		orderbusinessService.deleteOrders(
						order3)).thenReturn("Deletion Done");
        MvcResult mvcResult = mockmvc.perform(MockMvcRequestBuilders.delete("/somsapi/orders/2").accept(MediaType.APPLICATION_JSON)).andReturn();
		
        
    }
	

}
*/