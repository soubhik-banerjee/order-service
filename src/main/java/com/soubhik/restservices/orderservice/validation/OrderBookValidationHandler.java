package com.soubhik.restservices.orderservice.validation;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.data.OrderExecutionVO;
import com.soubhik.restservices.orderservice.exception.OrderException;
import com.soubhik.restservices.orderservice.util.Constants;
import com.soubhik.restservices.orderservice.util.ResponseMessage;

//We can make this class more generic by using custom annotations on methods 
//and invoking only those validation methods which are annotated at runtime

@Component("orderBookValidationHandler")
@Scope(value="request", proxyMode =ScopedProxyMode.TARGET_CLASS)
public class OrderBookValidationHandler {

	OrderBook orderBook;
	
	public void validateAdd(OrderBook orderBook) {
		this.orderBook = orderBook;
		checkIfAlreadyPresent();
	}
	
	public void validateUpdate(OrderBook orderBook) {
		this.orderBook = orderBook;
		checkIfNotFound();
	}
	
	public void validateClose(OrderBook orderBook) {
		this.orderBook = orderBook;
		checkIfNotFound();
		checkIfOrderBookClosed();
	}
	
	public void validateExecute(OrderBook orderBook,OrderExecutionVO orderExecutionVO) {
		this.orderBook = orderBook;
		checkIfNotFound();
		checkIfOrderBookOpenOrExecuted();
	}
	
	public void validateExecutionPriceMismatch(OrderBook orderBook,OrderExecutionVO orderExecutionVO) {
		this.orderBook = orderBook;
		checkIfExecutionPriceMismatch(orderBook,orderExecutionVO);
	}
	
	public void validateAddOrder(OrderBook orderBook,Order order,Order currentOrder) {
		this.orderBook = orderBook;
		checkIfNotFound();
		checkIfOrderBookClosed();
		checkIfOrderValid(order,currentOrder);
	}
	
	private void checkIfAlreadyPresent() {
		if(orderBook!=null && orderBook.getStatus().
				equals(Constants.ORDER_BOOK_OPEN)) {
			throw new OrderException.OrderBookAlreadyPresent(String.format(ResponseMessage.ORDER_BOOK_ALREADY_PRESENT, 
					orderBook.getOrderBookId()));
		}
	}
	
	private void checkIfNotFound() {
		if(orderBook==null) {
			throw new OrderException.OrderBookNotFound(ResponseMessage.ORDER_BOOK_NOT_FOUND);
		}
	}
	
	private void checkIfOrderBookClosed() {
		if(!Constants.ORDER_BOOK_OPEN.equals(orderBook.getStatus())) {
			throw new OrderException.OrderBookClosed(ResponseMessage.ORDER_BOOK_CLOSED);
		}
	}
	
	private void checkIfOrderBookOpenOrExecuted() {
		if(Constants.ORDER_BOOK_OPEN.equals(orderBook.getStatus())) {
			throw new OrderException.OrderBookNotExecutable(ResponseMessage.EXECUTION_NOT_POSSIBLE_FOR_OPEN_ORDERBOOK);
		}
		
		if(Constants.ORDER_BOOK_EXECUTED.equals(orderBook.getStatus())) {
			throw new OrderException.OrderBookNotExecutable(ResponseMessage.EXECUTION_NOT_POSSIBLE_FOR_EXECUTED_ORDERBOOK);
		}
	}
	
	private void checkIfOrderValid(Order order,Order currentOrder) {
		if(order==null && currentOrder.getOrderId()!=0) {
			throw new OrderException.OrderAlreadyAdded(ResponseMessage.INVALID_ORDER);
		}
		if(order!=null){
			throw new OrderException.OrderAlreadyAdded(ResponseMessage.ORDER_ADD_FAIL);
		}
	}
	
	private void checkIfExecutionPriceMismatch(OrderBook orderBook,OrderExecutionVO orderExecutionVO) {
		if(orderBook.getExecutionPrice().compareTo(orderExecutionVO.getPrice())!=0) {
			throw new OrderException.ExecutionPriceMismatch(ResponseMessage.EXECUTION_PRICE_MISMATCH);
		}
	}
	
}
