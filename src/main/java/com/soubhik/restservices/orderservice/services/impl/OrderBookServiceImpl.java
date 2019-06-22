package com.soubhik.restservices.orderservice.services.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soubhik.restservices.orderservice.dao.OrderBookDao;
import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderAttributes;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.data.OrderExecutionVO;
import com.soubhik.restservices.orderservice.data.OrderStat;
import com.soubhik.restservices.orderservice.services.OrderBookService;
import com.soubhik.restservices.orderservice.services.OrderExecutionManager;
import com.soubhik.restservices.orderservice.services.OrderStatusBuilder;
import com.soubhik.restservices.orderservice.util.Constants;
import com.soubhik.restservices.orderservice.validation.OrderBookValidationHandler;

@Service("orderBookService")
public class OrderBookServiceImpl implements OrderBookService { 

	@Autowired
	private OrderBookDao orderBookDao;

	@Autowired
	private OrderBookValidationHandler validationHandler;

	@Autowired
	private OrderExecutionManager orderExecutionManager;

	@Autowired
	private OrderStatusBuilder orderStatusBuilder;

	private Lock orderBookLock = new ReentrantLock();

	@Override
	public OrderBook createOrderBook(long instrumentId) {
		OrderBook createOrderBook;

		orderBookLock.lock(); //Lock is used so that two concurrent request should not be able to open an order book for the same instrument

		try {
			validationHandler.validateAdd(orderBookDao.findOrderBookByInstID(instrumentId));
			createOrderBook = orderBookDao.createOrderBook(instrumentId);
		}
		finally {
			orderBookLock.unlock();
		}

		return createOrderBook;
	}

	@Override
	public OrderBook closeOrderBook(long orderBookId) {
		orderBookLock.lock(); //Lock is used so that two concurrent request should not be able to close an order book for the same instrument
		OrderBook orderBook;
		try {
			validationHandler.validateClose(orderBookDao.findOrderBookByID(orderBookId));
			orderBook = orderBookDao.closeOrderBook(orderBookId);
		}
		finally {
			orderBookLock.unlock();
		}
		return orderBook;
	}

	@Override
	public Order addOrder(Order order) { 
		OrderBook orderBook = null;
		order.setEntryDate(new Date());
		orderBook = orderBookDao.findOrderBookByID(order.getOrderBookId());
		Order savedOrder = orderBookDao.findOrderById(order.getOrderId());
		
		validationHandler.validateAddOrder(orderBook, savedOrder,order);

		OrderAttributes attr = new OrderAttributes();
		attr.setOrderType(order.getPrice()==null || order.getPrice().longValue()==0L?Constants.MARKET_ORDER
				:Constants.LIMIT_ORDER);
		
		order.setOrderAttributes(attr);

		return orderBookDao.addOrder(order);
	}

	@Override
	public boolean executeOrderBook(OrderExecutionVO orderExecutionVO, long orderBookId) {
		OrderBook orderBook = getOrderBookByID(orderBookId);
		validationHandler.validateExecute(orderBook,orderExecutionVO);

		orderExecutionManager.executeOrder(orderExecutionVO, orderBook);

		return true;
	}

	@Override
	public List<OrderStat> getOrderStatistics() {
		List<OrderBook> orderBooks = orderBookDao.findOrderBooks();
		return orderStatusBuilder.buildOrderStatus(orderBooks);
	}

	@Override
	public OrderBook getOrderBookByID(long orderBookId) {
		return orderBookDao.findOrderBookByID(orderBookId);
	}

	@Override
	public OrderBook getOrderBookByInstID(long instrumentId) {
		return orderBookDao.findOrderBookByInstID(instrumentId);
	}

	@Override
	public Order getOrder(long orderId) {
		return orderBookDao.findOrderById(orderId);
	}

}
