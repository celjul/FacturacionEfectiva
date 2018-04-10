package com.entich.ezfact.folios.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.factory.FactoryException;
import com.entich.ezfact.folios.model.PaqueteUsuario;

public class PaqueteUsuarioFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(PaqueteUsuarioFactory.class);
	
	public static PaqueteUsuario newInstance() {
		LOG.debug("Creando una nueva instancia de un PaqueteUsuario.");
		return new PaqueteUsuario();
	}

	public static PaqueteUsuario newInstance(Long id) {
		LOG.debug("Creando una instancia de un PaqueteUsuario a partir de un "
				+ "id long.");
		try {
			Assert.notNull(id);
			PaqueteUsuario paqueteUsuario = newInstance();
			paqueteUsuario.setId(id);
			return paqueteUsuario;
		} catch (IllegalArgumentException ex) {
			String message = "Error en lo parámetros para crear el objeto.";
			LOG.warn(message);
			throw new FactoryException(message, ex);
		}
	}

	public static PaqueteUsuario newInstance(String id) {
		LOG.debug("Creando una instancia de un paqueteUsuario a partir de un id "
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
