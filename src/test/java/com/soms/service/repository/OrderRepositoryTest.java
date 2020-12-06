package com.soms.service.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soms.service.entity.Order;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {
	@Autowired
	OrderRepository orderRepo;
	@Test
	public void testSave() {
		Order order1 = new Order();
		order1.setId((long) 2);
		order1.setStatus("N");
		order1.setDate(new Date());
		order1.setTotalPrice(new BigDecimal("20.22"));
		Order order2 = orderRepo.save(order1);
		assertNotNull(order2);
		assertEquals("N", order2.getStatus());
		assertEquals(new BigDecimal("20.22"), order2.getTotalPrice());
	}
	
	@Test
	public void testfindAll() {
		Order order1 = new Order();
		order1.setId((long) 2);
		order1.setStatus("N");
		order1.setDate(new Date());
		order1.setTotalPrice(new BigDecimal("20.22"));
		orderRepo.save(order1);
		assertNotNull(orderRepo.findAll());
	}
	
	@Test
	public void testfindById() {
		Order order1 = new Order();
		final Long id = 2L;
		order1.setId(id);
		order1.setStatus("N");
		order1.setDate(new Date());
		order1.setTotalPrice(new BigDecimal("20.22"));
		orderRepo.save(order1);
		Order order2 = orderRepo.findById(id).get();
		assertEquals(order1.getStatus(), order2.getStatus());
	}
	
	@Test
	public void testdelete() {
		Order orderDelete = new Order();
		final Long id = 3L;
		orderDelete.setId(id);
		orderDelete.setStatus("N");
		orderDelete.setDate(new Date());
		orderDelete.setTotalPrice(new BigDecimal("20.22"));
		orderRepo.delete(orderDelete);
	}
	
	

}
