package com.entich.test.productos.service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.productos.dao.ProductoDao;
import com.entich.ezfact.productos.model.Producto;
import com.entich.ezfact.productos.service.ProductoService;
import com.entich.test.productos.utils.ProductoGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {
	"/service-test-productos.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductoServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(ProductoServiceTest.class);
	
	@Autowired
	@ReplaceWithMock
	private ProductoDao mockProductoDao;
	
	@ReplaceWithMock
	@Autowired
	private Validator mockValidator;
	
	@InjectMocks
	@Autowired
	private ProductoService productoService;
	
	@Before
	public void clearMocks() {
		Mockito.reset(mockProductoDao, mockValidator);
	}
	
	@Test
	public void crearProductoCorrecto() {
		Producto producto = ProductoGenerator.generate();
		
		productoService.create(producto);
		
		Mockito.verify(mockValidator, Mockito.only()).validate(producto);
		Mockito.verify(mockProductoDao, Mockito.only()).create(producto);
	}
	
	@Test(expected = ServiceException.class)
	public void crearProductoNulo() {
		Producto producto = null;
		
		productoService.create(producto);
	}
	
	@Test(expected = ServiceException.class)
	public void crearProductoConErrores() {
		Producto producto = ProductoGenerator.generate();
		
		Mockito.when(mockValidator.validate(producto)).thenReturn(getErrorSet());
		
		productoService.create(producto);
		Mockito.verify(mockValidator, Mockito.only()).validate(producto);
		Mockito.verify(mockProductoDao, Mockito.never()).create(producto);
	}
	
	@Test
	public void recuperarProducto() {
		Long id = 1L;
		Mockito.when(mockProductoDao.read(id)).thenReturn(ProductoGenerator.generate(id));
		
		Producto producto = productoService.read(id);
		Mockito.verify(mockProductoDao, Mockito.only()).read(id);
		Assert.assertNotNull(producto);
	}
	
	@Test
	public void actualizarProducto() {
		Producto producto = ProductoGenerator.generate(1L);
		
		productoService.update(producto);
		Mockito.verify(mockValidator, Mockito.only()).validate(producto);
		Mockito.verify(mockProductoDao, Mockito.only()).update(producto);
	}
	
	@Test(expected = ServiceException.class)
	public void actualizarProductoIdNulo() {
		Producto producto = ProductoGenerator.generate();
		
		productoService.update(producto);
		Mockito.verify(mockValidator, Mockito.only()).validate(producto);
	}
	
	@Test(expected = ServiceException.class)
	public void actualizarProductoConErrores() {
		Producto producto = ProductoGenerator.generate(1L);
		
		Mockito.when(mockValidator.validate(producto)).thenReturn(getErrorSet());
		
		productoService.update(producto);
		Mockito.verify(mockValidator, Mockito.only()).validate(producto);
		Mockito.verify(mockProductoDao, Mockito.never()).create(producto);
	}
	
	@Test
	public void borrarProducto() {
		Producto producto = ProductoGenerator.generate(1L);
		
		productoService.delete(producto);
		Mockito.verify(mockValidator, Mockito.never()).validate(producto);
		Mockito.verify(mockProductoDao, Mockito.only()).delete(producto);
	}
	
	@Test(expected = ServiceException.class)
	public void borrarProductoNulo() {
		Producto producto = null;
		
		productoService.delete(producto);
	}
	
	@Test(expected = ServiceException.class)
	public void borrarProductoIdNulo() {
		Producto producto = ProductoGenerator.generate();
		
		productoService.delete(producto);
	}
	
	@SuppressWarnings("unchecked")
	private Set<ConstraintViolation<Producto>> getErrorSet() {
		Set<ConstraintViolation<Producto>> set = new HashSet<ConstraintViolation<Producto>>();
		ConstraintViolation<Producto> error = Mockito.mock(ConstraintViolation.class);
		
		set.add(error);
		return set;
	}
}
