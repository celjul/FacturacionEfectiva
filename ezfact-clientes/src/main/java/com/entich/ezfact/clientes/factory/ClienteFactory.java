package com.entich.ezfact.clientes.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.factory.FactoryException;
import com.entich.ezfact.clientes.model.Cliente;

/**
 * Factoria de instancias de la clases que extienden de <code>Cliente</code>
 * 
 * @author Pedro Josue Mendoza Islas
 */
public class ClienteFactory {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(ClienteFactory.class);

	/**
	 * Método que genera una nueva instancia de un cliente.
	 * 
	 * @param tipo
	 *            tipo
	 * @return un cliente
	 * @see {@link Cliente}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Cliente> tipo) {
		try {
			Assert.notNull(tipo);
			LOG.debug(String.format(
					"Creando la instancia del cliente de tipo %s.",
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
	 * Método que obtiene una instancia de un cliente a partir de un id en long.
	 * 
	 * @param tipo
	 *            tipo
	 * @param id
	 *            id
	 * @return un cliente
	 * @see {@link Cliente}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Cliente> tipo, Long id) {
		try {
			Assert.notNull(tipo);
			LOG.debug(String.format(
					"Creando la instancia del cliente de tipo %s a "
							+ "partir de un id en long.", tipo.getSimpleName()));
			Assert.notNull(id);
			Cliente cliente = newInstance(tipo);
			cliente.setId(id);
			return (T) cliente;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * Método que obtiene una instancia de un cliente a partir de un id en
	 * string.
	 * 
	 * @param tipo
	 *            tipo
	 * @param id
	 *            id
	 * @return un cliente
	 * @see {@link Cliente}
	 */
	public static <T> T newInstance(Class<? extends Cliente> tipo, String id) {
		try {
			Assert.notNull(tipo);
			LOG.debug(String.format(
					"Creando la instancia del cliente de tipo %s a "
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