package com.entich.ezfact.emisores.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.factory.FactoryException;
import com.entich.ezfact.emisores.model.Emisor;

/**
 * Factoría para los emisores.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
public class EmisorFactory {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(EmisorFactory.class);

	/**
	 * Método que genera una nueva instancia de un emisor.
	 * 
	 * @param tipo
	 *            tipo
	 * @return un emisor
	 * @see {@link Emisor}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Emisor> tipo) {
		try {
			Assert.notNull(tipo);
			LOG.debug(String.format(
					"Creando la instancia del emisor de tipo %s.",
					tipo.getSimpleName()));
			return (T) tipo.newInstance();
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
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
	 * Método que obtiene una instancia de un emisor a partir de un id en long.
	 * 
	 * @param tipo
	 *            tipo
	 * @param id
	 *            id
	 * @return un emisor
	 * @see {@link Emisor}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Emisor> tipo, Long id) {
		try {
			Assert.notNull(tipo);
			LOG.debug(String.format(
					"Creando la instancia del emisor de tipo %s a "
							+ "partir de un id en long.", tipo.getSimpleName()));
			Assert.notNull(id);
			Emisor emisor = newInstance(tipo);
			emisor.setId(id);
			return (T) emisor;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * Método que obtiene una instancia de un emisor a partir de un id en
	 * string.
	 * 
	 * @param tipo
	 *            tipo
	 * @param id
	 *            id
	 * @return un emisor
	 * @see {@link Emisor}
	 */
	public static <T> T newInstance(Class<? extends Emisor> tipo, String id) {
		try {
			Assert.notNull(tipo);
			LOG.debug(String.format(
					"Creando la instancia del emisor de tipo %s a "
							+ "partir de un string.", tipo.getSimpleName()));
			Assert.notNull(id);
			Assert.hasLength(id);
			return newInstance(tipo, Long.parseLong(id));
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}
}