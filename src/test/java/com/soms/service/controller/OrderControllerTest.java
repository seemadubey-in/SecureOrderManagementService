
/*package com.soms.service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soms.service.businessService.OrderBusinessService;
import com.soms.service.entity.Order;
import com.soms.service.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class OrderControllerTest {

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

	 @Autowired
	 private FilterChainProxy springSecurityFilterChain;

     private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
		          .addFilter(springSecurityFilterChain).build();
		this.orderList = new ArrayList<>();
		Order order1;
		for( int i= 1; i<5; i++) {
			order1 = new Order();
			order1.setId((long) i);
			order1.setStatus("N");
			order1.setDate(new Date());
			order1.setTotalPrice(new BigDecimal(i*10));	
			orderList.add(order1);
		}
		
		
		for (Order o : orderList) {
			System.out.println("Before: " + o);
		}
	}

	
	
	private String obtainAccessToken(String username, String password) throws Exception {
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("client_id", "fooClientIdPassword");
	    params.add("username", username);
	    params.add("password", password);
	    System.out.println("generating token");
	    ResultActions result 
	      = mockMvc.perform(post("/oauth/token")
	        .params(params)
	        .with(httpBasic("fooClientIdPassword","secret"))
	        .accept("application/json;charset=UTF-8"))
	        //.andExpect(status().isOk())
	        ;

	    String resultString = result.andReturn().getResponse().getContentAsString();
System.out.println("result string: " + resultString);
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
//	    System.out.println("The generated token is "+jsonParser.parseMap(resultString).get("access_token").toString());
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}
	
	@Test
	void getAllOrdersCall() throws Exception {
		Mockito.when(orderbusinessService.getAllOrders()).thenReturn(orderList);

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/somsapi/orders")
						.accept(MediaType.APPLICATION_JSON)).andReturn();
	

		System.out.println("getAllOrdersCall status: " + mvcResult.getResponse() + " status: "
				+ mvcResult.getResponse().getStatus());
		//Mockito.verify(orderbusinessService).getAllOrders();
	}

	@Test
	void getAllOrdersTest() throws Exception {

		Mockito.when(orderbusinessService.getAllOrders()).thenReturn(orderList);

		mockMvc.perform(get("/somsapi/orders")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(orderList.size())));
		//Mockito.verify(orderbusinessService).getAllOrders();
	}
	/*
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
*/

