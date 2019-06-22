package com.soubhik.restservices.orderservice.data;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderExecutionVO {

	@Min(value=1,message="Quantity should be greater than zero")
	@NotNull(message="Quantity can not be null")
	private Long quantity;
	
	@NotNull(message="Price can not be null")
	@Min(value=1,message="Price should be greater than zero")
	private BigDecimal price;
	
	public Long getQuantity() {
		return quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
