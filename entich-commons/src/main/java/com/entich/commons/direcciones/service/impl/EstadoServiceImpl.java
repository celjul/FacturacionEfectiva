package com.entich.commons.direcciones.service.impl;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entich.commons.direcciones.dao.EstadoDao;
import com.entich.commons.direcciones.model.Estado;
import com.entich.commons.direcciones.service.EstadoService;

/**
 * Servicio para los estados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Service
public class EstadoServiceImpl implements EstadoService {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(EstadoServiceImpl.class);

	/**
	 * Dependencia del acceso a datos.
	 */
	@Autowired
	private EstadoDao estadoDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Estado> search(Map map) {
		LOG.debug("Buscando estados");
		Collection<Estado> estados = null;
		if (map != null && !map.isEmpty()) {
			estados = estadoDao.search(map.entrySet());
		} else {
			estados = estadoDao.findAll();
		}
		if (estados.isEmpty()) {
			LOG.info("No se encontraron estados.");
		}
		return estados;
	}
}