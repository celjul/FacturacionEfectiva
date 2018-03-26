package com.entich.test.productos.dao;

import java.math.BigDecimal;

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

import com.entich.commons.catalogo.factory.OpcionDeCatalogoFactory;
import com.entich.commons.exceptions.ApplicationException;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.ezfact.productos.dao.ProductoDao;
import com.entich.ezfact.productos.factory.ProductoFactory;
import com.entich.ezfact.productos.model.Producto;
import com.entich.ezfact.productos.model.UnidadDeMedida;
import com.entich.test.productos.utils.ProductoGenerator;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/datasource-test-productos.xml", "/persistence-test-productos.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/datasets/unidades-medida.xml", "/datasets/productos.xml"})
@DatabaseTearDown(value = "/datasets/tearDown.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductoDaoTest {
	private static final int CODIGO_LENGTH = 50;

	private static final int NOMBRE_LENGTH = 500;

	private static final Logger logger = LoggerFactory.getLogger(ProductoDaoTest.class);
	
	private static final String PLANTILLA = "Prueba para validar la %s de un producto %s.";
	@Autowired
	private ProductoDao productoDao;
	
	@Test(expected = ApplicationException.class)
	public void crearProductoNulo() {
		logger.info(String.format(PLANTILLA, "creación", "nulo"));
		
		productoDao.create(null);
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoVacio() {
		logger.info(String.format(PLANTILLA, "creación", "vacio"));
		
		productoDao.create(ProductoFactory.newInstance());
	}
	
	@Test
	public void crearProductoCorrecto() {
		logger.info(String.format(PLANTILLA, "creación", "correcto"));
		Producto producto = ProductoGenerator.generate();
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoSinCodigo() {
		logger.info(String.format(PLANTILLA, "creación", "sin código"));
		Producto producto = ProductoGenerator.generate();
		producto.setCodigo(null);
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoConCodigoVacio() {
		logger.info(String.format(PLANTILLA, "creación", "con código vacío"));
		Producto producto = ProductoGenerator.generate();
		producto.setCodigo("");
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoConCodigoLongitudMayor() {
		logger.info(String.format(PLANTILLA, "creación", "con codigo con longitud mayor a la permitida"));
		Producto producto = ProductoGenerator.generate();
		producto.setCodigo(getString(producto.getCodigo(), CODIGO_LENGTH));
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoSinNombre() {
		logger.info(String.format(PLANTILLA, "creación", "sin nombre"));
		Producto producto = ProductoGenerator.generate();
		producto.setNombre(null);
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoConNombreVacio() {
		logger.info(String.format(PLANTILLA, "creación", "con nombre vacío"));
		Producto producto = ProductoGenerator.generate();
		producto.setNombre("");
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoConNombreLongitudMayor() {
		logger.info(String.format(PLANTILLA, "creación", "con nombre con longitud mayor a la permitida"));
		Producto producto = ProductoGenerator.generate();
		producto.setNombre(getString(producto.getNombre(), NOMBRE_LENGTH));
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoConPrecioNegativo() {
		logger.info(String.format(PLANTILLA, "creación", "con nombre con longitud mayor a la permitida"));
		Producto producto = ProductoGenerator.generate();
		producto.setPrecio(new BigDecimal(-1));
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoCodigoRepetido() {
		logger.info(String.format(PLANTILLA, "creación", "con un código existente"));
		Producto producto = ProductoGenerator.generate();
		producto.setCodigo("9234986240021");
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test(expected = ApplicationException.class)
	public void crearProductoSinUnidadDeMedida() {
		logger.info(String.format(PLANTILLA, "creación", "sin unidad de medida"));
		Producto producto = ProductoGenerator.generate();
		producto.setUnidadDeMedida(null);
		productoDao.create(producto);
		
		Assert.assertNotNull(producto.getId());
	}
	
	@Test
	public void recuperarProducto() {
		logger.info(String.format(PLANTILLA, "recuperación", "completo"));
		Long id = 1L;
		Producto producto = productoDao.read(id);
		Assert.assertEquals(id, producto.getId());
		Assert.assertEquals("9234986240021", producto.getCodigo());
		Assert.assertEquals("Producto 1", producto.getNombre());
		Assert.assertEquals(new BigDecimal(0.50).setScale(2), producto.getPrecio());
		Assert.assertNotNull(producto.getUnidadDeMedida());
		Assert.assertEquals("Pieza", producto.getUnidadDeMedida().getDescripcion());
	}
	
	@Test
	public void actualizarCodigoProducto() {
		logger.info(String.format(PLANTILLA, "edición", "codigo correcto"));
		Long id = 2L;
		Producto producto = productoDao.read(id);
		
		String codigo = "9000986240022";
		producto.setCodigo(codigo);
		
		productoDao.update(producto);
		Producto productoDB = productoDao.read(id);
		Assert.assertEquals(id, productoDB.getId());
		Assert.assertEquals(codigo, productoDB.getCodigo());
		Assert.assertEquals("Producto 2", productoDB.getNombre());
		Assert.assertEquals(new BigDecimal(120.50).setScale(2), productoDB.getPrecio());
		Assert.assertNotNull(productoDB.getUnidadDeMedida());
		Assert.assertEquals("Caja", productoDB.getUnidadDeMedida().getDescripcion());
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoCodigoVacio() {
		logger.info(String.format(PLANTILLA, "edición", "código vacio"));
		Long id = 2L;
		Producto producto = productoDao.read(id);
		
		String codigo = "";
		producto.setCodigo(codigo);
		
		productoDao.update(producto);
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoCodigoNulo() {
		logger.info(String.format(PLANTILLA, "edición", "código nulo"));
		Long id = 2L;
		Producto producto = productoDao.read(id);
		
		String codigo = null;
		producto.setCodigo(codigo);
		
		productoDao.update(producto);
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoCodigoMuyLargo() {
		logger.info(String.format(PLANTILLA, "edición", "código muy largo"));
		Long id = 2L;
		Producto producto = productoDao.read(id);
		
		String codigo = getString(producto.getCodigo(), CODIGO_LENGTH);
		producto.setCodigo(codigo);
		
		productoDao.update(producto);
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoCodigoRepetido() {
		logger.info(String.format(PLANTILLA, "edición", "código repetido"));
		Long id = 2L;
		Producto producto = productoDao.read(id);
		
		String codigo = "9234986240021";
		producto.setCodigo(codigo);
		
		productoDao.update(producto);
	}
	
	@Test
	public void actualizarNombreProducto() {
		logger.info(String.format(PLANTILLA, "edición", "nombre permitido"));
		Long id = 3L;
		Producto producto = productoDao.read(id);
		
		String nombre = "Producto 3 editado";
		producto.setNombre(nombre);
		
		Assert.assertEquals(id, producto.getId());
		Assert.assertEquals("9234986240023", producto.getCodigo());
		Assert.assertEquals(nombre, producto.getNombre());
		Assert.assertEquals(new BigDecimal(6.78).setScale(2, BigDecimal.ROUND_HALF_UP), producto.getPrecio());
		Assert.assertNotNull(producto.getUnidadDeMedida());
		Assert.assertEquals("Litro", producto.getUnidadDeMedida().getDescripcion());
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoNombreVacio() {
		logger.info(String.format(PLANTILLA, "edición", "nombre vacio"));
		Long id = 3L;
		Producto producto = productoDao.read(id);
		
		String nombre = "";
		producto.setNombre(nombre);
		
		productoDao.update(producto);
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoNombreNulo() {
		logger.info(String.format(PLANTILLA, "edición", "nombre nulo"));
		Long id = 3L;
		Producto producto = productoDao.read(id);
		
		String nombre = null;
		producto.setNombre(nombre);
		
		productoDao.update(producto);
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoNombreMuyLargo() {
		logger.info(String.format(PLANTILLA, "edición", "nombre muy largo"));
		Long id = 3L;
		Producto producto = productoDao.read(id);
		
		String nombre = getString(producto.getNombre(), NOMBRE_LENGTH);
		producto.setNombre(nombre);
		
		productoDao.update(producto);
	}
	
	@Test
	public void actualizarPrecioProducto() {
		logger.info(String.format(PLANTILLA, "edición", "nombre permitido"));
		Long id = 4L;
		Producto producto = productoDao.read(id);
		
		BigDecimal precio = new BigDecimal(1232.34);
		producto.setPrecio(precio);
		
		Assert.assertEquals(id, producto.getId());
		Assert.assertEquals("9234986240024", producto.getCodigo());
		Assert.assertEquals("Producto 4", producto.getNombre());
		Assert.assertEquals(precio, producto.getPrecio());
		Assert.assertNotNull(producto.getUnidadDeMedida());
		Assert.assertEquals("Caja", producto.getUnidadDeMedida().getDescripcion());
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoPrecioIncorrecto() {
		logger.info(String.format(PLANTILLA, "edición", "nombre vacio"));
		Long id = 4L;
		Producto producto = productoDao.read(id);
		
		producto.setPrecio(new BigDecimal(-1));
		
		productoDao.update(producto);
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoPrecioNulo() {
		logger.info(String.format(PLANTILLA, "edición", "nombre nulo"));
		Long id = 4L;
		Producto producto = productoDao.read(id);
		
		producto.setPrecio(null);
		
		productoDao.update(producto);
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoPrecioMuyLargo() {
		logger.info(String.format(PLANTILLA, "edición", "nombre muy largo"));
		Long id = 4L;
		Producto producto = productoDao.read(id);
		
		producto.setPrecio(new BigDecimal(99999999999999999999999.99));
		productoDao.update(producto);
	}
	
	@Test
	public void actualizarProductoUnidadDeMedida() {
		logger.info(String.format(PLANTILLA, "edición", "unidad de medida"));
		Producto producto = ProductoGenerator.generate();
		Long id = 5L;
		producto.setId(id);
		UnidadDeMedida unidad = OpcionDeCatalogoFactory.newInstance(UnidadDeMedida.class, 3L);
		producto.setUnidadDeMedida(unidad);
		
		productoDao.update(producto);
		
		Producto productoDB = productoDao.read(id);
		Assert.assertNotNull(productoDB.getId());
		Assert.assertEquals("Litro", productoDB.getUnidadDeMedida().getDescripcion());
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoUnidadDeMedidaNula() {
		logger.info(String.format(PLANTILLA, "creación", "unidad de medida nula"));
		Producto producto = ProductoGenerator.generate();
		Long id = 5L;
		producto.setId(id);
		UnidadDeMedida unidad = null;
		producto.setUnidadDeMedida(unidad);
		
		productoDao.update(producto);
	}
	
	@Test(expected = DataAccessException.class)
	public void actualizarProductoUnidadDeMedidaNoExistente() {
		logger.info(String.format(PLANTILLA, "creación", "unidad de medida no existente"));
		Producto producto = ProductoGenerator.generate();
		Long id = 5L;
		producto.setId(id);
		UnidadDeMedida unidad = OpcionDeCatalogoFactory.newInstance(UnidadDeMedida.class, 324L);
		producto.setUnidadDeMedida(unidad);
		
		productoDao.update(producto);
	}
	
	@Test
	public void borrarProducto(){
		logger.info(String.format(PLANTILLA, "eliminación", "existente"));
		Producto producto = ProductoGenerator.generate();
		Long id = 6L;
		producto.setId(id);
		
		productoDao.delete(producto);
		
		Producto producto2 = productoDao.read(id);
		Assert.assertNull(producto2);
	}
	
	@Test(expected = DataAccessException.class)
	public void borrarProductoNoExistente(){
		logger.info(String.format(PLANTILLA, "eliminación", "no existente"));
		Producto producto = ProductoGenerator.generate();
		Long id = 122L;
		producto.setId(id);
		
		productoDao.delete(producto);
		
		Producto producto2 = productoDao.read(id);
		Assert.assertNull(producto2);
	}
	
	private String getString(String string, int length) {
		StringBuilder strBuilder = new StringBuilder(string);
		while(strBuilder.length() <= length) {
			strBuilder.append("0");
		}
		
		return strBuilder.toString();
	}
}
