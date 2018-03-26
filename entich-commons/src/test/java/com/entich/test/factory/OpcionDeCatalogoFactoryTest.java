package com.entich.test.factory;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entich.commons.catalogo.factory.OpcionDeCatalogoFactory;
import com.entich.commons.exceptions.ApplicationException;
import com.entich.test.model.OpcionTest;

public class OpcionDeCatalogoFactoryTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(OpcionDeCatalogoFactoryTest.class);

	@Test
	public void testClaseDeInstanciaDevuelta() {
		LOG.info("Pruaba para validar que se devuelva una instancia de la clase indicada.");
		
		Assert.assertTrue(OpcionDeCatalogoFactory
				.newInstance(OpcionTest.class) instanceof OpcionTest);
	}

	@Test
	public void testInstanciaVacia() {
		LOG.info("Test para validar que se devuelva una instancia vacia.");
		
		OpcionTest opcion = OpcionDeCatalogoFactory
				.newInstance(OpcionTest.class);

		Assert.assertNotNull(opcion);
		Assert.assertNull(opcion.getId());
		Assert.assertNull(opcion.getDescripcion());
	}

	@Test
	public void testInstanciaIdLong() {
		LOG.info("Test para validar que se devuelva una instancia con el Id especificado.");
		OpcionTest opcion = OpcionDeCatalogoFactory.newInstance(
				OpcionTest.class, 1L);

		Assert.assertNotNull(opcion);
		Assert.assertEquals(Long.valueOf(1), opcion.getId());
		Assert.assertNull(opcion.getDescripcion());
	}

	@Test(expected = ApplicationException.class)
	public void testInstanciaIdLongNulo() {
		LOG.info("Test para validar que la creacion de una instancia con un nulo como parametro para el id");
		Long id = null;
		OpcionDeCatalogoFactory.newInstance(OpcionTest.class, id);
	}

	@Test
	public void testInstanciaIdString() {
		LOG.info("Test para validar la creacion de una instancia con id tipo String");
		OpcionTest opcion = OpcionDeCatalogoFactory.newInstance(
				OpcionTest.class, "1");

		Assert.assertNotNull(opcion);
		Assert.assertEquals(Long.valueOf(1), opcion.getId());
		Assert.assertNull(opcion.getDescripcion());
	}

	@Test(expected = ApplicationException.class)
	public void testInstanciaIdStringNoValido() {
		LOG.info("Test para validar la creacion de una instancia con id tipo String no valido");
		OpcionTest opcion = OpcionDeCatalogoFactory.newInstance(
				OpcionTest.class, "asdf");

		Assert.assertNotNull(opcion);
		Assert.assertEquals(Long.valueOf(1), opcion.getId());
		Assert.assertNull(opcion.getDescripcion());
	}

	@Test(expected = ApplicationException.class)
	public void testInstanciaIdStringNulo() {
		LOG.info("Test para validar la creacion de una instancia con id tipo String nulo");
		String id = null;
		OpcionTest opcion = OpcionDeCatalogoFactory.newInstance(
				OpcionTest.class, id);

		Assert.assertNotNull(opcion);
		Assert.assertEquals(Long.valueOf(1), opcion.getId());
		Assert.assertNull(opcion.getDescripcion());
	}

	@Test
	public void testInstanciaIdLongConDescripcion() {
		LOG.info("Test para validar la creacion de una instancia con descripcion");
		String descripcion = "Descripcion";
		Long id = 1l;
		OpcionTest opcion = OpcionDeCatalogoFactory.newInstance(
				OpcionTest.class, id, descripcion);

		Assert.assertNotNull(opcion);
		Assert.assertEquals(id, opcion.getId());
		Assert.assertEquals(descripcion, opcion.getDescripcion());
	}
}
