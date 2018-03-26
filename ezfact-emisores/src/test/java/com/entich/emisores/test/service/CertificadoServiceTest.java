package com.entich.emisores.test.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.emisores.test.dummies.CertificadoDummyGenerator;
import com.entich.ezfact.emisores.dao.CertificadoDao;
import com.entich.ezfact.emisores.factory.CertificadoFactory;
import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorFisica;
import com.entich.ezfact.emisores.model.EmisorMoral;
import com.entich.ezfact.emisores.service.CertificadoService;

/**
 * Pruebas unitarias para el servicio de los certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 30/11/2013
 */
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/service-test.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class CertificadoServiceTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(CertificadoServiceTest.class);

	@Autowired
	private CertificadoService certificadoService;

	@ReplaceWithMock
	@Autowired
	private CertificadoDao certificadoDao;

	@Test
	public void _000_init() {
		LOG.info("Comprobando que el servicio no sea nulo");
		Assert.assertNotNull(certificadoService);
	}

	@Test
	public void _002_create() {
		LOG.debug("Creando un certificado correcto.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.atLeastOnce()).create(
				certificado);
	}

	@Test(expected = ServiceException.class)
	public void _003_createNulo() {
		LOG.debug("Creando un certificado nulo.");
		Certificado certificado = null;
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _004_createVacio() {
		LOG.debug("Creando un certificado vacío.");
		Certificado certificado = CertificadoFactory.newInstance();
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _005_createSinCertificado() {
		LOG.debug("Creando un certificado sin certificado.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setCertificado(null);
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _006_createSinClave() {
		LOG.debug("Creando un certificado sin clave.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setClave(null);
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _007_createSinEmisor() {
		LOG.debug("Creando un certificado sin emisor.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setEmisor(null);
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _008_createEmisorVacio() {
		LOG.debug("Creando un certificado con emisor vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setEmisor((Emisor) EmisorFactory
				.newInstance(EmisorFisica.class));
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Ignore
	@Test(expected = ServiceException.class)
	public void _009_createSinInicio() {
		LOG.debug("Creando un certificado sin fecha inicio.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setInicio(null);
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Ignore
	@Test(expected = ServiceException.class)
	public void _010_createSinFin() {
		LOG.debug("Creando un certificado sin fecha fin.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setFin(null);
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _011_createSinNombre() {
		LOG.debug("Creando un certificado sin nombre.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setNombre(null);
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _012_createNombreVacio() {
		LOG.debug("Creando un certificado con nombre vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setNombre(new String());
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _013_createNombreMuyLargo() {
		LOG.debug("Creando un certificado con nombre muy largo.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setNombre(CertificadoDummyGenerator.insertChars(
				"Nombre muy largo", 64));
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _014_createSinPassword() {
		LOG.debug("Creando un certificado sin password.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setPassword(null);
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _015_createPasswordVacio() {
		LOG.debug("Creando un certificado con nombre vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setPassword(new String());
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _016_createPasswordMuyLarga() {
		LOG.debug("Creando un certificado con password muy larga.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setPassword(CertificadoDummyGenerator.insertChars(
				"Password muy larga", 32));
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Ignore
	@Test(expected = ServiceException.class)
	public void _017_createSinSerie() {
		LOG.debug("Creando un certificado sin serie.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setSerie(null);
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Ignore
	@Test(expected = ServiceException.class)
	public void _018_createSerieVacio() {
		LOG.debug("Creando un certificado con serie vacía.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setSerie(new String());
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Ignore
	@Test(expected = ServiceException.class)
	public void _019_createSerieMuyLarga() {
		LOG.debug("Creando un certificado con serie muy larga.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setSerie(CertificadoDummyGenerator.insertChars(
				"Serie muy larga", 32));
		certificadoService.create(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).create(certificado);
	}

	@Test
	public void _020_read() {
		LOG.debug("Obteniendo un emisor correcto.");
		Mockito.when(certificadoDao.read(1L)).thenReturn(
				CertificadoDummyGenerator.getCertificado());
		Certificado certificado = certificadoService.read(1L);
		Assert.assertNotNull(certificado);
		Assert.assertEquals(certificado.getEmisor(),
				EmisorFactory.newInstance(EmisorMoral.class, 2L));
		Assert.assertEquals("Certificado de Prueba", certificado.getNombre());
		Assert.assertEquals("12345678a", certificado.getPassword());
	}

	@SuppressWarnings("unused")
	@Test(expected = ServiceException.class)
	public void _021_readIdNulo() {
		LOG.debug("Obteniendo un certificado con id nulo.");
		Long id = null;
		Certificado certificado = certificadoService.read(id);
		Mockito.verify(certificadoDao, Mockito.never()).read(id);
	}

	@Test
	public void _022_update() {
		LOG.debug("Actualizando un certificado correcto.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.atLeastOnce()).update(
				certificado);
	}

	@Test(expected = ServiceException.class)
	public void _023_updateNulo() {
		LOG.debug("Actualizando un certificado nulo.");
		Certificado certificado = null;
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _024_updateVacio() {
		LOG.debug("Actualizando un certificado vacío.");
		Certificado certificado = CertificadoFactory.newInstance();
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _025_updateSinCertificado() {
		LOG.debug("Actualizando un certificado sin certificado.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setCertificado(null);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _026_updateSinClave() {
		LOG.debug("Actualizando un certificado sin clave.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setClave(null);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _027_updateSinEmisor() {
		LOG.debug("Actualizando un certificado sin emisor.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setEmisor(null);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _028_updateEmisorVacio() {
		LOG.debug("Actualizando un certificado con emisor vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setEmisor((Emisor) EmisorFactory
				.newInstance(EmisorFisica.class));
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _029_updateSinInicio() {
		LOG.debug("Actualizando un certificado sin fecha inicio.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setInicio(null);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _030_updateSinFin() {
		LOG.debug("Actualizando un certificado sin fecha fin.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setFin(null);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _031_updateSinNombre() {
		LOG.debug("Actualizando un certificado sin nombre.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setNombre(null);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _032_updateNombreVacio() {
		LOG.debug("Actualizando un certificado con nombre vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setNombre(new String());
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _033_updateNombreMuyLargo() {
		LOG.debug("Actualizando un certificado con nombre muy largo.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setNombre(CertificadoDummyGenerator.insertChars(
				"Nombre muy largo", 64));
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _034_updateSinPassword() {
		LOG.debug("Actualizando un certificado sin password.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setPassword(null);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _035_updatePasswordVacio() {
		LOG.debug("Actualizando un certificado con nombre vacío.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setPassword(new String());
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _036_updatePasswordMuyLarga() {
		LOG.debug("Actualizando un certificado con password muy larga.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setPassword(CertificadoDummyGenerator.insertChars(
				"Password muy larga", 32));
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _037_updateSinSerie() {
		LOG.debug("Actualizando un certificado sin serie.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setSerie(null);
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _038_updateSerieVacio() {
		LOG.debug("Actualizando un certificado con serie vacía.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setSerie(new String());
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _039_updateSerieMuyLarga() {
		LOG.debug("Actualizando un certificado con serie muy larga.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificado.setSerie(CertificadoDummyGenerator.insertChars(
				"Serie muy larga", 32));
		certificadoService.update(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).update(certificado);
	}

	@Test
	public void _040_getAll() {
		LOG.debug("Obteniendo todos los certificados.");
		Mockito.when(certificadoDao.findAll()).thenReturn(
				new ArrayList<Certificado>() {
					private static final long serialVersionUID = 1L;
					{
						add(CertificadoDummyGenerator.getCertificado());
					}
				});
		Collection<Certificado> certificados = certificadoDao.findAll();
		Assert.assertNotNull(certificados);
		Assert.assertEquals(1, certificados.size());
	}

	@Test
	public void _041_delete() {
		LOG.debug("Eliminando un certificado.");
		Certificado certificado = CertificadoDummyGenerator.getCertificado();
		certificado.setId(2L);
		certificadoService.delete(certificado);
		Mockito.verify(certificadoDao, Mockito.atLeastOnce()).delete(
				certificado);
	}

	@Test(expected = ServiceException.class)
	public void _042_deleteNulo() {
		LOG.debug("Eliminando un certificado nulo.");
		Certificado certificado = null;
		certificadoService.delete(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).delete(certificado);
	}

	@Test(expected = ServiceException.class)
	public void _043_deleteVacio() {
		LOG.debug("Eliminando un certificado vacío.");
		Certificado certificado = CertificadoFactory.newInstance();
		certificadoService.delete(certificado);
		Mockito.verify(certificadoDao, Mockito.never()).delete(certificado);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void _044_search() {
		LOG.debug("Buscando por emisor");
		Map map = new HashMap();
		map.put("emisor", EmisorFactory.newInstance(EmisorMoral.class, 2L));
		Mockito.when(certificadoDao.search(map.entrySet())).thenReturn(
				new ArrayList<Certificado>() {
					private static final long serialVersionUID = 1L;
					{
						add(CertificadoDummyGenerator.getCertificado());
					}
				});
		Collection<Certificado> certificados = certificadoService.search(map);
		Assert.assertNotNull(certificados);
		Assert.assertEquals(1, certificados.size());
	}
	
	public static String parseHexToString(String hex) {
 		StringBuilder sb = new StringBuilder();
 		StringBuilder temp = new StringBuilder();
 
 		for (int i = 0; i < hex.length() - 1; i += 2) {
 			String output = hex.substring(i, (i + 2));
 			int decimal = Integer.parseInt(output, 16);
 			sb.append((char) decimal);
 			temp.append(decimal);
 		}
 		return sb.toString();
 	}
}