package com.entich.ezfact.facturacion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.facturacion.dao.ConceptoDao;
import com.entich.ezfact.facturacion.factory.ConceptoFactory;
import com.entich.ezfact.facturacion.model.Concepto;
import com.entich.ezfact.facturacion.service.ConceptoService;

@Service("conceptoService")
public class ConceptoServiceImpl implements ConceptoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConceptoServiceImpl.class);
	
	@Autowired
	private ConceptoDao conceptoDao;
	
	@Override
	public void delete(Long id) {
		try {
			Assert.notNull(id);
			LOGGER.debug(String.format("Eliminando un concepto %s de la remisión.", id));
			
			conceptoDao.delete(ConceptoFactory.newInstance(id));
		} catch (IllegalArgumentException ex) {
			throw new ServiceException("El id del concepto no puede ser nulo.");
		}
	}

}
