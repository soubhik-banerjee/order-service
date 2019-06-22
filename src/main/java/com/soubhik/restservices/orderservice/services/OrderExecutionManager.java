package com.soubhik.restservices.orderservice.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soubhik.restservices.orderservice.dao.OrderBookDao;
import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderAttributes;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.data.OrderExecutionVO;
import com.soubhik.restservices.orderservice.util.Constants;
import com.soubhik.restservices.orderservice.validation.OrderBookValidationHandler;

@Component("orderExecutionManager")
public class OrderExecutionManager {

	@Autowired 
	OrderBookDao orderBookDao;
	
	@Autowired
	private OrderBookValidationHandler validationHandler;
	
	Lock executionLock = new ReentrantLock();
	Object monitor = new Object();
	
	public void executeOrder(OrderExecutionVO executionVO,OrderBook orderBook) {
		if(orderBook.getExecutionPrice()==null) {
			executionLock.lock();
			try {
				if(orderBook.getExecutionPrice()==null) { //double check to ensure the calculations happen only once, since for the first request if there are concurrent requests
					orderBook.setExecutionPrice(executionVO.getPrice());
						orderBook.getOrders().forEach(order->{
							OrderAttributes orderAttr = order.getOrderAttributes();
							if(Constants.LIMIT_ORDER.equals(orderAttr.getOrderType()) && order.getPrice().compareTo(orderBook.getExecutionPrice())<0) {
								orderAttr.setOrderStatus(Constants.INVALID_ORDER);
							}
							else {
								//Else block can't be avoided since valid demand quantity needs to be calculated, hence keeping.
								//Market orders are valid by default. Could have moved it to add order method. But doesn't make sense since we are anyways calculating
								//status for valid limit order
								orderAttr.setOrderStatus(Constants.VALID_ORDER);
								orderBook.setValidDemandQuantity(orderBook.getValidDemandQuantity()+order.getQuantity());
							}
	
							orderBookDao.addUpdateOrderAttributes(orderAttr);
							orderBookDao.updateOrderBook(orderBook);
						});
				}
				
			}
			finally {
				executionLock.unlock();
			}
		}
		
		if(orderBook.getExecutionPrice()==null) {
			OrderBook savedOrderBook = orderBookDao.findOrderBookByInstID(orderBook.getInstrumentId());
			execute(executionVO,savedOrderBook);
		}
		else {
			execute(executionVO,orderBook);
		}
	}
	
	private void execute(OrderExecutionVO executionVO,OrderBook orderBook) {
		
		validationHandler.validateExecutionPriceMismatch(orderBook, executionVO);
		
		float distributionFactor = (float)executionVO.getQuantity()/(float)orderBook.getValidDemandQuantity();
		long totalCurrentOrderBookExecutionQuatity=0L;
		boolean hasMetValidDemand=false;
		long newExecQuantity = executionVO.getQuantity();
		
		//In real scenario this synchronization is not required since multiple updates will be taken care by transactions. So just this block needs to be removed.
		synchronized(monitor) {
			for(Order order:orderBook.getOrders()) {
				OrderAttributes attr = order.getOrderAttributes();
				
				if(Constants.VALID_ORDER
						.equals(attr.getOrderStatus())) {
					
					long currentDistribution = new BigDecimal(distributionFactor*order.getQuantity())
							.setScale(1,RoundingMode.CEILING).longValue();
					attr.setExecutionQuantity(attr.getExecutionQuantity()+currentDistribution);
					orderBookDao.addUpdateOrderAttributes(attr);
					
					totalCurrentOrderBookExecutionQuatity = totalCurrentOrderBookExecutionQuatity + attr.getExecutionQuantity();
					newExecQuantity = newExecQuantity - currentDistribution;
					
					if(totalCurrentOrderBookExecutionQuatity>=orderBook.getValidDemandQuantity()) {
						hasMetValidDemand=true;
						break;
					}
				}
			}
			
			//If even after distributing based on the distribution factor there is some excess, it will be assigned to the order with highest quantity
			if(newExecQuantity>0) {
				//This will get the order with highest quantity order since order book orders are sorted during fetch
				Order order = orderBookDao.findValidOrderWithHighestQuantity(orderBook.getOrderBookId());
				OrderAttributes orderAttributes = order.getOrderAttributes(); 
				orderAttributes.setExecutionQuantity(orderAttributes.getExecutionQuantity()+newExecQuantity);
				orderBookDao.addUpdateOrderAttributes(orderAttributes);
			}
			
			if(hasMetValidDemand) {
				orderBook.setStatus(Constants.ORDER_BOOK_EXECUTED);
				orderBookDao.updateOrderBook(orderBook);
			}
		}
	}
}
