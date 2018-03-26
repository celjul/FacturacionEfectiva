package com.entich.ezfact.productos.service;

import java.util.Collection;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.productos.model.Producto;

public interface ProductoService {
	void create(Producto producto);
	
	Producto read(long id);

	void update(Producto producto);

	void delete(Producto producto);
	
	Collection<Producto> getAll(Emisor emisor);
	
	Collection<Producto> find(Emisor emisor, String codigo, String descripcion);
}
