package com.example.appointment.Exception;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler to handle custom exception returns  based on exception thrown
 * @author xiao
 *
 */

@ControllerAdvice
public class CarAppointmentExceptionHandler extends ResponseEntityExceptionHandler{

	   @ExceptionHandler(Exception.class)
	   public ResponseEntity<Object> handleAllExceptions(Exception ex) {
	       ArrayList<String> details = new ArrayList<String>();
	       details.add(ex.getLocalizedMessage());
	       ErrorResponse error = new ErrorResponse("Server Error occurred", details);
	       return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   
	   @ExceptionHandler(CarAppointmentNotFoundException.class)
	   public ResponseEntity<Object> handleNoAppointmentFound(CarAppointmentNotFoundException ex){
		   ArrayList<String> details = new ArrayList<String>();
	       details.add(ex.getLocalizedMessage());
	       ErrorResponse error = new ErrorResponse("Appoinment Not Found", details);
	       return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	   }
	   
	   @ExceptionHandler(InvalidDateException.class)
	   public ResponseEntity<Object> handleInvalidDates(InvalidDateException ex){
		   ArrayList<String> details = new ArrayList<String>();
	       details.add(ex.getLocalizedMessage());
	       ErrorResponse error = new ErrorResponse("Invalid Date format found", details);
	       return new ResponseEntity(error, HttpStatus.PRECONDITION_FAILED);
	   }
	   
}
