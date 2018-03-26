package com.entich.ezfact.clientes.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.clientes.dao.ClienteDao;
import com.entich.ezfact.clientes.model.Cliente;
import com.entich.ezfact.clientes.model.ContactoCliente;
import com.entich.ezfact.clientes.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteDao clienteDao;

	@Autowired
	private Validator validator;

	@Override
	public void create(Cliente cliente) {
		try {
			Assert.notNull(cliente, "El Cliente no puede ser nulo.");
			fixCliente(cliente);
			validar(cliente);
			
			if (!cliente.getRfc().equals("XAXX010101000") && !cliente.getRfc().equals("XEXX010101000")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rfc", cliente.getRfc());
				map.put("emisor", cliente.getEmisor());
				
				if (CollectionUtils.isNotEmpty(search(map))) {
					throw new ServiceException("Ya existe un cliente con un RFC idéntico.");
				}
			}
			
			clienteDao.create(cliente);
		} catch (IllegalArgumentException ex) {
			LOG.error(ex.getMessage());
			throw new ServiceException(ex.getMessage());
		}
	}

	private void validar(Cliente cliente) {
		Set<ConstraintViolation<Cliente>> errors = validator
				.validate(cliente);
		if (CollectionUtils.isNotEmpty(errors)) {
			String message = "El cliente tiene errores de validación.";
			LOG.warn(message + " " + errors);
			throw new ValidationException(message, errors);
		}
		
		if (cliente.getEmisor().getRfc().equalsIgnoreCase(cliente.getRfc())) {
			throw new ServiceException("El RFC del cliente no puede ser igual "
					+ "al RFC del emisor.");
		}
	}

	private void fixCliente(Cliente cliente) {
		fixContactos(cliente);
	}

	private void fixContactos(Cliente cliente) {
		if (CollectionUtils.isNotEmpty(cliente.getContactos())) {
			for (ContactoCliente contacto : cliente.getContactos()) {
				contacto.setCliente(cliente);
			}
		}
	}

	@Override
	public Cliente read(Long id) {
		try {
			Assert.notNull(id, "El identificador no puede ser nulo.");
			return clienteDao.read(id);
		} catch (IllegalArgumentException ex) {
			String message = "Error al obtener el cliente de la base de datos.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public void update(Cliente cliente) {
		LOG.debug("Actualizando un cliente.");
		try {
			Assert.notNull(cliente);
			Assert.notNull(cliente.getId());
			fixCliente(cliente);
			validar(cliente);
			clienteDao.update(cliente);
		} catch (IllegalArgumentException ex) {
			String message = "El cliente y/o su identificador es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public Collection<Cliente> findAll() {
		LOG.debug("Obteniendo los clientes existentes.");
		Collection<Cliente> clientes = null;
		clientes = clienteDao.findAll();
		if (clientes.isEmpty()) {
			LOG.info("No se encontraron clientes.");
		}
		return clientes;
	}

	@Override
	public Collection<Cliente> search(Map<String, Object> map) {
		LOG.debug("Buscando clientes");
		Collection<Cliente> clientes = null;
		if (map != null && !map.isEmpty()) {
			clientes = clienteDao.search(map.entrySet());
		} else {
			clientes = clienteDao.findAll();
		}
		if (clientes.isEmpty()) {
			LOG.info("No se encontraron clientes.");
		}
		return clientes;
	}

	@Override
	public Collection<Cliente> find(Map<String, Object> map) {
		LOG.debug("Buscando clientes");
		Collection<Cliente> clientes = null;
		if (map != null && !map.isEmpty()) {
			clientes = clienteDao.find(map);
		} 
		if (clientes.isEmpty()) {
			LOG.info("No se encontraron clientes.");
		}
		return clientes;
	}

	@Override
	public Cliente readByRfc(String rfc) {
		return clienteDao.readByRfc(rfc);
	}
}