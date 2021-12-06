package com.revature.exceptions;

public class UnauthorizedException extends Exception {
	
	public UnauthorizedException() {
		super();
	}
	
	public UnauthorizedException(String message, Throwable cause, boolean enableSuppression,
			boolean writeableStackTrace) {
		super(message, cause, enableSuppression, writeableStackTrace);
		
	}
	
	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	public UnauthorizedException(String message) {
		super(message);
		
	}
	
	public UnauthorizedException(Throwable cause) {
		super(cause);
	}
	
	
	

}
