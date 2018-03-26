package com.entich.ezfact.productos.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.productos.dao.ProductoDao;
import com.entich.ezfact.productos.model.Producto;
import com.entich.ezfact.productos.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductoServiceImpl.class);
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ProductoDao productoDao;
	
	@Override
	public void create(Producto producto) {
		try {
			Assert.notNull(producto, "El producto no puede ser nulo.");
			
			if (producto.getExentoIVA() == null) {
				producto.setExentoIVA(false);
			}
			
			validar(producto);
			productoDao.create(producto);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error al intentar guardar el producto", ex);
			throw new ServiceException(ex.getMessage());
		}
	}

	private void validar(Producto producto) {
		Set<ConstraintViolation<Producto>> errors = validator.validate(producto);
		if (CollectionUtils.isNotEmpty(errors)) {
			String message = "El producto tiene errores de validación.";
			LOGGER.warn(message + " " + errors);
			throw new ValidationException(message, errors);
		}
	}

	@Override
	public Producto read(long id) {
		
		return productoDao.read(id);
	}

	@Override
	public void update(Producto producto) {
		try {
			Assert.notNull(producto, "El producto que se quiere actualizar no puede ser nulo.");
			Assert.notNull(producto.getId(), "El id del producto no puede ser nulo");
			validar(producto);
			
			productoDao.update(producto);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error al intentar actualizar el producto.", ex);
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public void delete(Producto producto) {
		try {
			Assert.notNull(producto, "El producto que se quiere eliminar no puede ser nulo.");
			Assert.notNull(producto.getId(), "El id del producto no puede ser nulo");
			
			productoDao.delete(producto);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error al intentar eliminar el producto.", ex);
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public Collection<Producto> getAll(Emisor emisor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emisor", emisor);
		
		return productoDao.search(map.entrySet());
	}

	@Override
	public Collection<Producto> find(Emisor emisor, String codigo,
			String descripcion) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emisor", emisor);
		
		if (StringUtils.isNotBlank(codigo) && codigo.length() >= 3) {
			map.put("codigo", String.format("%%%s%%", codigo));
		}
		
		if (StringUtils.isNotBlank(descripcion) && descripcion.length() >= 3) {
			map.put("nombre", String.format("%%%s%%", descripcion));
		}
		
		return productoDao.search(map.entrySet());
	}
}
