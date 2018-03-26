package com.entich.ezfact.clientes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.clientes.dao.DireccionClienteDao;
import com.entich.ezfact.clientes.model.DireccionCliente;
import com.entich.ezfact.clientes.service.DireccionClienteService;

@Service("direccionClienteService")
public class DireccionClienteServiceImpl implements DireccionClienteService {
	@Autowired
	private DireccionClienteDao direccionClienteDao;
	
	@Override
	public void delete(DireccionCliente direccion) {
		try {
			Assert.notNull(direccion);
			Assert.notNull(direccion.getId());
			
			direccionClienteDao.delete(direccion);
		} catch (IllegalArgumentException ex) {
			throw new ServiceException("La direccion no puede ser nula.");
		}
	}
}