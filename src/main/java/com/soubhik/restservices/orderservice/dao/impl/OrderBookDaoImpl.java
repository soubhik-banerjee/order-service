package com.soubhik.restservices.orderservice.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.soubhik.restservices.orderservice.dao.OrderBookDao;
import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderAttributes;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.repo.OrderDB;
import com.soubhik.restservices.orderservice.util.Constants;

@Repository("orderBookDao")
public class OrderBookDaoImpl implements OrderBookDao {  

	@Autowired
	private OrderDB orderDB;

	@Override
	public OrderBook createOrderBook(Long instrumentId) {
		return orderDB.addOrderBook(getBlankOrderBook(instrumentId));
	}

	@Override
	public OrderBook closeOrderBook(Long orderBookId) {
		OrderBook orderBook = findOrderBookByID(orderBookId);
		orderBook.setStatus(Constants.ORDER_BOOK_CLOSED);
		orderDB.updateOrderBook(orderBook);
		return orderBook;
	}

	@Override
	public Order addOrder(Order order) {
		return orderDB.addOrder(order);
	}

	private OrderBook getBlankOrderBook(Long instrumentId) {
		OrderBook orderBook = new OrderBook();
		orderBook.setInstrumentId(instrumentId);
		orderBook.setStatus(Constants.ORDER_BOOK_OPEN);

		return orderBook;
	}

	@Override
	public OrderBook findOrderBookByID(Long orderBookId) {
		OrderBook orderBook=null;
		orderBook = orderDB.getOrderBookById(orderBookId);

		return orderBook;
	}

	@Override
	public OrderBook findOrderBookByInstID(Long instrumentId) {
		return orderDB.getOrderBookByInstId(instrumentId);
	}

	@Override
	public Order findOrderById(Long orderId) {
		return orderDB.getOrderById(orderId);
	}

	@Override
	public OrderAttributes addUpdateOrderAttributes(OrderAttributes orderAttributes) {
		return orderDB.addUpdateOrderAttributes(orderAttributes);
	}

	@Override
	public OrderBook updateOrderBook(OrderBook orderBook) {
		return orderDB.updateOrderBook(orderBook);
	}

	@Override
	public Order findValidOrderWithHighestQuantity(Long orderBookId) {

		return orderDB.getValidOrderWithHighestQuantity(orderBookId);
	}


	@Override
	public List<OrderBook> findOrderBooks() {
		return orderDB.getOrderBooks();
	}

}
