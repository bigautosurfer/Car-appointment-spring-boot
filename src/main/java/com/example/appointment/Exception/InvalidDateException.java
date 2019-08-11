package com.example.appointment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidDateException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidDateException(String error) {
		super(error);
	}
}
