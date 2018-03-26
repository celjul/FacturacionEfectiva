package com.entich.ezfact.emisores.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.emisores.dao.DireccionEmisorDao;
import com.entich.ezfact.emisores.model.DireccionEmisor;
import com.entich.ezfact.emisores.service.DireccionEmisorService;

@Service("direccionEmisorService")
public class DireccionEmisorServiceImpl implements DireccionEmisorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DireccionEmisorServiceImpl.class);
	
	@Autowired
	private DireccionEmisorDao direccionEmisorDao;
	
	@Override
	public void eliminar(DireccionEmisor direccion) {
		try {
			Assert.notNull(direccion);
			
			direccionEmisorDao.delete(direccion);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error al intentar borrar la direccion.", ex);
			throw new ServiceException("La direccion no puede ser nula.");
		}
	}

}
