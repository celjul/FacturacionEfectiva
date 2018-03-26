package com.entich.ezfact.productos.factory;

import com.entich.ezfact.productos.model.Producto;

public class ProductoFactory {
	public static Producto newInstance() {
		Producto producto = new Producto();
		return producto;
	}
	
	public static Producto newInstance(long id) {
		Producto producto = new Producto();
		producto.setId(id);
		
		return producto;
	}
}
