package com.soubhik.restservices.orderservice.exception;

import java.util.Date;

public class GenericExceptionFormat {
	
	public GenericExceptionFormat(Date date, String message, String rootCause) {
		super();
		this.date = date;
		this.message = message;
		this.rootCause = rootCause;
	}
	private Date date;
	private String message;
	private String rootCause;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRootCause() {
		return rootCause;
	}
	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

}
