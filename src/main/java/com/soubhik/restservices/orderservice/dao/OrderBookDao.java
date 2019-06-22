package com.soubhik.restservices.orderservice.dao;

import java.util.List;

import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderAttributes;
import com.soubhik.restservices.orderservice.data.OrderBook;

public interface OrderBookDao {

	OrderBook createOrderBook(Long instrumentId);

	OrderBook closeOrderBook(Long orderBookId);
	
	OrderBook updateOrderBook(OrderBook orderBook);

	OrderBook findOrderBookByID(Long orderBookId);

	OrderBook findOrderBookByInstID(Long instrumentId);

	Order addOrder(Order order);
	
	Order findOrderById(Long orderId);
	
	Order findValidOrderWithHighestQuantity(Long orderBookId);

	OrderAttributes addUpdateOrderAttributes(OrderAttributes orderAttributes);
	
	List<OrderBook> findOrderBooks();

}
