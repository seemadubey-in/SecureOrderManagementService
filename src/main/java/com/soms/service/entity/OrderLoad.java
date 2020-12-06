package com.soms.service.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderTable")
public class OrderLoad {
	@Id
	@Column(name = "orderId")
	private Long id;
	@Column(name = "orderStatus")
	private String status;
	@Column(name = "orderPrice")
	private BigDecimal totalPrice;
	@Column(name = "orderDate")
	private Date date;
    public OrderLoad() {
    	
    }
	
	
	public OrderLoad(Long id, String status, BigDecimal totalPrice, Date date) {
		super();
		this.id = id;
		this.status = status;
		this.totalPrice = totalPrice;
		this.date = date;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
