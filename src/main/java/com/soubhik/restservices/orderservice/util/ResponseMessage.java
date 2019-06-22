package com.soubhik.restservices.orderservice.util;

public class ResponseMessage {
	
	public static final String ORDER_BOOK_CREATED_SUCCESS = "The order book created successfully with id %d";
	
	public static final String ORDER_BOOK_CLOSED_SUCCESS = "The order book closed successfully";
	
	public static final String ORDER_BOOK_NOT_FOUND = "No order book is available for the instrument";
	
	public static final String ORDER_ADDED_SUCCESS = "The order added successfully with id %d";
	
	public static final String ORDER_BOOK_ALREADY_PRESENT = "One order book is already open with the same instrument id";
	
	public static final String INVALID_ORDER = "The order is invalid";
	
	public static final String ORDER_NOT_FOUND = "The order is not found";
	
	public static final String ORDER_ADD_FAIL = "The order could not be added to the order book";
	
	public static final String ORDER_BOOK_CLOSED = "The order book is already closed for modification";
	
	public static final String EXECUTION_PRICE_MISMATCH = "The execution price is different from order book price";
	
	public static final String EXECUTION_SUCCESS = "The order book executed successfully";
	
	public static final String EXECUTION_NOT_POSSIBLE_FOR_OPEN_ORDERBOOK = "The order book is open";
	
	public static final String EXECUTION_NOT_POSSIBLE_FOR_EXECUTED_ORDERBOOK = "The order book is executed";

}
