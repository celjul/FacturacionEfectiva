package com.entich.commons.exceptions;

/**
 * Excepcion base de la aplicacion.
 * 
 * @author Luis Angel Cardenas
 *
 */
public abstract class ApplicationException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = 
			"Default Exception, excepcion lanzada sin descripcion."; 
	private static final long serialVersionUID = 2948942330329641604L;

	/**
	 * Crea una excepcion vacia, con una descripcion por default.
	 */
	public ApplicationException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Crea una excepcion con una descripcion por default, con la causa 
	 * especificada.
	 * @param cause Causa de la excepcion. 
	 */
	public ApplicationException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	/**
	 * Crea una excepcion con el mensaje especificado.
	 * 
	 * @param message Mensaje de descripcion de la excepcion.
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * Crea una excepcion con el mensaje especificado y con la causa de la 
	 * excepcion.
	 *  
	 * @param message Mensaje de descripcion de la excepcion.
	 * @param cause Causa de la excepcion.
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}
