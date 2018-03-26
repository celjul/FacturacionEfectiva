package com.entich.commons.direcciones.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.entich.commons.direcciones.dao.ColoniaDao;
import com.entich.commons.direcciones.model.Colonia;
import com.entich.commons.direcciones.service.ColoniaService;
import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;

/**
 * Servicio para las colonias.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Service
public class ColoniaServiceImpl implements ColoniaService {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(ColoniaServiceImpl.class);

	/**
	 * Dependencia del acceso a datos.
	 */
	@Autowired
	private ColoniaDao coloniaDao;

	/**
	 * Dependencia del validador de beans.
	 */
	@Autowired
	private Validator validator;

	@Transactional
	@Override
	public void create(Colonia colonia) {
		LOG.debug("Creando una colonia.");
		try {
			Assert.notNull(colonia);
			Set<ConstraintViolation<Colonia>> violations = validator
					.validate(colonia);
			if (violations.isEmpty()) {
				coloniaDao.create(colonia);
			} else {
				String message = "La colonia tiene errores de validación: "
						+ violations;
				LOG.warn(message);
				throw new ValidationException("La colonia presenta errores de validación.", violations);
			}
		} catch (IllegalArgumentException ex) {
			String message = "La colonia es nula.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Colonia> search(Map map) {
		LOG.debug("Buscando colonias");
		Collection<Colonia> colonias = null;
		if (map != null && !map.isEmpty()) {
			colonias = coloniaDao.search(map.entrySet());
		} else {
			colonias = coloniaDao.findAll();
		}
		if (colonias.isEmpty()) {
			LOG.info("No se encontraron colonias.");
		}
		return colonias;
	}
}