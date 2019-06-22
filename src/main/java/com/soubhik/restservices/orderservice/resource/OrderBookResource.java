package com.soubhik.restservices.orderservice.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.data.OrderExecutionVO;
import com.soubhik.restservices.orderservice.data.OrderResponse;
import com.soubhik.restservices.orderservice.data.OrderStat;
import com.soubhik.restservices.orderservice.services.OrderBookService;
import com.soubhik.restservices.orderservice.util.ResponseMessage;

@RestController
public class OrderBookResource {

	@Autowired
	private OrderBookService orderBookService;

	@PostMapping(path="/orderbook/{instId}")
	public ResponseEntity<Object> createOrderBook(@PathVariable long instId){
		OrderBook orderBook = orderBookService.createOrderBook(instId);
		return ResponseEntity.ok().body(String.format(ResponseMessage.ORDER_BOOK_CREATED_SUCCESS, orderBook.getOrderBookId()));
	}

	@PutMapping(path="/orderbook/{orderBookId}")
	public ResponseEntity<Object> closeOrderBook(@PathVariable long orderBookId){
		orderBookService.closeOrderBook(orderBookId);
		return ResponseEntity.ok().body(ResponseMessage.ORDER_BOOK_CLOSED_SUCCESS);

	}

	@PostMapping(path="/orderbook/add")
	public ResponseEntity<Object> addOrderToOrderBook(@Valid @RequestBody Order order){
		Order savedOrder = orderBookService.addOrder(order);
		return ResponseEntity.ok().body(String.format(ResponseMessage.ORDER_ADDED_SUCCESS, savedOrder.getOrderId()));
	}

	@PostMapping(path="/orderbook/{orderBookId}/execute")
	public ResponseEntity<Object> executeOrderBook(@Valid @RequestBody OrderExecutionVO orderExecutionVO,@PathVariable long orderBookId){
		orderBookService.executeOrderBook(orderExecutionVO, orderBookId);
		
		return ResponseEntity.ok().body(ResponseMessage.EXECUTION_SUCCESS);
	}
	

	/**
	 * This method is not required to be implemented in original requirement.
	 * To be used just to validate the created order book for testing purpose only
	 * @return ResponseEntity<Object>
	 */
	/*@GetMapping(path="/orderbook/{orderBookId}")
	public OrderBook getOrderBook(@PathVariable long orderBookId) {
		return orderBookService.getOrderBookByID(orderBookId);

	}*/

	@GetMapping(path="/orderbook/stats")
	public ResponseEntity<Object> getOrderStatistics() {
		List<OrderStat> orderStatus = orderBookService.getOrderStatistics();
		
		return ResponseEntity.ok().body(orderStatus);

	}
	
	@GetMapping(path="/orderbook/{orderBookId}/order/{orderId}")
	public ResponseEntity<Object> getOrder(@PathVariable long orderBookId,@PathVariable long orderId) {
		OrderBook orderBook = orderBookService.getOrderBookByID(orderBookId);
		Order savedOrder = orderBookService.getOrder(orderId);
		
		if(savedOrder==null) {
			return ResponseEntity.ok().body(ResponseMessage.ORDER_NOT_FOUND);
		}
		else {
			OrderResponse orderResponse = new OrderResponse(savedOrder, orderBook);
			return ResponseEntity.ok().body(orderResponse);
		}
	}
	
}
