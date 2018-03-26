package com.entich.commons.direcciones.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entich.commons.direcciones.dao.PaisDao;
import com.entich.commons.direcciones.model.Pais;
import com.entich.commons.direcciones.service.PaisService;

/**
 * Servicio para los países.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Service
public class PaisServiceImpl implements PaisService {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(PaisServiceImpl.class);

	/**
	 * Dependencia del acceso a datos.
	 */
	@Autowired
	private PaisDao paisDao;

	@Override
	public Collection<Pais> findAll() {
		LOG.debug("Obteniendo los pa�ses existentes.");
		Collection<Pais> paises = null;
		paises = paisDao.findAll();
		if (paises.isEmpty()) {
			LOG.info("No se encontraron pa�ses.");
		}
		return paises;
	}
}
