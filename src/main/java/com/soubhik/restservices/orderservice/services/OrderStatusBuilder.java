package com.soubhik.restservices.orderservice.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.data.OrderStat;
import com.soubhik.restservices.orderservice.util.Constants;

@Component("orderStatusBuilder")
public class OrderStatusBuilder {

	public List<OrderStat> buildOrderStatus(List<OrderBook> orderBooks) {
		
		List<OrderStat> orderStatus = new ArrayList<>();
		
		orderBooks.stream()
		.forEach(orderBook->orderStatus.add(processOrder(orderBook)));
		
		return orderStatus;
	}
	
	private OrderStat processOrder(OrderBook orderBook) {
		long totalDemand = 0;
		long highestOrder = 0L;
		long lowestOrder = 0L;
		Date firstOrderDate = null;
		Date lastOrderDate = null;
		long totalValidOrders = 0L;
		long accumaulatedExecution = 0L;
		
		Map<BigDecimal,Long> priceDemandTable = new HashMap<>();
		
		if(orderBook.getFirstOrder()!=null) {
			highestOrder = orderBook.getFirstOrder().getQuantity();
			lowestOrder = orderBook.getFirstOrder().getQuantity();
			firstOrderDate = orderBook.getFirstOrder().getEntryDate();
			lastOrderDate = orderBook.getFirstOrder().getEntryDate();
			
			for(Order order:orderBook.getOrders()) {
				totalDemand = totalDemand+order.getQuantity();
				highestOrder = order.getQuantity()>highestOrder?order.getQuantity():highestOrder;
				lowestOrder = order.getQuantity()<lowestOrder?order.getQuantity():lowestOrder;
				firstOrderDate = order.getEntryDate().compareTo(firstOrderDate)<0?order.getEntryDate():firstOrderDate;
				lastOrderDate = order.getEntryDate().compareTo(lastOrderDate)>0?order.getEntryDate():lastOrderDate;
				
				if(Constants.VALID_ORDER
						.equals(order.getOrderAttributes().getOrderStatus())) {
					totalValidOrders++;
					accumaulatedExecution = accumaulatedExecution+order.getOrderAttributes().getExecutionQuantity();
				}
							
				if(Constants.LIMIT_ORDER
						.equals(order.getOrderAttributes().getOrderType())) {
					priceDemandTable.put(order.getPrice(), order.getQuantity());
				}
			}
		}
		
		
		OrderStat orderStat = new OrderStat();
		orderStat.setOrderBookId(orderBook.getOrderBookId());
		orderStat.setTotalOrders(orderBook.getOrders().size());
		orderStat.setTotalDemand(totalDemand);
		orderStat.setHighestDemand(highestOrder);
		orderStat.setLowestDemand(lowestOrder);
		orderStat.setFirstOrderDate(firstOrderDate);
		orderStat.setLastOrderDate(lastOrderDate);
		orderStat.setValidOrders(totalValidOrders);
		orderStat.setInvalidOrders(orderBook.getExecutionPrice()!=null?orderStat.getTotalOrders()-totalValidOrders:0);
		orderStat.setValidDemnad(orderBook.getValidDemandQuantity());
		orderStat.setInValidDemand(orderBook.getExecutionPrice()!=null?totalDemand-orderStat.getValidDemnad():0);
		orderStat.setExecutionPrice(orderBook.getExecutionPrice());
		orderStat.setAccumulatedExecutionQuantity(accumaulatedExecution);
		orderStat.setPriceDemandTable(priceDemandTable);
		
		
		return orderStat;
	}
	 
	

}
