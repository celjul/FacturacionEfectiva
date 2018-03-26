package com.entich.commons.direcciones.service;

import java.util.Collection;
import java.util.Map;

import com.entich.commons.direcciones.model.Estado;

/**
 * Interface que define el comportamiento del servicio para los estados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
public interface EstadoService {

	/**
	 * Método que obtiene una lista de estados existentes de acuerdo a los
	 * criterios especificados en el mapa.
	 * 
	 * @param map
	 *            mapa de criterios
	 * @return una lista de estados
	 * @see {@link Estado}
	 */
	@SuppressWarnings("rawtypes")
	Collection<Estado> search(Map map);
}