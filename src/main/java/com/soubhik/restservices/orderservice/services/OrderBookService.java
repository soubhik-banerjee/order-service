package com.soubhik.restservices.orderservice.services;

import java.util.List;

import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.data.OrderExecutionVO;
import com.soubhik.restservices.orderservice.data.OrderStat;

public interface OrderBookService {
	
	OrderBook createOrderBook(long instrumentId);
	
	OrderBook closeOrderBook(long orderBookId);
	
	OrderBook getOrderBookByID(long orderBookId);
	
	OrderBook getOrderBookByInstID(long instrumentId);
	
	Order addOrder(Order order);
	
	Order getOrder(long orderId);
	
	boolean executeOrderBook(OrderExecutionVO orderExecutionVO,long orderBookId);
	
	List<OrderStat> getOrderStatistics();

}
