package com.soms.service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soms.service.businessService.OrderBusinessService;
import com.soms.service.entity.Order;
import com.soms.service.repository.OrderRepository;

@RunWith(SpringRunner.class)
//@Configuration
//@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, OAuth2AutoConfiguration.class, OAuth2AccessToken.class, ResourceServerTokenServicesConfiguration.class})
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

	@Autowired
	MockMvc mockmvc;

	@MockBean
	private OrderRepository orderRepo;
	@MockBean
	private OrderBusinessService orderbusinessService;

	@Autowired
	private ObjectMapper objectMapper;

	private List<Order> orderList;
	String mockOrderJson = "{\r\n" + "     \"id\" :1,\r\n" + "    \"status\": \"C\",\r\n"
			+ "    \"totalPrice\": 56.99,\r\n" + "    \"date\": \"2013-01-01T00:00:00.000+0000\"\r\n" + "}";

	@Autowired
	WebApplicationContext context;

	@BeforeEach
	public void setUp() {
		mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
		this.orderList = new ArrayList<>();
		Order order1 = new Order();
		order1.setId((long) 1);
		order1.setStatus("N");
		order1.setDate(new Date());
		order1.setTotalPrice(new BigDecimal("3.22"));

		orderList.add(order1);

		order1.setId((long) 2);
		order1.setStatus("N");
		order1.setDate(new Date());
		order1.setTotalPrice(new BigDecimal("20.22"));
		orderList.add(order1);

		order1.setId((long) 3);
		order1.setStatus("N");
		order1.setDate(new Date());
		order1.setTotalPrice(new BigDecimal("33.22"));

		orderList.add(order1);
		for (Order o : orderList) {
			System.out.println("Before: " + o);
		}
	}

	@Test
	void getAllOrdersCall() throws Exception {

		Mockito.when(orderbusinessService.getAllOrders()).thenReturn(orderList);

		MvcResult mvcResult = mockmvc
				.perform(MockMvcRequestBuilders.get("/somsapi/orders").accept(MediaType.APPLICATION_JSON)).andReturn();

		System.out.println("getAllOrdersCall status: " + mvcResult.getResponse() + " status: "
				+ mvcResult.getResponse().getStatus());
		Mockito.verify(orderbusinessService).getAllOrders();
	}

	@Test
	void getAllOrdersTest() throws Exception {

		Mockito.when(orderbusinessService.getAllOrders()).thenReturn(orderList);

		mockmvc.perform(get("/somsapi/orders")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(orderList.size())));
		Mockito.verify(orderbusinessService).getAllOrders();
	}

	@Test
	void shouldCreateNewOrder() throws Exception {

		given(orderbusinessService.createOrder(any(Order.class))).willAnswer((invocation) -> invocation.getArgument(0));
		Order order2 = new Order();
		order2.setStatus("N");
		order2.setTotalPrice(new BigDecimal("3.22"));

		this.mockmvc
				.perform(post("/somsapi/orders").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(order2)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.status", is(order2.getStatus())));
	}

	@Test
	void shouldFetchOneOrderById() throws Exception {
		final Long id = 1L;
		Order order3 = new Order();
		order3.setId((long) 1L);
		order3.setStatus("C");
		order3.setDate(new Date());
		order3.setTotalPrice(new BigDecimal("4.22"));

		given(orderbusinessService.getOrder(id)).willReturn(order3);

		this.mockmvc.perform(get("/somsapi/orders/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(order3.getStatus())));
	}

	@Test
	void shouldUpdateOrder() throws Exception {
		final Long id = 1L;
		Order order3 = new Order();
		order3.setId((long) 1L);
		order3.setStatus("I");
		order3.setDate(new Date());
		order3.setTotalPrice(new BigDecimal("4.22"));

		given(orderbusinessService.getOrder(id)).willReturn(order3);
		given(orderbusinessService.updateOrder(order3, id)).willReturn(order3);

		this.mockmvc.perform(put("/somsapi/orders/{id}", order3.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(order3))).andExpect(status().isOk());

	}

	@Test
	void shoulddeleteOrder() throws Exception {
		final Long id = 1L;
		Order order5 = new Order();
		order5.setId((long) 1L);
		order5.setStatus("I");
		order5.setDate(new Date());
		order5.setTotalPrice(new BigDecimal("5.22"));

		given(orderbusinessService.getOrder(id)).willReturn(order5);
		given(orderbusinessService.deleteOrders(order5.getId())).willReturn("Object Deleted");

		this.mockmvc.perform(delete("/somsapi/orders/{id}", order5.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(order5))).andExpect(status().isOk());

	}

}
