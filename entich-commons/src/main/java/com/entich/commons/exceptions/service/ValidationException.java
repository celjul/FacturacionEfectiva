package com.entich.commons.exceptions.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7646258211280147863L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationException.class);

	private Collection<String> errores;
	
	public <T> ValidationException(String message,
			Set<ConstraintViolation<T>> errores) {
		super(message);
		if (errores != null && errores.size() > 0) {
			this.errores = new ArrayList<String>();
			
			for (ConstraintViolation<T> error : errores) {
				LOGGER.info(error.toString());
				this.errores.add(error.getMessage());
			}
		}
	}
	
	public Collection<String> getErrores() {
		return errores;
	}
}
