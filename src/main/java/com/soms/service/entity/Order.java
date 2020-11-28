package com.soms.service.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import com.soms.service.validation.ValidateStatus;

@Entity
@Table(name = "orderTable")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private int id;
	@ValidateStatus(acceptedValues = { "N", "I",
			"C" }, message = "Invalid status,Please provide one valid status from N, I or C")
	@Column(name = "orderStatus")
	private String status;
	@NotNull(message = "Please provide a price")
	@DecimalMin("2.00")
	@Column(name = "orderPrice")
	private BigDecimal totalPrice;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Please provide a date.")
	@Column(name = "orderDate")
	private Date date;

	public Order() {
	}

	public Order(String status, BigDecimal totalPrice, Date date) {
		super();
		this.status = status;
		this.totalPrice = totalPrice;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
