package com.soubhik.restservices.orderservice.data;

import java.math.BigDecimal;

public final class OrderResponse {
	
	private String status;
	private long executionQuantity;
	private BigDecimal price;
	private BigDecimal executionPrice;
	
	public OrderResponse() {
		super();
	}
	
	public OrderResponse(Order order,OrderBook orderBook) {
		this.status = order.getOrderAttributes().getOrderStatus();
		this.executionQuantity = order.getOrderAttributes().getExecutionQuantity();
		this.price = order.getPrice();
		this.executionPrice = orderBook.getExecutionPrice();
	}

	public String getStatus() {
		return status;
	}

	public long getExecutionQuantity() {
		return executionQuantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getExecutionPrice() {
		return executionPrice;
	}

}
