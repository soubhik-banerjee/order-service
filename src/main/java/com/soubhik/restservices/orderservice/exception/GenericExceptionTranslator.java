package com.soubhik.restservices.orderservice.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GenericExceptionTranslator extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
		GenericExceptionFormat gExFormat = new GenericExceptionFormat(new Date(),ex.getMessage(),"Root Cause");
		return new ResponseEntity<Object>(gExFormat,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(OrderException.OrderBookNotFound.class)
	public ResponseEntity<Object> handleOrderBookNotFoundException(OrderException.OrderBookNotFound ex, WebRequest request){
		GenericExceptionFormat gExFormat = new GenericExceptionFormat(new Date(),ex.getMessage(),"Root Cause");
		return new ResponseEntity<Object>(gExFormat,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrderException.OrderBookAlreadyPresent.class)
	public ResponseEntity<Object> handleOrderBookAlreadyPresentException(OrderException.OrderBookAlreadyPresent ex, WebRequest request){
		GenericExceptionFormat gExFormat = new GenericExceptionFormat(new Date(),ex.getMessage(),"Root Cause");
		return new ResponseEntity<Object>(gExFormat,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OrderException.OrderBookClosed.class)
	public ResponseEntity<Object> handleOrderBookClosedException(OrderException.OrderBookClosed ex, WebRequest request){
		GenericExceptionFormat gExFormat = new GenericExceptionFormat(new Date(),ex.getMessage(),"Root Cause");
		return new ResponseEntity<Object>(gExFormat,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OrderException.OrderAlreadyAdded.class)
	public ResponseEntity<Object> handleOrderAlreadyAddedException(OrderException.OrderAlreadyAdded ex, WebRequest request){
		GenericExceptionFormat gExFormat = new GenericExceptionFormat(new Date(),ex.getMessage(),"Root Cause");
		return new ResponseEntity<Object>(gExFormat,HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		GenericExceptionFormat gExFormat = new GenericExceptionFormat(new Date(),ex.getMessage(),"Root Cause");
		return new ResponseEntity<Object>(gExFormat,HttpStatus.BAD_REQUEST);
	}
}
