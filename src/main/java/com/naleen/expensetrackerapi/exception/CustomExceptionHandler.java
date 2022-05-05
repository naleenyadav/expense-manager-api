package com.naleen.expensetrackerapi.exception;

import java.sql.Timestamp;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(ExpenseDataNotFoundException.class)
	public ResponseEntity<String> handleExpenseDataNotFoundException(ExpenseDataNotFoundException exception) {

		return new ResponseEntity<String>("Expense not found in database", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {

		return new ResponseEntity<String>("No value is present in DB for deletion, please check the id",
				HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(ItemAlreadyExistsException.class)
	public ResponseEntity<ErrorObject> handleItemAlreadyExistsException(ItemAlreadyExistsException ex,
			WebRequest request) {

		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.CONFLICT.value());
		errorObject.setMessage(ex.getMessage());
		errorObject.setTimeStamp(new Timestamp(System.currentTimeMillis()));

		return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorObject> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		
		ErrorObject errorObject = new ErrorObject();
		errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorObject.setMessage(ex.getMessage());
		errorObject.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		
		return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);

	}

	/*
	 * @ExceptionHandler(MethodArgumentNotValidException.class) public
	 * ResponseEntity<String>
	 * handleMethodArgumentNotValidException(MethodArgumentNotValidException
	 * exception){
	 * 
	 * //List<String> errors =
	 * exception.getBindingResult().getFieldErrors().stream().map(x->x.
	 * getDefaultMessage()).collect(Collectors.toList());
	 * 
	 * return new
	 * ResponseEntity<String>("Expense name must be greator than 2 characters !!"
	 * ,HttpStatus.BAD_REQUEST);
	 * 
	 * }
	 */

}
