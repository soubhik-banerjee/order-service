package com.soubhik.rest.services.orderservice.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.soubhik.restservices.orderservice.dao.OrderBookDao;
import com.soubhik.restservices.orderservice.data.Order;
import com.soubhik.restservices.orderservice.data.OrderBook;
import com.soubhik.restservices.orderservice.exception.OrderException;
import com.soubhik.restservices.orderservice.services.OrderBookService;
import com.soubhik.restservices.orderservice.services.OrderExecutionManager;
import com.soubhik.restservices.orderservice.services.OrderStatusBuilder;
import com.soubhik.restservices.orderservice.services.impl.OrderBookServiceImpl;
import com.soubhik.restservices.orderservice.util.Constants;
import com.soubhik.restservices.orderservice.validation.OrderBookValidationHandler;

@RunWith(SpringRunner.class)
public class OrderBookServiceImplTest {
	
	@TestConfiguration
    static class OrderServiceApplicationTestContextConfiguration {
  
        @Bean
        public OrderBookService orderBookService() {
            return new OrderBookServiceImpl();
        }
    }
	
	@Autowired
    private OrderBookService orderBookService;
 
    @MockBean
    private OrderBookDao orderBookDao;
    
    @SpyBean
    private OrderBookValidationHandler validationHandler;
    
    @MockBean
	private OrderExecutionManager orderExecutionManager;
    
    @MockBean
	private OrderStatusBuilder orderStatusBuilder;
    
    OrderBook orderBook;
    Order order;
    
    @Before
    public void setup() {
    	orderBook = new OrderBook();
    	orderBook.setInstrumentId(Long.valueOf(100L));
    	orderBook.setOrderBookId(Long.valueOf(1L));
    	
    	order = new Order();
    	order.setOrderBookId(Long.valueOf(1L));
    	order.setOrderId(Long.valueOf(1L));
    	
    	Mockito.when(orderBookDao.findOrderBookByID(Mockito.anyLong())).thenReturn(orderBook);
    	Mockito.when(orderBookDao.findOrderBookByInstID(Long.valueOf(100L))).thenReturn(orderBook);
    	Mockito.when(orderBookDao.findOrderBookByID(Long.valueOf(1L))).thenReturn(orderBook);
    	Mockito.when(orderBookDao.findOrderById(Long.valueOf(1L))).thenReturn(order);
    }
    
    @Test
    public void should_create_orderbook() {
    	orderBook.setStatus(Constants.ORDER_BOOK_CLOSED);
    	Mockito.when(orderBookDao.createOrderBook(Long.valueOf(100L))).thenReturn(orderBook);
    	OrderBook orderBook = orderBookService.createOrderBook(100);
    	assertNotNull(orderBook);
    	assertEquals(100, orderBook.getInstrumentId());
    }
    
    @Test(expected=OrderException.OrderBookAlreadyPresent.class)
    public void should_not_create_orderbook() {
    	orderBook.setStatus(Constants.ORDER_BOOK_OPEN);
    	Mockito.when(orderBookDao.createOrderBook(Long.valueOf(100L))).thenReturn(orderBook);
    	orderBookService.createOrderBook(100);
    }
    
    @Test
    public void should_close_orderbook() {
    	orderBook.setStatus("Open");
    	OrderBook closedOrderBook = new OrderBook();
    	closedOrderBook.setInstrumentId(Long.valueOf(100L));
    	closedOrderBook.setOrderBookId(Long.valueOf(1L));
    	closedOrderBook.setStatus("Closed");
    	Mockito.when(orderBookDao.closeOrderBook(Long.valueOf(1L))).thenReturn(closedOrderBook);
    	
    	OrderBook orderBook = orderBookService.closeOrderBook(1);
    	
    	assertNotNull(orderBook);
    	assertEquals(100, orderBook.getInstrumentId());
    	assertEquals("Closed", orderBook.getStatus());
    }
    
    @Test(expected=OrderException.OrderBookNotFound.class)
    public void should_not_close_as_orderbook_not_found() {
    	Mockito.when(orderBookDao.findOrderBookByID(Long.valueOf(10L))).thenReturn(null);
    	orderBookService.closeOrderBook(10);
    }
    
    @Test(expected=OrderException.OrderBookClosed.class)
    public void should_not_close_as_orderbook_not_open() {
    	orderBook.setStatus("Closed");
    	Mockito.when(orderBookDao.findOrderBookByID(Long.valueOf(10L))).thenReturn(orderBook);
    	orderBookService.closeOrderBook(10);
    }
    
    @Test
    public void should_add_order_to_orderbook() {
    	orderBook.setStatus("Open");
    	
    	Order newOrder = new Order();
    	newOrder.setOrderBookId(Long.valueOf(1L));
    	
    	Mockito.when(orderBookDao.addOrder(newOrder)).thenReturn(newOrder);
    	Mockito.when(orderBookDao.findOrderById(Long.valueOf(0))).thenReturn(null);
    	
    	Order newlySavedOrder = orderBookService.addOrder(newOrder);
    	assertNotNull(newlySavedOrder);
    	assertEquals(Long.valueOf(1L), newlySavedOrder.getOrderBookId());
    	assertEquals(0, newlySavedOrder.getOrderId());
    }
    
    @Test(expected=OrderException.OrderBookClosed.class)
    public void should_not_add_order_as_order_book_closed() {
    	orderBook.setStatus("Closed");
    	
    	Order newOrder = new Order();
    	newOrder.setOrderBookId(Long.valueOf(1L));
    	
    	orderBookService.addOrder(newOrder);
    }
    
}
