package com.soubhik.restservices.orderservice.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

//This class can not be immutable in actual scenario. The id has to be updated during insertion and also
//classes as entity should ideally not be final. Immutability has to be handled differently by restricting creation of same order in DB
//In short no end point is exposed to update an order
public class Order implements Serializable {

	private long orderId;
	
	@NotNull(message="Please pass a valid order book id")
	private Long orderBookId;
	
	@Min(value=1L,message="Quantity is invalid")
	private Long quantity;
	
	@Min(value=0,message="Price should be equal or greater than zero")
	private BigDecimal price;
	
	private Date entryDate;
	
	@Null(message="Order attributes should not be set. It is computed")
	private OrderAttributes orderAttributes;

	/**
	 * Order Id is the only mutable field
	 * @param orderId
	 */
	public void setOrderId(long orderId) {
			this.orderId = orderId;
	}

	public void setOrderBookId(Long orderBookId) {
		this.orderBookId = orderBookId;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public long getOrderId() {
		return orderId;
	}

	public Long getOrderBookId() {
		return orderBookId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public OrderAttributes getOrderAttributes() {
		return orderAttributes;
	}

	public void setOrderAttributes(OrderAttributes orderAttributes) {
		this.orderAttributes = orderAttributes;
	}

}
