package com.entich.commons.catalogo.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;
import com.entich.commons.exceptions.factory.FactoryException;

/**
 * Factoría para los catálogos.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 07/11/2013
 */
public class OpcionDeCatalogoFactory {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(OpcionDeCatalogoFactory.class);

	/**
	 * M�todo que genera una nueva instancia de un catálogo.
	 * 
	 * @param tipo
	 *            tipo de catálogo
	 * @return un catálogo
	 * @see {@link OpcionDeCatalogo}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(
			Class<? extends OpcionDeCatalogo> tipo) {
		LOG.debug(String.format(
				"Creando la instancia del catálogo de tipo %s.",
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
	 * M�todo que obtiene una instancia de un catálogo a partir de un id en
	 * long.
	 * 
	 * @param tipo
	 *            tipo de catálogo
	 * @param id
	 *            id
	 * @return un catálogo
	 * @see {@link OpcionDeCatalogo}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(
			Class<? extends OpcionDeCatalogo> tipo, Long id) {
		LOG.debug(String.format(
				"Creando la instancia del catálogo de tipo %s a "
						+ "partir de un id en long.", tipo.getSimpleName()));
		try {
			Assert.notNull(id);
			OpcionDeCatalogo catalogo = newInstance(tipo);
			catalogo.setId(id);
			return (T) catalogo;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * M�todo que obtiene una instancia de un catálogo a partir de un id en
	 * string.
	 * 
	 * @param tipo
	 *            tipo de catálogo
	 * @param id
	 *            id
	 * @return un catálogo
	 * @see {@link OpcionDeCatalogo}
	 */
	public static <T> T newInstance(
			Class<? extends OpcionDeCatalogo> tipo, String id) {
		LOG.debug(String.format(
				"Creando la instancia del catálogo de tipo %s a "
						+ "partir de un string.", tipo.getSimpleName()));
		try {
			Assert.notNull(id);
			Assert.hasLength(id);
			return newInstance(tipo, Long.parseLong(id));
		} catch (NumberFormatException ex) {
			String message = String.format("El valor [%s] no se puede convertir en un id válido.");
			LOG.warn(message, ex);
			throw new FactoryException(message, ex);
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		} 
	}

	/**
	 * M�todo que obtiene una instancia de un catálogo a partir de un id en long
	 * y una descripción.
	 * 
	 * @param tipo
	 *            tipo de catálogo
	 * @param id
	 *            id
	 * @param descripcion
	 *            descripción
	 * @return un catálogo
	 * @see {@link OpcionDeCatalogo}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(
			Class<? extends OpcionDeCatalogo> tipo, Long id, String descripcion) {
		LOG.debug(String.format(
				"Creando la instancia del catálogo de tipo %s a "
						+ "partir de un id en long y una descripción.",
				tipo.getSimpleName()));
		try {
			Assert.notNull(id);
			Assert.hasLength(descripcion);
			OpcionDeCatalogo catalogo = newInstance(tipo, id);
			catalogo.setDescripcion(descripcion);
			return (T) catalogo;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * M�todo que obtiene una instancia de un catálogo a partir de un id en
	 * string y una descripción.
	 * 
	 * @param tipo
	 *            tipo de catálogo
	 * @param id
	 *            id
	 * @param descripcion
	 *            descripción
	 * @return un catálogo
	 * @see {@link OpcionDeCatalogo}
	 */
	public static <T> T newInstance(
			Class<? extends OpcionDeCatalogo> tipo, String id,
			String descripcion) {
		LOG.debug(String.format(
				"Creando la instancia del catálogo de tipo %s a "
						+ "partir de un id en string y una descripción.",
				tipo.getSimpleName()));
		try {
			Assert.notNull(id);
			Assert.hasLength(descripcion);
			return newInstance(tipo, Long.parseLong(id), descripcion);
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}
}