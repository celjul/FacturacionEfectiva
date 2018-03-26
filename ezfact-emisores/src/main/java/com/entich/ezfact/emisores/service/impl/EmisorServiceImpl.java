package com.entich.ezfact.emisores.service.impl;

import java.io.File;
import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.direcciones.model.Direccion;
import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.emisores.dao.EmisorDao;
import com.entich.ezfact.emisores.model.DireccionEmisor;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.service.EmisorService;

/**
 * Servicio de los emisores.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Service
public class EmisorServiceImpl implements EmisorService {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(EmisorServiceImpl.class);

	/**
	 * Dependencia del acceso a datos.
	 */
	@Autowired
	private EmisorDao emisorDao;

	/**
	 * Dependencia del validador de beans.
	 */
	@Autowired
	private Validator validator;

	@Value("${facturacion.path.plantillas}")
	private String pathPlantillas;

	@Override
	public Collection<Emisor> getAll() {
		LOG.debug("Obteniendo los emisores existentes.");
		Collection<Emisor> emisores = null;
		emisores = emisorDao.findAll();
		if (emisores.isEmpty()) {
			LOG.info("No se encontraron emisores.");
		}
		return emisores;
	}

	@Override
	public void create(Emisor emisor) {
		LOG.debug("Creando un emisor.");
		try {
			Assert.notNull(emisor);
			
			fixEmisor(emisor);
			validar(emisor);
			validarRFC(emisor.getRfc());
			
			emisorDao.create(emisor);
			
		} catch (IllegalArgumentException ex) {
			String message = "El emisor es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	private void validar(Emisor emisor) {
		Set<ConstraintViolation<Emisor>> violations = validator
				.validate(emisor);
		if (CollectionUtils.isNotEmpty(violations)) {
			String message = "El emisor tiene errores de validación.";
			LOG.warn(message + " " + violations);
			throw new ValidationException(message, violations);
		}
	}

	private void validarRFC(String rfc) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rfc", rfc);
		if (CollectionUtils.isNotEmpty(emisorDao.search(map.entrySet()))) {
			throw new ServiceException("El RFC ya existe.");
		}
	}

	private void fixEmisor(Emisor emisor) {
		/*if (StringUtils.isBlank(emisor.getPlantilla())) {
			emisor.setPlantilla(new File(pathPlantillas + File.separator +  ).getAbsolutePath());
		}*/
		
		if (CollectionUtils.isNotEmpty(emisor.getDirecciones())) {
			for (Direccion direccion : emisor.getDirecciones()) {
				DireccionEmisor dirEmisor = (DireccionEmisor) direccion; 
				dirEmisor.setEmisor(emisor);
			}			
		}
	}

	@Override
	public Emisor read(Long id) {
		LOG.debug("Obteniendo un emisor por id.");
		try {
			Assert.notNull(id);
			return emisorDao.read(id);
		} catch (IllegalArgumentException ex) {
			String message = "El identificador es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public void update(Emisor emisor) {
		LOG.debug("Actualizando un emisor.");
		try {
			Assert.notNull(emisor);
			Assert.notNull(emisor.getId());
			
			fixEmisor(emisor);
			if (StringUtils.isBlank(emisor.getPlantilla())) {
				emisor.setPlantilla(read(emisor.getId()).getPlantilla());
			}
			
			validar(emisor);
			emisorDao.update(emisor);
		} catch (IllegalArgumentException ex) {
			String message = "El emisor y/o su identificador es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public void delete(Emisor emisor) {
		LOG.debug("Eliminando un producto.");
		try {
			Assert.notNull(emisor);
			Assert.notNull(emisor.getId());
			emisorDao.delete(emisor);
		} catch (IllegalArgumentException ex) {
			String message = "El emisor y/o su identificador es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Collection<Emisor> search(Map map) {
		throw new ServiceException("Funcionalidad no desarrollada.");
	}

	@Override
	public Integer getFoliosUtilizados(Date fechaInicio, Date fechaFin, Emisor... emisores) {
		try {
			Assert.notNull(emisores);
			Assert.notEmpty(emisores);
			Assert.notNull(fechaInicio);
			Assert.notNull(fechaFin);
			return emisorDao.getFoliosUtilizados(fechaInicio, fechaFin, emisores);
		} catch (IllegalArgumentException ex) {
			String message = "El emisor y/o su identificador es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}
}