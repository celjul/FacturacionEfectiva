package com.entich.commons.direcciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.direcciones.model.Colonia;
import com.entich.commons.exceptions.factory.FactoryException;

/**
 * Factoría para las colonias.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 24/11/2013
 */
public class ColoniaFactory {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(ColoniaFactory.class);

	/**
	 * Método que genera una nueva instancia de una colonia.
	 * 
	 * @return una colonia
	 * @see {@link Colonia}
	 */
	public static Colonia newInstance() {
		LOG.debug("Creando una nueva instancia de una colonia.");
		return new Colonia();
	}

	/**
	 * Método que obtiene una instancia de una colonia a partir de un id en
	 * long.
	 * 
	 * @param id
	 *            id
	 * @return una colonia
	 * @see {@link Colonia}
	 */
	public static Colonia newInstance(Long id) {
		LOG.debug("Creando una instancia de una colonia a partir de un id en "
				+ "long.");
		try {
			Assert.notNull(id);
			Colonia colonia = newInstance();
			colonia.setId(id);
			return colonia;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo par�metros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * Método que obtiene una instancia de una colonia a partir de un id en
	 * string.
	 * 
	 * @param id
	 *            id
	 * @return una colonia
	 * @see {@link Colonia}
	 */
	public static Colonia newInstance(String id) {
		LOG.debug("Creando una instancia de una colonia a partir de un id en "
				+ "string.");
		try {
			Assert.notNull(id);
			Assert.hasLength(id);
			return newInstance(Long.parseLong(id));
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo par�metros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * Método que obtiene una instancia de colonia a partir de un código postal
	 * y un nombre.
	 * 
	 * @param codigo
	 *            código postal
	 * @param nombre
	 *            nombre
	 * @return una colonia
	 * @see {@link Colonia}
	 */
	public static Colonia newInstance(String codigo, String nombre) {
		LOG.debug("Creando una instancia de una colonia a partir de un código "
				+ "postal y un nombre.");
		try {
			Assert.notNull(codigo);
			Assert.hasLength(codigo);
			Assert.notNull(nombre);
			Assert.hasLength(nombre);
			Colonia colonia = newInstance();
			colonia.setCodigoPostal(codigo);
			colonia.setNombre(nombre);
			return colonia;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo par�metros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}
}