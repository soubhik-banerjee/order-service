package com.soubhik.restservices.orderservice.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderBook {

	private long orderBookId;
	private long instrumentId;
	private String status;
	private List<Order> orders = new ArrayList<>();
	private BigDecimal executionPrice;
	private long validDemandQuantity;
	
	public long getOrderBookId() {
		return orderBookId;
	}

	public void setOrderBookId(long orderBookId) {
		this.orderBookId = orderBookId;
	}

	public long getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(long instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public BigDecimal getExecutionPrice() {
		return executionPrice;
	}

	public long getValidDemandQuantity() {
		return validDemandQuantity;
	}

	public void setExecutionPrice(BigDecimal executionPrice) {
		this.executionPrice = executionPrice;
	}

	public void setValidDemandQuantity(long validDemandQuantity) {
		this.validDemandQuantity = validDemandQuantity;
	}
	
	@JsonIgnore
	public Order getFirstOrder() {
		if(orders.size()>0) {
			return orders.get(0);
		}
		else {
			return null;
		}
		
	}
	
}
