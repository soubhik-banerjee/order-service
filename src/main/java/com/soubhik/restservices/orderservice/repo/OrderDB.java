package com.soubhik.restservices.orderservice.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderAttributes;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.util.Constants;

/**
 * This is the mock up of an actual DB. 
 * All the methods written will be replaced by actual DB transaction using queries/ORM.
 *
 */

@Component("orderDB")
public class OrderDB {

	private Map<Long,OrderBook> orderBookTable = new HashMap<>();
	private Map<Long,Order> orderTable  = new HashMap<>();
	private Map<Long,OrderAttributes> orderAttributesTable = new HashMap<>(); 

	public OrderBook addOrderBook(OrderBook orderBook) {

		orderBook.setOrderBookId(Long.valueOf(orderBookTable.size()+1));
		orderBookTable.put(orderBook.getInstrumentId(), orderBook);

		return orderBook;
	}

	public OrderBook updateOrderBook(OrderBook orderBook) {
		orderBookTable.put(orderBook.getInstrumentId(), orderBook);

		return orderBook;
	}

	//In actual scenario, this should go in a transaction or if using JPA, could be cascaded using a one to one relationship
	public Order addOrder(Order order) {
		//In real scenario this will be DB sequence based
				long calculatedOrderId = orderTable.size()+1;
				
				order.getOrderAttributes().setOrderAttributeId(Long.valueOf(orderAttributesTable.size()+1));
				order.getOrderAttributes().setOrderId(calculatedOrderId);
				
				addUpdateOrderAttributes(order.getOrderAttributes());
				
				order.setOrderId(calculatedOrderId);
				orderTable.put(order.getOrderId(),order);
				
				OrderBook orderBook = getOrderBookById(order.getOrderBookId());
				orderBook.getOrders().add(order);
				orderBookTable.put(orderBook.getInstrumentId(), orderBook);

				return order;
	}

	public OrderAttributes addUpdateOrderAttributes(OrderAttributes orderAttribute) {
		orderAttributesTable.put(orderAttribute.getOrderAttributeId(),orderAttribute);
		return orderAttribute;
	} 

	// This is similar to select * from OrderBook where instID=?
	public OrderBook getOrderBookByInstId(Long instrumentId) {
		OrderBook orderBook = orderBookTable.get(instrumentId);
		List<Order> orders = null;

		if(orderBook!=null) {
			orders = orderBook.getOrders().stream()
					.sorted((o1,o2)->Long.valueOf(o1.getQuantity()).compareTo(Long.valueOf(o2.getQuantity())))
					.collect(Collectors.toList());

			orderBook.setOrders(orders);
		}

		return orderBook;
	}

	// This is similar to select * from OrderBook where orderBookID=?
	public OrderBook getOrderBookById(Long orderBookId) {
		Optional<OrderBook> orderBookOptional = orderBookTable.values().stream()
				.filter(ob->ob.getOrderBookId()==orderBookId.longValue())
				.findFirst();

		List<Order> orders = null;
		OrderBook orderBook = orderBookOptional.orElse(null);

		if(orderBook!=null) {
			orders = orderBook.getOrders().stream()
					.sorted((o1,o2)->Long.valueOf(o1.getQuantity()).compareTo(Long.valueOf(o2.getQuantity())))
					.collect(Collectors.toList());

			orderBook.setOrders(orders);
		}

		return orderBook;
	}
	
	//This is similar to select * from Order where order_id=?
	public Order getOrderById(Long orderId) {
		return orderTable.get(orderId);
	}
	
	//This is similar to select * from Order where order_book_id=? and status like '%Valid' order by quantity
	public Order getValidOrderWithHighestQuantity(Long orderBookId) {
		OrderBook orderBook = getOrderBookById(orderBookId);
		List<Order> orderList = orderBook.getOrders();

		ListIterator<Order> it = orderList.listIterator(orderList.size());
		
		Order order = Stream.generate(it::previous).limit(orderList.size())
		.filter(o->Constants.VALID_ORDER.equals(o.getOrderAttributes().getOrderStatus()))
		.findFirst().get();
		
		return order;
	}
	
	//This is similar to select * from Order_BOOK
	public List<OrderBook> getOrderBooks(){
		List<OrderBook> orderBooks = new ArrayList<>();
		for(OrderBook orderBook : orderBookTable.values()) {
			orderBooks.add(orderBook);
		}
		return orderBooks;
	}
}
