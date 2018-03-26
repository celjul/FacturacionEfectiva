package com.entich.test.usuarios.dao;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.entich.commons.exceptions.ApplicationException;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.ezfact.usuarios.dao.UsuarioDao;
import com.entich.ezfact.usuarios.factory.RolFactory;
import com.entich.ezfact.usuarios.factory.UsuarioFactory;
import com.entich.ezfact.usuarios.model.Usuario;
import com.entich.test.usuarios.dummies.UsuarioDummyGenerator;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/datasource-test-usuarios.xml", "/persistence-test-usuarios.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/datasets/puestos.xml", "/datasets/usuarios.xml"})
@DatabaseTearDown(value = "/datasets/tearDown.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioDaoTest {
	private static final int NOMBRE_LENGTH = 255;

	private static final int PASS_LENGTH = 32;

	private static final int LOGIN_LENGTH = 20;

	private static final int APE_PAT_LENGTH = 255;

	private static final int APE_MAT_LENGTH = 255;

	private static final Logger logger = LoggerFactory.getLogger(UsuarioDaoTest.class);
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	
	@Test
	public void testCreateUsuarioCompleto() {
		logger.info("Prueba para alidar el registro de un usuario con todos los datos completos.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
		Assert.assertEquals(4, usuarioDao.findAll().size());
	}
	
	@Test
	public void testCreateUsuarioInactivo() {
		logger.info("Prueba para validar el registros de un usuario con estado inactivo");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setActivo(false);

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test
	public void testCreateUsuarioSinApellidoMaterno() {
		logger.info("Pueba para validar el registro de un usuario sin apellido materno");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setApellidoMaterno(null);

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test
	public void testCreateUsuarioApellidoMaternoVacio() {
		logger.info("Prueba para validar el registro de un usuario con apellido materno vacio.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setApellidoMaterno("");

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected=DataAccessException.class)
	public void testCreateUsuarioApellidoMaternoMuyLargo() {
		logger.info("Prueba para validar el registro de un usuario con apellido materno de longitud mayor a la permitida.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		
		usuario.setApellidoMaterno(getString("", APE_MAT_LENGTH));

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}

	@Test(expected = ApplicationException.class)
	public void testCreateUsuarioSinApellidoPaterno() {
		logger.info("Prueba para validar el registro de un usuario si apellido paterno");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setApellidoPaterno(null);

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected = DataAccessException.class )
	public void testCreateUsuarioApellidoPaternoVacio() {
		logger.info("Prueba para validar el registro de un usuario con apellido paterno vacio.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setApellidoPaterno("");

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected=DataAccessException.class)
	public void testCreateUsuarioApellidoPaternoMuyLargo() {
		logger.info("Prueba para validar el registro de un usuario con apellido paterno de longitud mayor a la permitida.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		
		usuario.setApellidoPaterno(getString("", APE_PAT_LENGTH));

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void testCreateUsuarioSinLogin() {
		logger.info("Prueba para validar el registro de un usuario sin login");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setLogin(null);

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected = DataAccessException.class )
	public void testCreateUsuarioLoginVacio() {
		logger.info("Prueba para validar el registro de un usuario con login vacio.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setLogin("");

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected=DataAccessException.class)
	public void testCreateUsuarioLoginMuyLargo() {
		logger.info("Prueba para validar el registro de un usuario con login de longitud mayor a la permitida.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		
		usuario.setLogin(getString(usuario.getLogin(), LOGIN_LENGTH));

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected=DataAccessException.class)
	public void testCreateUsuarioLoginRepetido() {
		logger.info("Prueba para validar el registro de un usuario con login ya existente.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setLogin("supervisor");
		
		usuarioDao.create(usuario);
	}
	
	@Test(expected = ApplicationException.class)
	public void testCreateUsuarioSinPassword() {
		logger.info("Prueba para validar el registro de un usuario sin password");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setPassword(null);

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected = DataAccessException.class )
	public void testCreateUsuarioPasswordVacio() {
		logger.info("Prueba para validar el registro de un usuario con password vacio.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setPassword("");

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected=DataAccessException.class)
	public void testCreateUsuarioPasswordMuyLargo() {
		logger.info("Prueba para validar el registro de un usuario con apellido paterno de longitud mayor a la permitida.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		
		usuario.setPassword(getString(usuario.getLogin(), PASS_LENGTH));

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}

	@Test(expected = ApplicationException.class)
	public void testCreateUsuarioSinNombre() {
		logger.info("Prueba para validar el registro de un usuario sin nombre");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setNombre(null);

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected = DataAccessException.class )
	public void testCreateUsuarioNombreVacio() {
		logger.info("Prueba para validar el registro de un usuario con nombre vacio.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		usuario.setNombre("");

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test(expected=DataAccessException.class)
	public void testCreateUsuarioNombreMuyLargo() {
		logger.info("Prueba para validar el registro de un usuario con nombre de longitud mayor a la permitida.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(UsuarioFactory.newInstance(1), RolFactory.newInstance(2L));
		
		usuario.setNombre(getString(usuario.getNombre(), NOMBRE_LENGTH));

		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test
	public void testCreateUsuarioSinSuperior() {
		logger.info("Prueba para validar el registro de un usuario sin superior.");
		Usuario usuario = UsuarioDummyGenerator.createUsuario(null, RolFactory.newInstance(2L));
		
		usuarioDao.create(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test
	public void testReadUsuario() {
		logger.info("Prueba para validar la lectura de un usuario.");

		Long id = 2L;
		Usuario usuario = usuarioDao.read(id);
		
		Assert.assertNotNull(usuario);
		Assert.assertEquals(id, usuario.getId());
		Assert.assertEquals("SupervisorMaterno", usuario.getApellidoMaterno());
		Assert.assertEquals("SupervisorPaterno", usuario.getApellidoPaterno());
		Assert.assertEquals("supervisor", usuario.getLogin());
		Assert.assertEquals("Supervisor", usuario.getNombre());
		Assert.assertEquals("supervisorpass", usuario.getPassword());
		Assert.assertTrue(usuario.getActivo());
	}
	
	@Test
	public void testValidate() {
		logger.info("Prueba para validar el login de usuario.");
		String login = "supervisor";
		String password = "supervisorpass";
		
		usuarioDao.validate(login, password);
	}
	private String getString(String string, int length) {
		StringBuilder strBuilder = new StringBuilder(string);
		while(strBuilder.length() <= length) {
			strBuilder.append("0");
		}
		
		return strBuilder.toString();
	}
}
