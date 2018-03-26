package com.entich.emisores.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.commons.exceptions.ApplicationException;
import com.entich.ezfact.emisores.factory.CertificadoFactory;
import com.entich.ezfact.emisores.model.Certificado;

/**
 * Pruebas unitarias para la factoría de los certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 30/11/2013
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CertificadoFactoryTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(CertificadoFactoryTest.class);

	@Test
	public void _001_newInstance() {
		LOG.debug("Creando una nueva instancia de un certificado.");
		Certificado certificado = CertificadoFactory.newInstance();
		Assert.assertNotNull(certificado);
		Assert.assertNull(certificado.getNombre());
		Assert.assertNull(certificado.getPassword());
		Assert.assertNull(certificado.getSerie());
		Assert.assertNull(certificado.getCertificado());
		Assert.assertNull(certificado.getClave());
		Assert.assertNull(certificado.getEmisor());
		Assert.assertNull(certificado.getFin());
		Assert.assertNull(certificado.getId());
		Assert.assertNull(certificado.getInicio());
	}

	@Test
	public void _002_newInstanceLong() {
		LOG.debug("Creando una nueva instancia de un certificado a partir de "
				+ "un id en long.");
		Certificado certificado = CertificadoFactory.newInstance(1L);
		Assert.assertNotNull(certificado);
		Assert.assertNull(certificado.getNombre());
		Assert.assertNull(certificado.getPassword());
		Assert.assertNull(certificado.getSerie());
		Assert.assertNull(certificado.getCertificado());
		Assert.assertNull(certificado.getClave());
		Assert.assertNull(certificado.getEmisor());
		Assert.assertNull(certificado.getFin());
		Assert.assertNotNull(certificado.getId());
		Assert.assertEquals(new Long(1), certificado.getId());
		Assert.assertNull(certificado.getInicio());
	}

	@Test
	public void _003_newInstanceString() {
		LOG.debug("Creando una nueva instancia de un certificado a partir de "
				+ "un id en string.");
		Certificado certificado = CertificadoFactory.newInstance("1");
		Assert.assertNotNull(certificado);
		Assert.assertNull(certificado.getNombre());
		Assert.assertNull(certificado.getPassword());
		Assert.assertNull(certificado.getSerie());
		Assert.assertNull(certificado.getCertificado());
		Assert.assertNull(certificado.getClave());
		Assert.assertNull(certificado.getEmisor());
		Assert.assertNull(certificado.getFin());
		Assert.assertNotNull(certificado.getId());
		Assert.assertEquals(new Long(1), certificado.getId());
		Assert.assertNull(certificado.getInicio());
	}

	@SuppressWarnings("unused")
	@Test(expected = ApplicationException.class)
	public void _004_newInstanceStringVacio() {
		LOG.debug("Creando una nueva instancia de un certificado a partir de "
				+ "un id en string vacío.");
		Certificado certificado = CertificadoFactory.newInstance("");
	}
}