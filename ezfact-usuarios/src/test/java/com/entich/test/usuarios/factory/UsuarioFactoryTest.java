package com.entich.test.usuarios.factory;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.commons.exceptions.ApplicationException;
import com.entich.ezfact.usuarios.factory.UsuarioFactory;
import com.entich.ezfact.usuarios.model.Usuario;

public class UsuarioFactoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioFactory.class);
	
	@Test
	public void testIntanciaVacia() {
		LOGGER.info("Prueba del metodo newInstance de la factoria.");
		Usuario usuario = UsuarioFactory.newInstance();
		
		Assert.assertNotNull(usuario);
		Assert.assertNull(usuario.getApellidoMaterno());
		Assert.assertNull(usuario.getApellidoPaterno());
		Assert.assertNull(usuario.getLogin());
		Assert.assertNull(usuario.getNombre());
		Assert.assertNull(usuario.getPassword());
		Assert.assertNull(usuario.getActivo());
		Assert.assertNull(usuario.getId());
	}
	
	@Test
	public void testIntanciaConIdLong() {
		LOGGER.info("Prueba del metodo newInstance de la factoria con id.");
		Long id = 1L;
		Usuario usuario = UsuarioFactory.newInstance(id);
		
		Assert.assertNotNull(usuario);
		Assert.assertNull(usuario.getApellidoMaterno());
		Assert.assertNull(usuario.getApellidoPaterno());
		Assert.assertNull(usuario.getLogin());
		Assert.assertNull(usuario.getNombre());
		Assert.assertNull(usuario.getPassword());
		Assert.assertNull(usuario.getActivo());
		Assert.assertNotNull(usuario.getId());
		Assert.assertEquals(id, usuario.getId());
	}
	
	@Test
	public void testIntanciaIdString() {
		LOGGER.info("Prueba del metodo newInstance de la factoria con cadena valida.");
		String id = "1";
		Usuario usuario = UsuarioFactory.newInstance(id);
		
		Assert.assertNotNull(usuario);
		Assert.assertNull(usuario.getApellidoMaterno());
		Assert.assertNull(usuario.getApellidoPaterno());
		Assert.assertNull(usuario.getLogin());
		Assert.assertNull(usuario.getNombre());
		Assert.assertNull(usuario.getPassword());
		Assert.assertNull(usuario.getActivo());
		Assert.assertNotNull(usuario.getId());
		Assert.assertEquals(Long.valueOf(id), usuario.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void testIntanciaIdStringNulo() {
		LOGGER.info("Prueba del metodo newInstance de la factoria con cadena nula.");
		String id = null;
		Usuario usuario = UsuarioFactory.newInstance(id);
		
		Assert.assertNotNull(usuario);
		Assert.assertNull(usuario.getApellidoMaterno());
		Assert.assertNull(usuario.getApellidoPaterno());
		Assert.assertNull(usuario.getLogin());
		Assert.assertNull(usuario.getNombre());
		Assert.assertNull(usuario.getPassword());
		Assert.assertNull(usuario.getActivo());
		Assert.assertNotNull(usuario.getId());
		Assert.assertEquals(Long.valueOf(id), usuario.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void testIntanciaIdStringNoValido() {
		LOGGER.info("Prueba del metodo newInstance de la factoria con cadena no valida.");
		String id = "";
		Usuario usuario = UsuarioFactory.newInstance(id);
		
		Assert.assertNotNull(usuario);
		Assert.assertNull(usuario.getApellidoMaterno());
		Assert.assertNull(usuario.getApellidoPaterno());
		Assert.assertNull(usuario.getLogin());
		Assert.assertNull(usuario.getNombre());
		Assert.assertNull(usuario.getPassword());
		Assert.assertNull(usuario.getActivo());
		Assert.assertNotNull(usuario.getId());
		Assert.assertEquals(Long.valueOf(id), usuario.getId());
	}
}
