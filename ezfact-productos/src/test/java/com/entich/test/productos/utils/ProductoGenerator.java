package com.entich.test.productos.utils;

import java.math.BigDecimal;

import com.entich.commons.catalogo.factory.OpcionDeCatalogoFactory;
import com.entich.ezfact.productos.factory.ProductoFactory;
import com.entich.ezfact.productos.model.Producto;
import com.entich.ezfact.productos.model.UnidadDeMedida;

public class ProductoGenerator {
	private static int INDEX = 1;
	
	public static Producto generate() {
		Producto producto = ProductoFactory.newInstance();
		producto.setCodigo("0001938449" + INDEX);
		producto.setNombre("Nombre Producto " + INDEX);
		producto.setPrecio(new BigDecimal(INDEX));
		UnidadDeMedida unidad = OpcionDeCatalogoFactory.newInstance(UnidadDeMedida.class, 1L);
		producto.setUnidadDeMedida(unidad);
		
		INDEX++;
		return producto;
	}
	
	public static Producto generate(long id) {
		Producto producto = ProductoFactory.newInstance(id);
		producto.setCodigo("0001938449" + id);
		producto.setNombre("Nombre Producto " + id);
		producto.setPrecio(new BigDecimal(id));
		UnidadDeMedida unidad = OpcionDeCatalogoFactory.newInstance(UnidadDeMedida.class, 1L);
		producto.setUnidadDeMedida(unidad);
		
		return producto;
	}
}
