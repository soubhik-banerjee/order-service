package com.soubhik.restservices.orderservice.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class OrderStat {

	private long orderBookId;
	private long totalOrders;
	private long totalDemand;
	private long highestDemand;
	private long lowestDemand;
	private Date firstOrderDate;
	private Date lastOrderDate;
	private long validOrders;
	private long invalidOrders;
	private long validDemnad;
	private long inValidDemand;
	private BigDecimal executionPrice;
	private long accumulatedExecutionQuantity;
	private Map<BigDecimal,Long> priceDemandTable;
	
	public long getOrderBookId() {
		return orderBookId;
	}
	public long getTotalOrders() {
		return totalOrders;
	}
	public long getTotalDemand() {
		return totalDemand;
	}
	public long getHighestDemand() {
		return highestDemand;
	}
	public long getLowestDemand() {
		return lowestDemand;
	}
	public Date getFirstOrderDate() {
		return firstOrderDate;
	}
	public Date getLastOrderDate() {
		return lastOrderDate;
	}
	public long getValidOrders() {
		return validOrders;
	}
	public long getInvalidOrders() {
		return invalidOrders;
	}
	public long getValidDemnad() {
		return validDemnad;
	}
	public long getInValidDemand() {
		return inValidDemand;
	}
	public BigDecimal getExecutionPrice() {
		return executionPrice;
	}
	public long getAccumulatedExecutionQuantity() {
		return accumulatedExecutionQuantity;
	}
	public Map<BigDecimal, Long> getPriceDemandTable() {
		return priceDemandTable;
	}
	public void setOrderBookId(long orderBookId) {
		this.orderBookId = orderBookId;
	}
	public void setTotalOrders(long totalOrders) {
		this.totalOrders = totalOrders;
	}
	public void setTotalDemand(long totalDemand) {
		this.totalDemand = totalDemand;
	}
	public void setHighestDemand(long highestDemand) {
		this.highestDemand = highestDemand;
	}
	public void setLowestDemand(long lowestDemand) {
		this.lowestDemand = lowestDemand;
	}
	public void setFirstOrderDate(Date firstOrderDate) {
		this.firstOrderDate = firstOrderDate;
	}
	public void setLastOrderDate(Date lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}
	public void setValidOrders(long validOrders) {
		this.validOrders = validOrders;
	}
	public void setInvalidOrders(long invalidOrders) {
		this.invalidOrders = invalidOrders;
	}
	public void setValidDemnad(long validDemnad) {
		this.validDemnad = validDemnad;
	}
	public void setInValidDemand(long inValidDemand) {
		this.inValidDemand = inValidDemand;
	}
	public void setExecutionPrice(BigDecimal executionPrice) {
		this.executionPrice = executionPrice;
	}
	public void setAccumulatedExecutionQuantity(long accumulatedExecutionQuantity) {
		this.accumulatedExecutionQuantity = accumulatedExecutionQuantity;
	}
	public void setPriceDemandTable(Map<BigDecimal, Long> priceDemandTable) {
		this.priceDemandTable = priceDemandTable;
	}
}
