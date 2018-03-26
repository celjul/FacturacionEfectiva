package com.entich.emisores.test.dao;

import java.util.ArrayList;
import java.util.Collection;

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

import com.entich.commons.direcciones.factory.DireccionFactory;
import com.entich.commons.direcciones.model.Direccion;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.emisores.test.dummies.EmisorDummyGenerator;
import com.entich.ezfact.emisores.dao.EmisorDao;
import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.DireccionEmisor;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorFisica;
import com.entich.ezfact.emisores.model.EmisorMoral;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * Pruebas unitarias para el acceso a datos de los emisores.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@ContextConfiguration(value = "/persistence-test.xml")
@DatabaseSetup(value = { "/datasets/emisores.xml", "/datasets/direcciones.xml",
		"/datasets/regimenes.xml" }, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/datasets/down/down-direcciones.xml")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class EmisorDaoTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(EmisorDaoTest.class);

	@Autowired
	private EmisorDao emisorDao;

	@Test
	public void _001_init() {
		LOG.debug("Comprobando que el acceso a datos no sea nulo.");
		Assert.assertNotNull(emisorDao);
	}

	@Test
	public void _002_create() {
		LOG.debug("Creando un emisor correcto.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisorDao.create(emisor);
		Assert.assertNotNull(emisor.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _003_createNulo() {
		LOG.debug("Creando un emisor nulo.");
		emisorDao.create(null);
	}

	@Test(expected = DataAccessException.class)
	public void _004_createVacio() {
		LOG.debug("Creando un emisor vacío.");
		emisorDao.create((Emisor) EmisorFactory.newInstance(EmisorMoral.class));
	}

	@Test(expected = DataAccessException.class)
	public void _005_createSinDirecciones() {
		LOG.debug("Creando un emisor sin direcciones.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setDirecciones(null);
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _006_createDireccionesVacias() {
		LOG.debug("Creando un emisor con direcciones vacías.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setDirecciones(new ArrayList<Direccion>());
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _007_createDireccionSinCalle() {
		LOG.debug("Creando un emisor con una dirección sin calle.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(null);
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _008_createDireccionCalleVacia() {
		LOG.debug("Creando un emisor con una dirección con calle vacía.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(new String());
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _009_createCalleMuyLarga() {
		LOG.debug("Creando un emisor con una dirección con calle muy larga.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(EmisorDummyGenerator.insertChars("Calle muy larga",
				128));
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _010_createDireccionSinColonia() {
		LOG.debug("Creando un emisor con una dirección sin colonia.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		//direccion.setColonia(null);
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _011_createDireccionColoniaVacia() {
		LOG.debug("Creando un emisor con una dirección sin colonia.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		//direccion.setColonia(ColoniaFactory.newInstance());
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _012_createDireccionColoniaNoExistente() {
		LOG.debug("Creando un emisor con una dirección con colonia no existente.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		//direccion.setColonia(ColoniaFactory.newInstance(0L));
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _013_createDireccionSinNoExterior() {
		LOG.debug("Creando un emisor con una dirección sin número exterior.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(null);
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _014_createDireccionNoExteriorVacio() {
		LOG.debug("Creando un emisor con una dirección con número exterior vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(new String());
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _015_createDireccionNoExteriorMuyLargo() {
		LOG.debug("Creando un emisor con una dirección con número exterior muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(EmisorDummyGenerator.insertChars(
				"Número exterior muy largo", 32));
		emisorDao.create(emisor);
	}

	@Test
	public void _016_createDireccionSinNoInterior() {
		LOG.debug("Creando un emisor con una dirección sin número interior.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(null);
		emisorDao.create(emisor);
		Assert.assertNotNull(emisor.getId());
	}

	@Test
	public void _017_createDireccionNoInteriorVacio() {
		LOG.debug("Creando un emisor con una dirección con número interior vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(new String());
		emisorDao.create(emisor);
		Assert.assertNotNull(emisor.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _018_createDireccionNoInteriorMuyLargo() {
		LOG.debug("Creando un emisor con una dirección con número interior muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(EmisorDummyGenerator.insertChars(
				"Número interior muy largo", 32));
		emisorDao.create(emisor);
	}

	@Test
	public void _019_createDireccionSinReferencias() {
		LOG.debug("Creando un emisor con una dirección sin referencias.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setReferencias(null);
		emisorDao.create(emisor);
		Assert.assertNotNull(emisor.getId());
	}

	@Test
	public void _020_createDireccionReferenciasVacias() {
		LOG.debug("Creando un emisor con una dirección con referecnias vacías.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setReferencias(new String());
		emisorDao.create(emisor);
		Assert.assertNotNull(emisor.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _021_createDireccionReferenciasMuyLargas() {
		LOG.debug("Creando un emisor con una dirección con referencias muy largas.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(EmisorDummyGenerator.insertChars(
				"Referencias muy largas", 512));
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _022_createSinRegimen() {
		LOG.debug("Creando un emisor sin régimen.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _023_createRegimenVacio() {
		LOG.debug("Creando un emisor con régimen vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _024_createRegimenMuyLargo() {
		LOG.debug("Creando un emisor con régimen muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _025_createSinRFC() {
		LOG.debug("Creando un emisor sin RFC.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc(null);
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _026_createRFCVacio() {
		LOG.debug("Creando un emisor con RFC vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc(new String());
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _027_createRFCMuyLargo() {
		LOG.debug("Creando un emisor con RFC muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc(EmisorDummyGenerator.insertChars("RFC muy largo", 13));
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _028_createRFCMuyCorto() {
		LOG.debug("Creando un emisor con RFC muy corto.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc("CUS");
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _029_createRFCInvalido() {
		LOG.debug("Creando un emisor con RFC inválido.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		emisor.setRfc("CUSO8909497X8");
		emisorDao.create(emisor);
	}

	@Test
	public void _030_createFisicaSinApellidoMaterno() {
		LOG.debug("Creando un emisor física sin apellido materno.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoMaterno(null);
		emisorDao.create(emisor);
		Assert.assertNotNull(emisor.getId());
	}

	@Test
	public void _031_createFisicaApellidoMaternoVacio() {
		LOG.debug("Creando un emisor física con apellido materno vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoMaterno(new String());
		emisorDao.create(emisor);
		Assert.assertNotNull(emisor.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _032_createFisicaApellidoMaternoMuyLargo() {
		LOG.debug("Creando un emisor física con apellido materno muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoMaterno(EmisorDummyGenerator
				.insertChars("Apellido materno muy largo", 64));
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _033_createFisicaSinApellidoPaterno() {
		LOG.debug("Creando un emisor física sin apellido paterno.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoPaterno(null);
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _034_createFisicaApellidoPaternoVacio() {
		LOG.debug("Creando un emisor física con apellido paterno vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoPaterno(new String());
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _035_createFisicaApellidoPaternoMuyLargo() {
		LOG.debug("Creando un emisor física con apellido paterno muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setApellidoMaterno(EmisorDummyGenerator
				.insertChars("Apellido paterno muy largo", 64));
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _036_createFisicaSinNombre() {
		LOG.debug("Creando un emisor física sin nombre.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setNombre(null);
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _037_createFisicaNombreVacio() {
		LOG.debug("Creando un emisor física con nombre vacío.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setNombre(new String());
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _038_createFisicaNombreMuyLargo() {
		LOG.debug("Creando un emisor física con nombre muy largo.");
		Emisor emisor = EmisorDummyGenerator.getEmisorFisica();
		((EmisorFisica) emisor).setNombre(EmisorDummyGenerator.insertChars(
				"Nombre muy largo", 64));
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _039_createMoralSinRazonSocial() {
		LOG.debug("Creando un emisor moral sin razón social.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		((EmisorMoral) emisor).setRazonSocial(null);
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _040_createMoralRazonSocialVacia() {
		LOG.debug("Creando un emisor moral con razón social vacía.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		((EmisorMoral) emisor).setRazonSocial(new String());
		emisorDao.create(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _041_createMoralRazonSocialMuyLarga() {
		LOG.debug("Creando un emisor física con razón social muy larga.");
		Emisor emisor = EmisorDummyGenerator.getEmisorMoral();
		((EmisorMoral) emisor).setRazonSocial(EmisorDummyGenerator.insertChars(
				"Razón social muy larga", 128));
		emisorDao.create(emisor);
	}

	@Test
	public void _042_read() {
		LOG.debug("Obteniendo un emisor correcto.");
		Emisor emisor = emisorDao.read(1L);
		Assert.assertNotNull(emisor);
		Assert.assertTrue(emisor instanceof EmisorFisica);
		Assert.assertEquals("GIMV8902037X8", emisor.getRfc());
		Assert.assertEquals("Martínez",
				((EmisorFisica) emisor).getApellidoMaterno());
		Assert.assertEquals("Gil", ((EmisorFisica) emisor).getApellidoPaterno());
		Assert.assertEquals("Verónica", ((EmisorFisica) emisor).getNombre());
		Assert.assertEquals(
				DireccionFactory.newInstance(DireccionEmisor.class, 1L), emisor
						.getDirecciones().iterator().next());
	}

	@SuppressWarnings("unused")
	@Test(expected = DataAccessException.class)
	public void _043_readIdNulo() {
		LOG.debug("Obteniendo un emisor con id nulo.");
		Emisor emisor = emisorDao.read(null);
	}

	@Test
	public void _044_readIdNoExistente() {
		LOG.debug("Obteniendo un emisor con id no existente.");
		Emisor emisor = emisorDao.read(0L);
		Assert.assertNull(emisor);
	}

	@Test
	public void _045_update() {
		LOG.debug("Actualizando un emisor correcto.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setApellidoMaterno("Puente");
		((EmisorFisica) emisor).setApellidoPaterno("Hernández");
		((EmisorFisica) emisor).setNombre("Yeimi Giselle");

		DireccionEmisor direccion = (DireccionEmisor) emisor.getDirecciones().iterator().next();
		direccion.setNoInterior("Torre 2, Dpto. 19");
		direccion.setReferencias("Frente al paradero de cuatro caminos");

		DireccionEmisor direccion2 = DireccionFactory
				.newInstance(DireccionEmisor.class);
		direccion2.setCalle("Paseo de la Reforma");
		//direccion2.setColonia(ColoniaFactory.newInstance(2L));
		direccion2.setNoExterior("222");
		direccion2.setNoInterior("Piso 19");
		emisor.addDireccion(direccion2);

		emisorDao.update(emisor);

		Emisor emisor2 = emisorDao.read(emisor.getId());
		Assert.assertEquals("Hernández",
				((EmisorFisica) emisor2).getApellidoPaterno());
		Assert.assertEquals("Yeimi Giselle",
				((EmisorFisica) emisor2).getNombre());
		Assert.assertEquals(2, emisor2.getDirecciones().size());
		for (Direccion d : emisor2.getDirecciones()) {
			if (d.equals(DireccionFactory
					.newInstance(DireccionEmisor.class, 1L))) {
				Assert.assertEquals("Torre 2, Dpto. 19", d.getNoInterior());
			}
		}
	}

	@Test(expected = DataAccessException.class)
	public void _046_updateNulo() {
		LOG.debug("Actualizando un emisor nulo.");
		emisorDao.update(null);
	}

	@Test(expected = DataAccessException.class)
	public void _047_updateVacio() {
		LOG.debug("Actualizando un emisor vacío.");
		emisorDao.update((Emisor) EmisorFactory.newInstance(EmisorMoral.class));
	}

	@Test(expected = DataAccessException.class)
	public void _048_updateSinDirecciones() {
		LOG.debug("Actualizando un emisor sin direcciones.");
		Emisor emisor = emisorDao.read(1L);
		emisor.getDirecciones().clear();
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _049_updateDireccionSinCalle() {
		LOG.debug("Actualizando un emisor con una dirección sin calle.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(null);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _050_updateDireccionCalleVacia() {
		LOG.debug("Actualizando un emisor con una dirección con calle vacía.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(new String());
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _051_updateCalleMuyLarga() {
		LOG.debug("Actualizando un emisor con una dirección con calle muy larga.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setCalle(EmisorDummyGenerator.insertChars("Calle muy larga",
				128));
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _052_updateDireccionSinColonia() {
		LOG.debug("Actualizando un emisor con una dirección sin colonia.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		//direccion.setColonia(null);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _053_updateDireccionColoniaVacia() {
		LOG.debug("Actualizando un emisor con una dirección sin colonia.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		//direccion.setColonia(ColoniaFactory.newInstance());
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _054_updateDireccionColoniaNoExistente() {
		LOG.debug("Actualizando un emisor con una dirección con colonia no existente.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		//direccion.setColonia(ColoniaFactory.newInstance(0L));
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _055_updateDireccionSinNoExterior() {
		LOG.debug("Actualizando un emisor con una dirección sin número exterior.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(null);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _056_updateDireccionNoExteriorVacio() {
		LOG.debug("Actualizando un emisor con una dirección con número exterior vacío.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(new String());
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _057_updateDireccionNoExteriorMuyLargo() {
		LOG.debug("Actualizando un emisor con una dirección con número exterior muy largo.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoExterior(EmisorDummyGenerator.insertChars(
				"Número exterior muy largo", 32));
		emisorDao.update(emisor);
	}

	@Test
	public void _058_updateDireccionSinNoInterior() {
		LOG.debug("Actualizando un emisor con una dirección sin número interior.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(null);
		emisorDao.update(emisor);
		Assert.assertNull(emisor.getDirecciones().iterator().next()
				.getNoInterior());
	}

	@Test
	public void _059_updateDireccionNoInteriorVacio() {
		LOG.debug("Actualizando un emisor con una dirección con número interior vacío.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(new String());
		emisorDao.update(emisor);
		Assert.assertEquals(new String(), emisor.getDirecciones().iterator()
				.next().getNoInterior());
	}

	@Test(expected = DataAccessException.class)
	public void _060_updateDireccionNoInteriorMuyLargo() {
		LOG.debug("Actualizando un emisor con una dirección con número interior muy largo.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(EmisorDummyGenerator.insertChars(
				"Número interior muy largo", 32));
		emisorDao.update(emisor);
	}

	@Test
	public void _061_updateDireccionSinReferencias() {
		LOG.debug("Actualizando un emisor con una dirección sin referencias.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setReferencias(null);
		emisorDao.update(emisor);
		Assert.assertNull(emisor.getDirecciones().iterator().next()
				.getReferencias());
	}

	@Test
	public void _062_updateDireccionReferenciasVacias() {
		LOG.debug("Actualizando un emisor con una dirección con referecnias vacías.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setReferencias(new String());
		emisorDao.update(emisor);
		Assert.assertEquals(new String(), emisor.getDirecciones().iterator()
				.next().getReferencias());
	}

	@Test(expected = DataAccessException.class)
	public void _063_updateDireccionReferenciasMuyLargas() {
		LOG.debug("Actualizando un emisor con una dirección con referencias muy largas.");
		Emisor emisor = emisorDao.read(1L);
		Direccion direccion = emisor.getDirecciones().iterator().next();
		direccion.setNoInterior(EmisorDummyGenerator.insertChars(
				"Referencias muy largas", 512));
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _064_updateSinRegimen() {
		LOG.debug("Actualizando un emisor sin régimen.");
		Emisor emisor = emisorDao.read(1L);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _065_updateRegimenVacio() {
		LOG.debug("Actualizando un emisor con régimen vacío.");
		Emisor emisor = emisorDao.read(1L);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _066_updateRegimenMuyLargo() {
		LOG.debug("Actualizando un emisor con régimen muy largo.");
		Emisor emisor = emisorDao.read(1L);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _067_updateSinRFC() {
		LOG.debug("Actualizando un emisor sin RFC.");
		Emisor emisor = emisorDao.read(1L);
		emisor.setRfc(null);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _068_updateRFCVacio() {
		LOG.debug("Actualizando un emisor con RFC vacío.");
		Emisor emisor = emisorDao.read(1L);
		emisor.setRfc(new String());
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _069_updateRFCMuyLargo() {
		LOG.debug("Actualizando un emisor con RFC muy largo.");
		Emisor emisor = emisorDao.read(1L);
		emisor.setRfc(EmisorDummyGenerator.insertChars("RFC muy largo", 13));
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _070_updateRFCMuyCorto() {
		LOG.debug("Actualizando un emisor con RFC muy corto.");
		Emisor emisor = emisorDao.read(1L);
		emisor.setRfc("CUS");
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _071_updateRFCInvalido() {
		LOG.debug("Actualizando un emisor con RFC inválido.");
		Emisor emisor = emisorDao.read(1L);
		emisor.setRfc("CUSO8909497X8");
		emisorDao.update(emisor);
	}

	@Test
	public void _072_updateFisicaSinApellidoMaterno() {
		LOG.debug("Actualizando un emisor física sin apellido materno.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setApellidoMaterno(null);
		emisorDao.update(emisor);
		Assert.assertNull(((EmisorFisica) emisor).getApellidoMaterno());
	}

	@Test
	public void _073_updateFisicaApellidoMaternoVacio() {
		LOG.debug("Actualizando un emisor física con apellido materno vacío.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setApellidoMaterno(new String());
		emisorDao.update(emisor);
		Assert.assertEquals(new String(),
				((EmisorFisica) emisor).getApellidoMaterno());
	}

	@Test(expected = DataAccessException.class)
	public void _074_updateFisicaApellidoMaternoMuyLargo() {
		LOG.debug("Actualizando un emisor física con apellido materno muy largo.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setApellidoMaterno(EmisorDummyGenerator
				.insertChars("Apellido materno muy largo", 64));
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _075_updateFisicaSinApellidoPaterno() {
		LOG.debug("Actualizando un emisor física sin apellido paterno.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setApellidoPaterno(null);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _076_updateFisicaApellidoPaternoVacio() {
		LOG.debug("Actualizando un emisor física con apellido paterno vacío.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setApellidoPaterno(new String());
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _077_updateFisicaApellidoPaternoMuyLargo() {
		LOG.debug("Actualizando un emisor física con apellido paterno muy largo.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setApellidoMaterno(EmisorDummyGenerator
				.insertChars("Apellido paterno muy largo", 64));
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _078_updateFisicaSinNombre() {
		LOG.debug("Actualizando un emisor física sin nombre.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setNombre(null);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _079_updateFisicaNombreVacio() {
		LOG.debug("Actualizando un emisor física con nombre vacío.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setNombre(new String());
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _080_updateFisicaNombreMuyLargo() {
		LOG.debug("Actualizando un emisor física con nombre muy largo.");
		Emisor emisor = emisorDao.read(1L);
		((EmisorFisica) emisor).setNombre(EmisorDummyGenerator.insertChars(
				"Nombre muy largo", 64));
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _081_updateMoralSinRazonSocial() {
		LOG.debug("Actualizando un emisor moral sin razón social.");
		Emisor emisor = emisorDao.read(2L);
		((EmisorMoral) emisor).setRazonSocial(null);
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _082_updateMoralRazonSocialVacia() {
		LOG.debug("Actualizando un emisor moral con razón social vacía.");
		Emisor emisor = emisorDao.read(2L);
		((EmisorMoral) emisor).setRazonSocial(new String());
		emisorDao.update(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _083_updateMoralRazonSocialMuyLarga() {
		LOG.debug("Actualizando un emisor física con razón social muy larga.");
		Emisor emisor = emisorDao.read(2L);
		((EmisorMoral) emisor).setRazonSocial(EmisorDummyGenerator.insertChars(
				"Razón social muy larga", 128));
		emisorDao.update(emisor);
	}

	@Test
	public void _084_getAll() {
		LOG.debug("Obteniendo todos los emisores.");
		Collection<Emisor> emisores = emisorDao.findAll();
		Assert.assertNotNull(emisores);
		Assert.assertEquals(2, emisores.size());
	}

	@Test
	public void _085_delete() {
		LOG.debug("Eliminando un emisor.");
		Emisor emisor = emisorDao.read(2L);
		emisorDao.delete(emisor);
	}

	@Test(expected = DataAccessException.class)
	public void _086_deleteNulo() {
		LOG.debug("Eliminando un emisor nulo.");
		emisorDao.delete(null);
	}

	@Test
	public void _087_deleteVacio() {
		LOG.debug("Eliminando un emisor vacío.");
		emisorDao
				.delete((Emisor) EmisorFactory.newInstance(EmisorFisica.class));
	}

	@Test(expected = DataAccessException.class)
	public void _088_deleteNoExistente() {
		LOG.debug("Eliminando un producto vacío.");
		emisorDao.delete((Emisor) EmisorFactory.newInstance(EmisorMoral.class,
				0L));
	}
}