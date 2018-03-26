package com.entich.ezfact.clientes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.clientes.dao.ContactoClienteDao;
import com.entich.ezfact.clientes.model.ContactoCliente;
import com.entich.ezfact.clientes.service.ContactoClienteService;

@Service("contactoService")
public class ContactoClienteServiceImpl implements ContactoClienteService {
	@Autowired
	private ContactoClienteDao contactoClienteDao;
	
	@Override
	public void delete(ContactoCliente contacto) {
		try {
			Assert.notNull(contacto);
			Assert.notNull(contacto.getId());
			
			contactoClienteDao.delete(contacto);
		} catch (IllegalArgumentException ex) {
			throw new ServiceException("El contacto no puede ser nulo.");
		}
	}
}
