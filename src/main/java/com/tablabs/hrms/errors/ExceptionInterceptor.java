package com.tablabs.hrms.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {
	
//	@ExceptionHandler(EmployeeDoesNotExistException.class)
//	public final ResponseEntity<Object> handleAllExceptions(EmployeeDoesNotExistException ex) {
//		EmployeeDoesNotExistException empException = new EmployeeDoesNotExistException(ex.getMessage(), ex.getDetails(), ex.getEmployeeIds());
//		return new ResponseEntity(empException, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

}
