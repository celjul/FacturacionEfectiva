package com.entich.commons.direcciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.direcciones.model.Direccion;
import com.entich.commons.exceptions.factory.FactoryException;

/**
 * Factoría para las direcciones.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 24/11/2013
 */
public class DireccionFactory {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(DireccionFactory.class);

	/**
	 * M�todo que genera una nueva instancia de una direcci�n.
	 * 
	 * @param tipo
	 *            tipo
	 * @return una dirección
	 * @see {@link Direccion}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Direccion> tipo) {
		LOG.debug(String.format(
				"Creando la instancia de direccion de tipo %s.",
				tipo.getSimpleName()));
		try {
			return (T) tipo.newInstance();
		} catch (IllegalAccessException ex) {
			String message = String.format(
					"Error al crear una instancia del tipo: %s",
					tipo.getSimpleName());
			LOG.warn(message, ex);
			throw new FactoryException(message, ex);
		} catch (InstantiationException ex) {
			String message = String.format(
					"Error al crear una instancia del tipo: %s",
					tipo.getSimpleName());
			LOG.warn(message, ex);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * M�todo que obtiene una instancia de una direcci�n a partir de un id en
	 * long.
	 * 
	 * @param tipo
	 *            tipo
	 * @param id
	 *            id
	 * @return una direcci�n
	 * @see {@link Direccion}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Direccion> tipo, Long id) {
		LOG.debug(String.format(
				"Creando la instancia de direcci�n de tipo %s a "
						+ "partir de un id en long.", tipo.getSimpleName()));
		try {
			Assert.notNull(id);
			Direccion direccion = newInstance(tipo);
			direccion.setId(id);
			return (T) direccion;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo par�metros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * M�todo que obtiene una instancia de una direcci�n a partir de un id en
	 * string.
	 * 
	 * @param tipo
	 *            tipo
	 * @param id
	 *            id
	 * @return una direcci�n
	 * @see {@link Direccion}
	 */
	public static <T> T newInstance(Class<? extends Direccion> tipo, String id) {
		LOG.debug(String.format(
				"Creando la instancia de direcci�n de tipo %s a "
						+ "partir de un string.", tipo.getSimpleName()));
		try {
			Assert.notNull(id);
			Assert.hasLength(id);
			return newInstance(tipo, Long.parseLong(id));
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo par�metros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}
}