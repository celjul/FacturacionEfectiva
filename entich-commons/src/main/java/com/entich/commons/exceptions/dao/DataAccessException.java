package com.entich.commons.exceptions.dao;

import com.entich.commons.exceptions.ApplicationException;

/**
 * Excepcion generada en la capa de acceso a datos de la aplicacion.
 * 
 * @author Luis Angel Cardenas
 *
 */
public class DataAccessException  extends ApplicationException {
	private static final String DEFAULT_MESSAGE = 
			"Excepcion generada en la capa de persistencia de datos."; 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8739700608587070066L;

	/**
	 * Crea una excepcion vacia, con una descripcion por default.
	 */
	public DataAccessException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Crea una excepcion con una descripcion por default, con la causa 
	 * especificada.
	 * @param cause Causa de la excepcion. 
	 */
	public DataAccessException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	/**
	 * Crea una excepcion con el mensaje especificado.
	 * 
	 * @param message Mensaje de descripcion de la excepcion.
	 */
	public DataAccessException(String message) {
		super(message);
	}

	/**
	 * Crea una excepcion con el mensaje especificado y con la causa de la 
	 * excepcion.
	 *  
	 * @param message Mensaje de descripcion de la excepcion.
	 * @param cause Causa de la excepcion.
	 */
	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
