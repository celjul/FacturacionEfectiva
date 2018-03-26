package com.entich.emisores.test.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.emisores.test.dummies.CertificadoDummyGenerator;
import com.entich.ezfact.emisores.dao.CertificadoDao;
import com.entich.ezfact.emisores.factory.CertificadoFactory;
import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorFisica;
import com.entich.ezfact.emisores.model.EmisorMoral;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * Pruebas unitarias para el acceso a datos de los certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 29/11/2013
 */
@ContextConfiguration(value = "/mysql/persistence-test.xml")
@DatabaseSetup(value = { "/datasets/emisores.xml", "/datasets/certificados.xml" }, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/datasets/down/down-certificados.xml")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class CertificadoDaoTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(CertificadoDaoTest.class);

	@Autowired
	private CertificadoDao certificadoDao;

	@Test
	public void _001_init() {
		LOG.debug("Comprobando que el acceso a datos no sea nulo.");
		Assert.assertNotNull(certificadoDao);
	}

	@Test
	public void _002_create() {
		LOG.debug("Creando un certificado correcto.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificadoDao.create(certificado);
		Assert.assertNotNull(certificado.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _003_createNulo() {
		LOG.debug("Creando un certificado nulo.");
		certificadoDao.create(null);
	}

	@Test(expected = DataAccessException.class)
	public void _004_createVacio() {
		LOG.debug("Creando un certificado vacío.");
		certificadoDao.create(CertificadoFactory.newInstance());
	}

	@Test(expected = DataAccessException.class)
	public void _005_createSinCertificado() {
		LOG.debug("Creando un certificado sin certificado.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setCertificado(null);
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _006_createSinClave() {
		LOG.debug("Creando un certificado sin clave.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setClave(null);
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _007_createSinEmisor() {
		LOG.debug("Creando un certificado sin emisor.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setEmisor(null);
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _008_createEmisorVacio() {
		LOG.debug("Creando un certificado con emisor vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setEmisor((Emisor) EmisorFactory
				.newInstance(EmisorFisica.class));
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _009_createEmisorNoExistente() {
		LOG.debug("Creando un certificado con emisor no existente.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setEmisor((Emisor) EmisorFactory.newInstance(
				EmisorFisica.class, 0L));
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _010_createSinInicio() {
		LOG.debug("Creando un certificado sin fecha inicio.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setInicio(null);
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _011_createSinFin() {
		LOG.debug("Creando un certificado sin fecha fin.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setFin(null);
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _012_createSinNombre() {
		LOG.debug("Creando un certificado sin nombre.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setNombre(null);
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _013_createNombreVacio() {
		LOG.debug("Creando un certificado con nombre vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setNombre(new String());
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _014_createNombreMuyLargo() {
		LOG.debug("Creando un certificado con nombre muy largo.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setNombre(CertificadoDummyGenerator.insertChars(
				"Nombre muy largo", 64));
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _015_createSinPassword() {
		LOG.debug("Creando un certificado sin password.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setPassword(null);
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _016_createPasswordVacio() {
		LOG.debug("Creando un certificado con nombre vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setPassword(new String());
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _017_createPasswordMuyLarga() {
		LOG.debug("Creando un certificado con password muy larga.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setPassword(CertificadoDummyGenerator.insertChars(
				"Password muy larga", 32));
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _018_createSinSerie() {
		LOG.debug("Creando un certificado sin serie.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setSerie(null);
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _019_createSerieVacio() {
		LOG.debug("Creando un certificado con serie vacía.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setSerie(new String());
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _020_createSerieMuyLarga() {
		LOG.debug("Creando un certificado con serie muy larga.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setSerie(CertificadoDummyGenerator.insertChars(
				"Serie muy larga", 32));
		certificadoDao.create(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _021_createSerieRepetida() {
		LOG.debug("Creando un certificado son serie repetida.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setSerie("00001000000202569219");
		certificadoDao.create(certificado);
	}

	@Test
	public void _022_read() {
		LOG.debug("Obteniendo un emisor correcto.");
		Certificado certificado = certificadoDao.read(1L);
		Assert.assertNotNull(certificado);
		// Assert.assertEquals(certificado.getEmisor(),
		// EmisorFactory.newInstance(EmisorFisica.class, 1L));
		Assert.assertEquals("Certificado VGM", certificado.getNombre());
		Assert.assertEquals("changeme", certificado.getPassword());
		Assert.assertEquals("00001000000202569219", certificado.getSerie());
	}

	@SuppressWarnings("unused")
	@Test(expected = DataAccessException.class)
	public void _023_readIdNulo() {
		LOG.debug("Obteniendo un certificado con id nulo.");
		Long id = null;
		Certificado certificado = certificadoDao.read(id);
	}

	@Test
	public void _024_readIdNoExistente() {
		LOG.debug("Obteniendo un certificado con id no existente.");
		Certificado certificado = certificadoDao.read(0L);
		Assert.assertNull(certificado);
	}

	@Test
	public void _025_update() {
		LOG.debug("Actualizando un certificado correcto.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setPassword("12345678a");

		certificadoDao.update(certificado);

		Certificado certificado2 = certificadoDao.read(certificado.getId());
		Assert.assertEquals(certificado.getPassword(),
				certificado2.getPassword());
	}

	@Test(expected = DataAccessException.class)
	public void _026_updateNulo() {
		LOG.debug("Actualizando un certificado nulo.");
		certificadoDao.update(null);
	}

	@Test(expected = DataAccessException.class)
	public void _027_updateVacio() {
		LOG.debug("Actualizando un certificado vacío.");
		certificadoDao.update(CertificadoFactory.newInstance());
	}

	@Test(expected = DataAccessException.class)
	public void _028_updateSinCertificado() {
		LOG.debug("Actualizando un certificado sin certificado.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setCertificado(null);
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _029_updateSinClave() {
		LOG.debug("Actualizando un certificado sin clave.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setClave(null);
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _030_updateSinEmisor() {
		LOG.debug("Actualizando un certificado sin emisor.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setEmisor(null);
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _031_updateEmisorVacio() {
		LOG.debug("Actualizando un certificado con emisor vacío.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setEmisor((Emisor) EmisorFactory
				.newInstance(EmisorFisica.class));
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _032_updateEmisorNoExistente() {
		LOG.debug("Actualizando un certificado con emisor no existente.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setEmisor((Emisor) EmisorFactory.newInstance(
				EmisorFisica.class, 0L));
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _033_updateSinInicio() {
		LOG.debug("Actualizando un certificado sin fecha inicio.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setInicio(null);
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _034_updateSinFin() {
		LOG.debug("Actualizando un certificado sin fecha fin.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setFin(null);
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _035_updateSinNombre() {
		LOG.debug("Actualizando un certificado sin nombre.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setNombre(null);
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _036_updateNombreVacio() {
		LOG.debug("Actualizando un certificado con nombre vacío.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setNombre(new String());
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _037_updateNombreMuyLargo() {
		LOG.debug("Actualizando un certificado con nombre muy largo.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setNombre(CertificadoDummyGenerator.insertChars(
				"Nombre muy largo", 64));
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _038_updateSinPassword() {
		LOG.debug("Actualizando un certificado sin password.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setPassword(null);
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _039_updatePasswordVacio() {
		LOG.debug("Actualizando un certificado con nombre vacío.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setPassword(new String());
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _040_updatePasswordMuyLarga() {
		LOG.debug("Actualizando un certificado con password muy larga.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setPassword(CertificadoDummyGenerator.insertChars(
				"Password muy larga", 32));
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _041_updateSinSerie() {
		LOG.debug("Actualizando un certificado sin serie.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setSerie(null);
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _042_updateSerieVacio() {
		LOG.debug("Actualizando un certificado con serie vacía.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setSerie(new String());
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _043_updateSerieMuyLarga() {
		LOG.debug("Actualizando un certificado con serie muy larga.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setSerie(CertificadoDummyGenerator.insertChars(
				"Serie muy larga", 32));
		certificadoDao.update(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _044_updateSerieRepetida() {
		LOG.debug("Actualizando un certificado son serie repetida.");
		Certificado certificado = certificadoDao.read(1L);
		certificado.setSerie("00001000000202569220");
		certificadoDao.update(certificado);
	}

	@Test
	public void _045_getAll() {
		LOG.debug("Obteniendo todos los certificados.");
		Collection<Certificado> certificados = certificadoDao.findAll();
		Assert.assertNotNull(certificados);
		Assert.assertEquals(2, certificados.size());
	}

	@Test
	public void _046_delete() {
		LOG.debug("Eliminando un certificado.");
		Certificado certificado = certificadoDao.read(2L);
		certificadoDao.delete(certificado);
	}

	@Test(expected = DataAccessException.class)
	public void _047_deleteNulo() {
		LOG.debug("Eliminando un certificado nulo.");
		certificadoDao.delete(null);
	}

	@Test
	public void _048_deleteVacio() {
		LOG.debug("Eliminando un certificado vacío.");
		certificadoDao.delete(CertificadoFactory.newInstance());
	}

	@Test(expected = DataAccessException.class)
	public void _049_deleteNoExistente() {
		LOG.debug("Eliminando un producto vacío.");
		certificadoDao.delete(CertificadoFactory.newInstance(0L));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void _050_search() {
		LOG.debug("Buscando por emisor");
		Map map = new HashMap();
		map.put("emisor", EmisorFactory.newInstance(EmisorMoral.class, 2L));
		Collection<Certificado> certificados = certificadoDao.search(map
				.entrySet());
		Assert.assertNotNull(certificados);
		Assert.assertEquals(1, certificados.size());
		Assert.assertTrue(certificados.contains(CertificadoFactory
				.newInstance(2L)));
	}
}