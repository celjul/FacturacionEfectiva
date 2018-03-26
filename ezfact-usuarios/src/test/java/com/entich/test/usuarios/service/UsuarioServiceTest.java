package com.entich.test.usuarios.service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
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
import com.entich.ezfact.usuarios.dao.UsuarioDao;
import com.entich.ezfact.usuarios.factory.UsuarioFactory;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.ezfact.usuarios.service.UsuarioService;
import com.entich.test.usuarios.dummies.UsuarioDummyGenerator;
import com.entich.utils.PasswordUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {
	"/service-test-usuarios.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(UsuarioServiceTest.class);
	
	
	@ReplaceWithMock
	@Autowired
	private UsuarioDao mockUsuarioDao;
	
	@ReplaceWithMock
	@Autowired
	private Validator mockValidator;
	
	@InjectMocks
	@Autowired
	private UsuarioService usuarioService;
	
	@Test(expected = ServiceException.class)
	public void testUsuarioNuloPassNulo() {
		clearMocks();
		LOG.info("Test de login de usuario con password y login nulo.");
		
		String usuario = null;
		String password = null;
		
		usuarioService.validate(usuario, password);
	}
	
	@Test(expected = ServiceException.class)
	public void testUsuarioNoNuloPassNulo() {
		clearMocks();
		LOG.info("Test de login de usuario con password nulo y login no nulo.");
		
		String usuario = "usuariovalido";
		String password = null;
		
		usuarioService.validate(usuario, password);
	}
	
	@Test(expected = ServiceException.class)
	public void testUsuarioNuloPassNoNulo() {
		clearMocks();
		LOG.info("Test de login de usuario con password no nulo y login nulo.");
		
		String usuario = null;
		String password = "passvalido";
		
		usuarioService.validate(usuario, password);
	}
	
	@Test
	public void testUsuarioNoValidoPassValido() {
		clearMocks();
		LOG.info("Test de login de usuario con password valido y login valido.");
		
		String usuario = "usuarionovalido";
		String password = "passvalido";
		Mockito.when(mockUsuarioDao.validate(usuario, password)).thenReturn(null);
		
		Usuario user = usuarioService.validate(usuario, password);
		Assert.assertNull(user);
	}
	
	@Test
	public void testUsuarioValidoPassValido() {
		clearMocks();
		LOG.info("Test de login de usuario con password valido y login valido.");
		
		String usuario = "usuariovalido";
		String password = "passvalido";
		Mockito.when(mockUsuarioDao.validate(usuario, PasswordUtils.md5Digest(password)))
				.thenReturn(UsuarioFactory.newInstance(1));
		
		Usuario user = usuarioService.validate(usuario, password);
		Assert.assertNotNull(user);
	}
	
	
	/******************************************************************************/
	@Test(expected = ServiceException.class)
	public void testCrearUsuarioNulo() {
		clearMocks();
		LOG.info("Test para crear un usuario nulo.");
		
		usuarioService.create(null);
	}
	
	@Test(expected = ServiceException.class)
	public void testCrearUsuarioErroresValidacion() {
		clearMocks();
		LOG.info("Test para crear un usuario con errores de validacion.");
		Usuario usuario = UsuarioFactory.newInstance();
		
		Mockito.when(mockValidator.validate(usuario)).thenReturn(getValidationErrors(1));
		
		usuarioService.create(usuario);
		Mockito.verify(mockValidator, Mockito.only()).validate(usuario);
		Mockito.verify(mockUsuarioDao, Mockito.never()).create(usuario);
	}

	@Test
	public void testCrearUsuarioSinErroresValidacion() {
		clearMocks();
		LOG.info("Test para crear un usuario sin errores de validacion.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(null, null);
		
		Mockito.when(mockValidator.validate(usuario)).thenReturn(getValidationErrors(0));
		
		usuarioService.create(usuario);
		Mockito.verify(mockValidator, Mockito.atLeastOnce()).validate(usuario);
		Mockito.verify(mockUsuarioDao, Mockito.only()).create(usuario);
	}
	
	@SuppressWarnings("unchecked")
	private Set<ConstraintViolation<Usuario>> getValidationErrors(int sizeErrors) {
		Set<ConstraintViolation<Usuario>> errors = new HashSet<ConstraintViolation<Usuario>>();
		for (int i = 0; i < sizeErrors; i++) {
			errors.add(Mockito.mock(ConstraintViolation.class));
		}
		
		return errors;
	}
	
	@Test
	public void testReadUserByUsername() {
		clearMocks();
		LOG.info("Test para crear un usuario sin errores de validacion.");
		Usuario usuario = usuarioService.getByUsername("usuario");
		LOG.info(usuario.toString());
	}
	
	private void clearMocks() {
		Mockito.reset(mockUsuarioDao, mockValidator);
	}
}
