package com.entich.ezfact.folios.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.factory.FactoryException;
import com.entich.ezfact.folios.model.PaqueteEmisor;

public class PaqueteEmisorFactory {
	private static final Logger LOG = LoggerFactory
			.getLogger(PaqueteEmisorFactory.class);

	public static PaqueteEmisor newInstance() {
		return new PaqueteEmisor();
	}

	public static PaqueteEmisor newInstance(long id) {
		PaqueteEmisor PaqueteEmisor = newInstance();
		PaqueteEmisor.setId(id);
		
		return PaqueteEmisor;
	}
	
	public static PaqueteEmisor newInstance(String id) {
		try {
			Assert.notNull(id, "La cadena no puede ser nula");
			
			return newInstance(Long.parseLong(id));
		} catch (NumberFormatException ex) {
			String message = String.format(
					"No es posible crear la instancia con id de valor [%s]",
					id);
			LOG.warn(message, ex);
			throw new FactoryException(message, ex);
		} catch (IllegalArgumentException ex) {
			String message = "El parametro del Id no puede ser nulo.";
			LOG.warn(message, ex);
			throw new FactoryException(message, ex);
		} 
	}
}
