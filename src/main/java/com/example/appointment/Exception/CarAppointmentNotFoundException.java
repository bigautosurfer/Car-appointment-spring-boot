package com.example.appointment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when an appointment cannot be found.
 * @author xiao
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarAppointmentNotFoundException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public CarAppointmentNotFoundException(String error) {
		super(error);
	}
	
}
