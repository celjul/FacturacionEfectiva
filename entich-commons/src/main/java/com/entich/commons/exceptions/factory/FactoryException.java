package com.entich.commons.exceptions.factory;

import com.entich.commons.exceptions.ApplicationException;

public class FactoryException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2493011499441032058L;
	
	private static final String DEFAULT_MESSAGE = 
			"Excepcion generada en la fabrica de objetos.";
	
	public FactoryException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Crea una excepcion con una descripcion por default, con la causa 
	 * especificada.
	 * @param cause Causa de la excepcion. 
	 */
	public FactoryException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	/**
	 * Crea una excepcion con el mensaje especificado.
	 * 
	 * @param message Mensaje de descripcion de la excepcion.
	 */
	public FactoryException(String message) {
		super(message);
	}

	/**
	 * Crea una excepcion con el mensaje especificado y con la causa de la 
	 * excepcion.
	 *  
	 * @param message Mensaje de descripcion de la excepcion.
	 * @param cause Causa de la excepcion.
	 */
	public FactoryException(String message, Throwable cause) {
		super(message, cause);
	}
}
