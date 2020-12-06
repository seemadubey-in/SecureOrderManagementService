package com.soms.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequestStructureNotValid extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public RequestStructureNotValid(String message) {
		super(message);
	}

}
