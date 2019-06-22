package com.soubhik.restservices.orderservice.data;

public class OrderAttributes {

	private long orderAttributeId;
	private long orderId;
	private String orderType;
	private String orderStatus;
	private long executionQuantity;
	
	public long getOrderAttributeId() {
		return orderAttributeId;
	}

	public void setOrderAttributeId(long orderAttributeId) {
		this.orderAttributeId = orderAttributeId;
	}

	public long getOrderId() {
		return orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public long getExecutionQuantity() {
		return executionQuantity;
	}

	public void setExecutionQuantity(long executionQuantity) {
		this.executionQuantity = executionQuantity;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
