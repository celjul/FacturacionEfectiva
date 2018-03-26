package com.entich.test.dao;

import java.util.Collection;

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

import com.entich.commons.catalogo.dao.OpcionDeCatalogoDao;
import com.entich.commons.catalogo.factory.OpcionDeCatalogoFactory;
import com.entich.commons.catalogo.model.OpcionDeCatalogo;
import com.entich.test.model.OpcionTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/datasource.xml", "/persistence.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/datasets/catalogos.xml"})
@DatabaseTearDown(value = "/datasets/tearDown.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpcionDeCatalogoDaoTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpcionDeCatalogoDaoTest.class);
	
	@Autowired
	private OpcionDeCatalogoDao opcionDeCatalogoDao;
	
	@Test
	public void getCatalogo() {
		LOGGER.info("Probando la lectura de opciones de catalogo de la base de datos.");
		
		Collection<? extends OpcionDeCatalogo> catalogo = opcionDeCatalogoDao.findAll(OpcionTest.class);
		for (OpcionDeCatalogo op : catalogo) {
			LOGGER.info(op.toString());
		}
		Assert.assertEquals(3, catalogo.size());
	}
	
	@Test
	public void getOpcionCatalogo() {
		LOGGER.info("Probando la lectura de una opcion de catalogo de la base de datos.");
		
		OpcionDeCatalogo opcion = opcionDeCatalogoDao.read(1L);
		Assert.assertNotNull(opcion);
		Assert.assertEquals(new Long(1), opcion.getId());
		Assert.assertEquals("Opcion 1 Test", opcion.getDescripcion());
		LOGGER.info(opcion.toString());
	}
	
	@Test
	public void crearCatalogo() {
		LOGGER.info("Probando la escritura de opciones de catalogo de la base de datos.");

		OpcionDeCatalogo opcion = OpcionDeCatalogoFactory.newInstance(OpcionTest.class);
		opcion.setDescripcion("Test Insertado");
		opcionDeCatalogoDao.create(opcion);
		Assert.assertNotNull(opcion.getId());
		
		LOGGER.info(String.format("El id generado es: %s", opcion.getId()));
	}
	
	@Test
	public void editarCatalogo() {
		LOGGER.info("Probando la actualizacion de opciones de catalogo de la base de datos.");

		OpcionDeCatalogo opcion = OpcionDeCatalogoFactory.newInstance(OpcionTest.class, 1L);
		opcion.setDescripcion("Actualizado");
		opcionDeCatalogoDao.update(opcion);
		
		OpcionDeCatalogo opcionAssert = opcionDeCatalogoDao.read(opcion.getId()); 
		Assert.assertEquals("{id : 1, descripcion : Actualizado}", opcionAssert.toString());
	}
}
