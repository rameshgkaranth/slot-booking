package com.redmart.slot.booking.exception;

public class InvalidRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRequestException() {
		
	}
	
	public InvalidRequestException(String message) {
		super(message);
	}
	
	public InvalidRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
