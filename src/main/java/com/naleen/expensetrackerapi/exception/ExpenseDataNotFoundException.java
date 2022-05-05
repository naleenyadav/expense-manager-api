package com.naleen.expensetrackerapi.exception;

public class ExpenseDataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ExpenseDataNotFoundException(String message) {

		super(message);
		this.message = message;

	}

	public String getMessage() {
		return message;
	}
}
