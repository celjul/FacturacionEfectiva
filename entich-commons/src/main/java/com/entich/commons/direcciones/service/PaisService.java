package com.entich.commons.direcciones.service;

import java.util.Collection;

import com.entich.commons.direcciones.model.Pais;

/**
 * Interface que define el comportamiento del servicio para los paises.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
public interface PaisService {

	/**
	 * M�todo que obtiene una lista de los paises existentes.
	 * 
	 * @return una lista de paises
	 * @see {@link Pais}
	 */
	Collection<Pais> findAll();
}