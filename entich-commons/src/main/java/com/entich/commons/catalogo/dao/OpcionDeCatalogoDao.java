package com.entich.commons.catalogo.dao;

import java.util.Collection;

import com.entich.commons.catalogo.model.OpcionDeCatalogo;
import com.entich.commons.dao.GenericDao;

public interface OpcionDeCatalogoDao extends GenericDao<OpcionDeCatalogo, Long> {
	/**
	 * Devuelve el catalogo de opciones existentes en el sistema para el tipo especificado.
	 * 
	 * @param clazz Tipo de objetos del catalogo que se esta buscando.
	 * @return Lista de opciones de catalogo del tipo seleccionado.
	 */
	Collection<? extends OpcionDeCatalogo> findAll(Class<? extends OpcionDeCatalogo> clazz);
	
	OpcionDeCatalogo get(Class<? extends OpcionDeCatalogo> tipo, String clave);
	
	Collection<? extends OpcionDeCatalogo> search(
			Class<? extends OpcionDeCatalogo> clazz, String key);
}
