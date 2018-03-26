package com.entich.commons.catalogo.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.catalogo.dao.OpcionDeCatalogoDao;
import com.entich.commons.catalogo.model.OpcionDeCatalogo;
import com.entich.commons.catalogo.service.OpcionDeCatalogoService;
import com.entich.commons.exceptions.service.ServiceException;

@Service
public class OpcionDeCatalogoServiceImpl implements OpcionDeCatalogoService {
	private static final Logger LOG = LoggerFactory.getLogger(OpcionDeCatalogoServiceImpl.class);
	
	@Autowired
	private OpcionDeCatalogoDao opcionDeCatalogoDao;
	
	@Override
	public OpcionDeCatalogo read(long id) {
		LOG.debug("Recuperando una opcion de catalogo.");
		return opcionDeCatalogoDao.read(id);
	}

	@Override
	public Collection<? extends OpcionDeCatalogo> getCatalogo(
			Class<? extends OpcionDeCatalogo> tipo) {
		
		try {
			Assert.notNull(tipo, "Se debe especificar el tipo de catalogo que se va a recuperar.");
			
			return opcionDeCatalogoDao.findAll(tipo);
		} catch (IllegalArgumentException ex) {
			String message = "Error al recuperar el catalogo de opciones";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public OpcionDeCatalogo get(Class<? extends OpcionDeCatalogo> tipo, String clave) {
		return opcionDeCatalogoDao.get(tipo, clave);
	}
	
	@Override
	public Collection<? extends OpcionDeCatalogo> search(Class<? extends OpcionDeCatalogo> tipo, String key) {
		LOG.debug("Recuperando una opcion de catalogo.");
		return opcionDeCatalogoDao.search(tipo, key);
	}
}
