package com.entich.commons.direcciones.service.impl;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entich.commons.direcciones.dao.MunicipioDao;
import com.entich.commons.direcciones.model.Municipio;
import com.entich.commons.direcciones.service.MunicipioService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Service
public class MunicipioServiceImpl implements MunicipioService {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(MunicipioServiceImpl.class);

	/**
	 * Dependencia del acceso a datos.
	 */
	@Autowired
	private MunicipioDao municipioDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Municipio> search(Map map) {
		LOG.debug("Buscando municipios");
		Collection<Municipio> municipios = null;
		if (map != null && !map.isEmpty()) {
			municipios = municipioDao.search(map.entrySet());
		} else {
			municipios = municipioDao.findAll();
		}
		if (municipios.isEmpty()) {
			LOG.info("No se encontraron municipios.");
		}
		return municipios;
	}

}