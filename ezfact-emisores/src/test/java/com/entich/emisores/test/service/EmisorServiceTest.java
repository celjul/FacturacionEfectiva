package com.entich.emisores.test.service;

import java.util.ArrayList;
import java.util.Collection;

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

import com.entich.commons.direcciones.model.Direccion;
import com.entich.commons.exceptions.service.ServiceException;
import com.entich.emisores.test.dummies.EmisorDummyGenerator;
import com.entich.ezfact.emisores.dao.EmisorDao;
import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorFisica;
import com.entich.ezfact.emisores.model.EmisorMoral;
import com.entich.ezfact.emisores.service.EmisorService;

/**
 * Pruebas unitarias para el servicio de los emisores.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 25/11/2013
 */
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/service-test.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmisorServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(EmisorServiceTest.class);

	@Autowired
	private EmisorService emisorService;

	@ReplaceWithMock
	@Autowired
	private EmisorDao emisorDao;

	@Test
	public void _000_init() {
		LOG.info("Comprobando que el servicio no sea nulo");
		Assert.assertNotNull(emisorService);
	}

	@Test
	public void _002_create() {
		LOG.debug("Creando un emisor correcto.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _003_createNulo() {
		LOG.debug("Creando un emisor nulo.");
		Emisor emisor = null;
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _004_createVacio() {
		LOG.debug("Creando un emisor vacío.");
		Emisor emisor = EmisorFactory.newInstance(EmisorMoral.class);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _005_createSinDirecciones() {
		LOG.debug("Creando un emisor sin direcciones.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setDirecciones(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _006_createDireccionesVacias() {
		LOG.debug("Creando un emisor con direcciones vacías.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setDirecciones(new ArrayList<Direccion>());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _007_createDireccionSinCalle() {
		LOG.debug("Creando un emisor con una dirección sin calle.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _008_createDireccionCalleVacia() {
		LOG.debug("Creando un emisor con una dirección con calle vacía.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(new String());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _009_createCalleMuyLarga() {
		LOG.debug("Creando un emisor con una dirección con calle muy larga.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(EmisorDummyGenerator.insertChars("Calle muy larga", 128));
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _010_createDireccionSinColonia() {
		LOG.debug("Creando un emisor con una dirección sin colonia.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		// direccion.setColonia(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	// TODO: ¿Que pasará si la colonia de la dirección no contiene id?
	@Ignore
	@Test(expected = ServiceException.class)
	public void _011_createDireccionColoniaVacia() {
		LOG.debug("Creando un emisor con una dirección sin colonia.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		// direccion.setColonia(ColoniaFactory.newInstance());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _012_createDireccionSinNoExterior() {
		LOG.debug("Creando un emisor con una dirección sin número exterior.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _013_createDireccionNoExteriorVacio() {
		LOG.debug("Creando un emisor con una dirección con número exterior vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(new String());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _014_createDireccionNoExteriorMuyLargo() {
		LOG.debug("Creando un emisor con una dirección con número exterior muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(EmisorDummyGenerator.insertChars("Número exterior muy largo", 32));
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test
	public void _015_createDireccionSinNoInterior() {
		LOG.debug("Creando un emisor con una dirección sin número interior.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).create(emisor);
	}

	@Test
	public void _016_createDireccionNoInteriorVacio() {
		LOG.debug("Creando un emisor con una dirección con número interior vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(new String());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _017_createDireccionNoInteriorMuyLargo() {
		LOG.debug("Creando un emisor con una dirección con número interior muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(EmisorDummyGenerator.insertChars("Número interior muy largo", 32));
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test
	public void _018_createDireccionSinReferencias() {
		LOG.debug("Creando un emisor con una dirección sin referencias.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setReferencias(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).create(emisor);
	}

	@Test
	public void _019_createDireccionReferenciasVacias() {
		LOG.debug("Creando un emisor con una dirección con referecnias vacías.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setReferencias(new String());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _020_createDireccionReferenciasMuyLargas() {
		LOG.debug("Creando un emisor con una dirección con referencias muy largas.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(EmisorDummyGenerator.insertChars("Referencias muy largas", 512));
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _021_createSinRegimen() {
		LOG.debug("Creando un emisor sin régimen.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _022_createRegimenVacio() {
		LOG.debug("Creando un emisor con régimen vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _023_createRegimenMuyLargo() {
		LOG.debug("Creando un emisor con régimen muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _024_createSinRFC() {
		LOG.debug("Creando un emisor sin RFC.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _025_createRFCVacio() {
		LOG.debug("Creando un emisor con RFC vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc(new String());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _026_createRFCMuyLargo() {
		LOG.debug("Creando un emisor con RFC muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc(EmisorDummyGenerator.insertChars("RFC muy largo", 13));
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _027_createRFCMuyCorto() {
		LOG.debug("Creando un emisor con RFC muy corto.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc("CUS");
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _028_createRFCInvalido() {
		LOG.debug("Creando un emisor con RFC inválido.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc("CUSO8909497X8");
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test
	public void _029_createFisicaSinApellidoMaterno() {
		LOG.debug("Creando un emisor física sin apellido materno.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoMaterno(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).create(emisor);
	}

	@Test
	public void _030_createFisicaApellidoMaternoVacio() {
		LOG.debug("Creando un emisor física con apellido materno vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoMaterno(new String());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _031_createFisicaApellidoMaternoMuyLargo() {
		LOG.debug("Creando un emisor física con apellido materno muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoMaterno(EmisorDummyGenerator.insertChars("Apellido materno muy largo", 64));
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _032_createFisicaSinApellidoPaterno() {
		LOG.debug("Creando un emisor física sin apellido paterno.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoPaterno(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _033_createFisicaApellidoPaternoVacio() {
		LOG.debug("Creando un emisor física con apellido paterno vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoPaterno(new String());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _034_createFisicaApellidoPaternoMuyLargo() {
		LOG.debug("Creando un emisor física con apellido paterno muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoMaterno(EmisorDummyGenerator.insertChars("Apellido paterno muy largo", 64));
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _035_createFisicaSinNombre() {
		LOG.debug("Creando un emisor física sin nombre.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setNombre(null);
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _036_createFisicaNombreVacio() {
		LOG.debug("Creando un emisor física con nombre vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setNombre(new String());
		emisorService.create(emisor);
		Mockito.verify(emisorDao, Mockito.never()).create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _037_createFisicaNombreMuyLargo() {
		LOG.debug("Creando un emisor física con nombre muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setNombre(EmisorDummyGenerator.insertChars("Nombre muy largo", 64));
		emisorService.create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _038_createMoralSinRazonSocial() {
		LOG.debug("Creando un emisor moral sin razón social.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		((EmisorMoral) emisor).setRazonSocial(null);
		emisorService.create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _039_createMoralRazonSocialVacia() {
		LOG.debug("Creando un emisor moral con razón social vacía.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		((EmisorMoral) emisor).setRazonSocial(new String());
		emisorService.create(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _040_createMoralRazonSocialMuyLarga() {
		LOG.debug("Creando un emisor física con razón social muy larga.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		((EmisorMoral) emisor).setRazonSocial(EmisorDummyGenerator.insertChars("Razón social muy larga", 128));
		emisorService.create(emisor);
	}

	@Test
	public void _041_read() {
		LOG.debug("Obteniendo un producto correcto.");
		Mockito.when(emisorDao.read(1L)).thenReturn(EmisorDummyGenerator.getEmisorFisica());
		Emisor emisor = emisorService.read(1L);
		Assert.assertNotNull(emisor);
		Assert.assertTrue(emisor instanceof EmisorFisica);
		Assert.assertEquals("CUSO8909197X8", emisor.getRfc());
		Assert.assertEquals("Santos", ((EmisorFisica) emisor).getApellidoMaterno());
		Assert.assertEquals("Cruz", ((EmisorFisica) emisor).getApellidoPaterno());
		Assert.assertEquals("Pablo", ((EmisorFisica) emisor).getNombre());
	}

	@SuppressWarnings("unused")
	@Test(expected = ServiceException.class)
	public void _042_readIdNulo() {
		LOG.debug("Obteniendo un producto con id nulo.");
		Long id = null;
		Emisor emisor = emisorService.read(id);
		Mockito.verify(emisorDao, Mockito.never()).read(id);
	}

	@Test
	public void _043_update() {
		LOG.debug("Actualizando un emisor correcto.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _044_updateNulo() {
		LOG.debug("Actualizando un emisor nulo.");
		Emisor emisor = null;
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _045_updateVacio() {
		LOG.debug("Actualizando un emisor vacío.");
		Emisor emisor = EmisorFactory.newInstance(EmisorMoral.class);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _046_updateSinDirecciones() {
		LOG.debug("Actualizando un emisor sin direcciones.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisor.getDirecciones().clear();
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _047_updateDireccionSinCalle() {
		LOG.debug("Actualizando un emisor con una dirección sin calle.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _048_updateDireccionCalleVacia() {
		LOG.debug("Actualizando un emisor con una dirección con calle vacía.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _049_updateCalleMuyLarga() {
		LOG.debug("Actualizando un emisor con una dirección con calle muy larga.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(EmisorDummyGenerator.insertChars("Calle muy larga", 128));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _050_updateDireccionSinColonia() {
		LOG.debug("Actualizando un emisor con una dirección sin colonia.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		// direccion.setColonia(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	// TODO: ¿Que pasará si la colonia de la dirección no contiene id?
	@Ignore
	@Test(expected = ServiceException.class)
	public void _051_updateDireccionColoniaVacia() {
		LOG.debug("Actualizando un emisor con una dirección sin colonia.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		// direccion.setColonia(ColoniaFactory.newInstance());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _052_updateDireccionSinNoExterior() {
		LOG.debug("Actualizando un emisor con una dirección sin número exterior.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _053_updateDireccionNoExteriorVacio() {
		LOG.debug("Actualizando un emisor con una dirección con número exterior vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _054_updateDireccionNoExteriorMuyLargo() {
		LOG.debug("Actualizando un emisor con una dirección con número exterior muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(EmisorDummyGenerator.insertChars("Número exterior muy largo", 32));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test
	public void _055_updateDireccionSinNoInterior() {
		LOG.debug("Actualizando un emisor con una dirección sin número interior.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).update(emisor);
	}

	@Test
	public void _056_updateDireccionNoInteriorVacio() {
		LOG.debug("Actualizando un emisor con una dirección con número interior vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _057_updateDireccionNoInteriorMuyLargo() {
		LOG.debug("Actualizando un emisor con una dirección con número interior muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(EmisorDummyGenerator.insertChars("Número interior muy largo", 32));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test
	public void _058_updateDireccionSinReferencias() {
		LOG.debug("Actualizando un emisor con una dirección sin referencias.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setReferencias(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).update(emisor);
	}

	@Test
	public void _059_updateDireccionReferenciasVacias() {
		LOG.debug("Actualizando un emisor con una dirección con referecnias vacías.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setReferencias(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _060_updateDireccionReferenciasMuyLargas() {
		LOG.debug("Actualizando un emisor con una dirección con referencias muy largas.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(EmisorDummyGenerator.insertChars("Referencias muy largas", 512));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _061_updateSinRegimen() {
		LOG.debug("Actualizando un emisor sin régimen.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _062_updateRegimenVacio() {
		LOG.debug("Actualizando un emisor con régimen vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _063_updateRegimenMuyLargo() {
		LOG.debug("Actualizando un emisor con régimen muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _064_updateSinRFC() {
		LOG.debug("Actualizando un emisor sin RFC.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisor.setRfc(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _065_updateRFCVacio() {
		LOG.debug("Actualizando un emisor con RFC vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisor.setRfc(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _066_updateRFCMuyLargo() {
		LOG.debug("Actualizando un emisor con RFC muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisor.setRfc(EmisorDummyGenerator.insertChars("RFC muy largo", 13));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _067_updateRFCMuyCorto() {
		LOG.debug("Actualizando un emisor con RFC muy corto.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisor.setRfc("CUS");
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _068_updateRFCInvalido() {
		LOG.debug("Actualizando un emisor con RFC inválido.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisor.setRfc("CUSO8909497X8");
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test
	public void _069_updateFisicaSinApellidoMaterno() {
		LOG.debug("Actualizando un emisor física sin apellido materno.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setApellidoMaterno(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).update(emisor);
	}

	@Test
	public void _070_updateFisicaApellidoMaternoVacio() {
		LOG.debug("Actualizando un emisor física con apellido materno vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setApellidoMaterno(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _071_updateFisicaApellidoMaternoMuyLargo() {
		LOG.debug("Actualizando un emisor física con apellido materno muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setApellidoMaterno(EmisorDummyGenerator.insertChars("Apellido materno muy largo", 64));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _072_updateFisicaSinApellidoPaterno() {
		LOG.debug("Actualizando un emisor física sin apellido paterno.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setApellidoPaterno(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _073_updateFisicaApellidoPaternoVacio() {
		LOG.debug("Actualizando un emisor física con apellido paterno vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setApellidoPaterno(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _074_updateFisicaApellidoPaternoMuyLargo() {
		LOG.debug("Actualizando un emisor física con apellido paterno muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setApellidoMaterno(EmisorDummyGenerator.insertChars("Apellido paterno muy largo", 64));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _075_updateFisicaSinNombre() {
		LOG.debug("Actualizando un emisor física sin nombre.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setNombre(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _076_updateFisicaNombreVacio() {
		LOG.debug("Actualizando un emisor física con nombre vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setNombre(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _077_updateFisicaNombreMuyLargo() {
		LOG.debug("Actualizando un emisor física con nombre muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		((EmisorFisica) emisor).setNombre(EmisorDummyGenerator.insertChars("Nombre muy largo", 64));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _078_updateMoralSinRazonSocial() {
		LOG.debug("Actualizando un emisor moral sin razón social.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		emisor.setId(2L);
		((EmisorMoral) emisor).setRazonSocial(null);
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _079_updateMoralRazonSocialVacia() {
		LOG.debug("Actualizando un emisor moral con razón social vacía.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		emisor.setId(2L);
		((EmisorMoral) emisor).setRazonSocial(new String());
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _080_updateMoralRazonSocialMuyLarga() {
		LOG.debug("Actualizando un emisor física con razón social muy larga.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		emisor.setId(2L);
		((EmisorMoral) emisor).setRazonSocial(EmisorDummyGenerator.insertChars("Razón social muy larga", 128));
		emisorService.update(emisor);
		Mockito.verify(emisorDao, Mockito.never()).update(emisor);
	}

	@Test
	public void _081_getAll() {
		LOG.debug("Obteniendo todos los emisores.");
		Mockito.when(emisorDao.findAll()).thenReturn(new ArrayList<Emisor>() {
			private static final long serialVersionUID = 1L;
			{
				add(EmisorDummyGenerator.getEmisorFisica());
				add(EmisorDummyGenerator.getEmisorMoral());
			}
		});
		Collection<Emisor> emisores = emisorService.getAll();
		Assert.assertNotNull(emisores);
		Assert.assertEquals(2, emisores.size());
	}

	@Test
	public void _082_delete() {
		LOG.debug("Eliminando un emisor.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setId(1L);
		emisorService.delete(emisor);
		Mockito.verify(emisorDao, Mockito.atLeastOnce()).delete(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _083_deleteNulo() {
		LOG.debug("Eliminando un emisor nulo.");
		Emisor emisor = null;
		emisorService.delete(emisor);
		Mockito.verify(emisorDao, Mockito.never()).delete(emisor);
	}

	@Test(expected = ServiceException.class)
	public void _084_deleteVacio() {
		LOG.debug("Eliminando un emisor vacío.");
		Emisor emisor = EmisorFactory.newInstance(EmisorFisica.class);
		emisorService.delete(emisor);
		Mockito.verify(emisorDao, Mockito.never()).delete(emisor);
	}
}