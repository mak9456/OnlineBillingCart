package com.src.retail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionhandler {

	
	@ExceptionHandler
	public ResponseEntity<billerrorresponse> billnotfound(BillNotFound ex){
	
		billerrorresponse error=new billerrorresponse();
		error.setErrorMessage(ex.getMessage());
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<billerrorresponse> itemRemove(ItemException ex){
		System.out.println("In itemRemove(): "+ ex.getMessage());
		billerrorresponse error=new billerrorresponse();
		error.setErrorMessage(ex.getMessage());
		error.setStatusCode(HttpStatus.NO_CONTENT.value());
		return new ResponseEntity<>(error,HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler
	public ResponseEntity<billerrorresponse> GenericException(Exception ex){
		System.out.println("In GenericException(): "+ex.getMessage());
		billerrorresponse error=new billerrorresponse();
		error.setErrorMessage(ex.getMessage());
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
