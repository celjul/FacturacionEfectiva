package com.entich.ezfact.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.entich.commons.exceptions.ApplicationException;

@ControllerAdvice
public class JsonError {
	
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public @ResponseBody ApplicationException 
			handlerApplicationException(ApplicationException ex) {
		
		return ex;
	}
}

