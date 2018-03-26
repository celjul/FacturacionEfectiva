package com.entich.ezfact.emisores.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.factory.FactoryException;
import com.entich.ezfact.emisores.model.Certificado;

/**
 * Factoría para los certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 29/11/2013
 */
public class CertificadoFactory {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(CertificadoFactory.class);

	/**
	 * Método que genera una nueva instancia de un certificado.
	 * 
	 * @return un certificado
	 * @see {@link Certificado}
	 */
	public static Certificado newInstance() {
		LOG.debug("Creando una nueva instancia de un certificado.");
		return new Certificado();
	}

	/**
	 * Método que obtiene una instancia de un certificado a partir de un id en
	 * long.
	 * 
	 * @param id
	 *            id
	 * @return un certificado
	 * @see {@link Certificado}
	 */
	public static Certificado newInstance(Long id) {
		LOG.debug("Creando una instancia de un certificado a partir de un "
				+ "id long.");
		try {
			Assert.notNull(id);
			Certificado certificado = newInstance();
			certificado.setId(id);
			return certificado;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	/**
	 * Método que obtiene una instancia de un certificado a partir de un id en
	 * string.
	 * 
	 * @param id
	 *            id
	 * @return un certificado
	 * @see {@link Certificado}
	 */
	public static Certificado newInstance(String id) {
		LOG.debug("Creando una instancia de un certificado a partir de un id "
				+ "en string.");
		try {
			Assert.notNull(id);
			Assert.hasLength(id);
			return newInstance(Long.parseLong(id));
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}
}