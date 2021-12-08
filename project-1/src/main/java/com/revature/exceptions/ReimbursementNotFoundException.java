package com.revature.exceptions;

public class ReimbursementNotFoundException extends Exception {

	public ReimbursementNotFoundException() {
		super();
	}
	
	public ReimbursementNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writeableStackTrace) {
		super(message, cause, enableSuppression, writeableStackTrace);
		
	}
	
	public ReimbursementNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	public ReimbursementNotFoundException(String message) {
		super(message);
		
	}
	
	public ReimbursementNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
