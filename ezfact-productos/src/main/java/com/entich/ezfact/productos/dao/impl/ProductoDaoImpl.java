package com.entich.ezfact.productos.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.productos.dao.ProductoDao;
import com.entich.ezfact.productos.model.Producto;

@Repository
public class ProductoDaoImpl extends GenericHibernateDaoImpl<Producto, Long> 
	implements ProductoDao {


}
