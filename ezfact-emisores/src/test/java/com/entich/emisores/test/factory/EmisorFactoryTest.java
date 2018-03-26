package com.entich.emisores.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.commons.exceptions.ApplicationException;
import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorFisica;
import com.entich.ezfact.emisores.model.EmisorMoral;

/**
 * Pruebas unitarias para la factoría de los emisores.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 27/11/2013
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmisorFactoryTest {

	private static final Logger LOG = LoggerFactory.getLogger(EmisorFactoryTest.class);

	@Test
	public void _001_newInstanceFisica() {
		LOG.debug("Creando una nueva instancia de un emisor física.");
		Emisor emisor = EmisorFactory.newInstance(EmisorFisica.class);
		Assert.assertNotNull(emisor);
		Assert.assertNull(emisor.getRfc());
		Assert.assertNull(emisor.getDirecciones());
		Assert.assertNull(emisor.getId());
		Assert.assertNull(((EmisorFisica) emisor).getApellidoMaterno());
		Assert.assertNull(((EmisorFisica) emisor).getApellidoPaterno());
		Assert.assertNull(((EmisorFisica) emisor).getNombre());
	}

	@Test
	public void _002_newInstanceMoral() {
		LOG.debug("Creando una nueva instancia de un emisor moral.");
		Emisor emisor = EmisorFactory.newInstance(EmisorMoral.class);
		Assert.assertNotNull(emisor);
		Assert.assertNull(emisor.getRfc());
		Assert.assertNull(emisor.getDirecciones());
		Assert.assertNull(emisor.getId());
		Assert.assertNull(((EmisorMoral) emisor).getRazonSocial());
	}

	@SuppressWarnings("unused")
	@Test(expected = ApplicationException.class)
	public void _003_newInstanceSinTipo() {
		LOG.debug("Creando una nueva instancia de un emisor sin tipo.");
		Emisor emisor = EmisorFactory.newInstance(null);
	}

	@Test
	public void _004_newInstanceFisicaLong() {
		LOG.debug("Creando una nueva instancia de un emisor física a partir de " + "un id en long.");
		Emisor emisor = EmisorFactory.newInstance(EmisorFisica.class, 1L);
		Assert.assertNotNull(emisor);
		Assert.assertNull(emisor.getRfc());
		Assert.assertNull(emisor.getDirecciones());
		Assert.assertNotNull(emisor.getId());
		Assert.assertEquals(new Long(1), emisor.getId());
		Assert.assertNull(((EmisorFisica) emisor).getApellidoMaterno());
		Assert.assertNull(((EmisorFisica) emisor).getApellidoPaterno());
		Assert.assertNull(((EmisorFisica) emisor).getNombre());
	}

	@Test
	public void _005_newInstanceMoralLong() {
		LOG.debug("Creando una nueva instancia de un emisor moral a partir de " + "un id en long.");
		Emisor emisor = EmisorFactory.newInstance(EmisorMoral.class, 1L);
		Assert.assertNotNull(emisor);
		Assert.assertNull(emisor.getRfc());
		Assert.assertNull(emisor.getDirecciones());
		Assert.assertNotNull(emisor.getId());
		Assert.assertEquals(new Long(1), emisor.getId());
		Assert.assertNull(((EmisorMoral) emisor).getRazonSocial());
	}

	@SuppressWarnings("unused")
	@Test(expected = ApplicationException.class)
	public void _006_newInstanceSinTipoLong() {
		LOG.debug("Creando una nueva instancia de un emisor sin tipo a partir de " + "un id en long.");
		Emisor emisor = EmisorFactory.newInstance(null, 1L);
	}

	@Test
	public void _007_newInstanceFisicaString() {
		LOG.debug("Creando una nueva instancia de un emisor física a partir de " + "un id en string.");
		Emisor emisor = EmisorFactory.newInstance(EmisorFisica.class, "1");
		Assert.assertNotNull(emisor);
		Assert.assertNull(emisor.getRfc());
		Assert.assertNull(emisor.getDirecciones());
		Assert.assertNotNull(emisor.getId());
		Assert.assertEquals(new Long(1), emisor.getId());
		Assert.assertNull(((EmisorFisica) emisor).getApellidoMaterno());
		Assert.assertNull(((EmisorFisica) emisor).getApellidoPaterno());
		Assert.assertNull(((EmisorFisica) emisor).getNombre());
	}

	@Test
	public void _008_newInstanceMoralString() {
		LOG.debug("Creando una nueva instancia de un emisor moral a partir de " + "un id en string.");
		Emisor emisor = EmisorFactory.newInstance(EmisorMoral.class, "1");
		Assert.assertNotNull(emisor);
		Assert.assertNull(emisor.getRfc());
		Assert.assertNull(emisor.getDirecciones());
		Assert.assertNotNull(emisor.getId());
		Assert.assertEquals(new Long(1), emisor.getId());
		Assert.assertNull(((EmisorMoral) emisor).getRazonSocial());
	}

	@SuppressWarnings("unused")
	@Test(expected = ApplicationException.class)
	public void _009_newInstanceSinTipoString() {
		LOG.debug("Creando una nueva instancia de un emisor física a partir de " + "un id en string sin tipo.");
		Emisor emisor = EmisorFactory.newInstance(null, "1");
	}

	@SuppressWarnings("unused")
	@Test(expected = ApplicationException.class)
	public void _010_newInstanceFisicaSinString() {
		LOG.debug("Creando una nueva instancia de un emisor física a partir de " + "un id en string vacío.");
		Emisor emisor = EmisorFactory.newInstance(EmisorFisica.class, "");
	}

	@SuppressWarnings("unused")
	@Test(expected = ApplicationException.class)
	public void _011_newInstanceMoralStringVacio() {
		LOG.debug("Creando una nueva instancia de un emisor moral a partir de " + "un id en string vacío.");
		Emisor emisor = EmisorFactory.newInstance(EmisorMoral.class, "");
	}

	@SuppressWarnings("unused")
	@Test(expected = ApplicationException.class)
	public void _012_newInstanceSinTipoStringVacio() {
		LOG.debug("Creando una nueva instancia de un emisor sin tipo a partir " + "de un id en string vacío.");
		Emisor emisor = EmisorFactory.newInstance(null, "");
	}
}