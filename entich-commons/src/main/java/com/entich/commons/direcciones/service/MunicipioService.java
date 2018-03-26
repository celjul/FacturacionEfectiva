package com.entich.commons.direcciones.service;

import java.util.Collection;
import java.util.Map;

import com.entich.commons.direcciones.model.Municipio;

/**
 * Interface que define el comportamiento del servicio para los municipios.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
public interface MunicipioService {

	/**
	 * Método que obtiene una lista de municipios existentes de acuerdo a los
	 * criterios especificados en el mapa.
	 * 
	 * @param map
	 *            mapa de criterios
	 * @return una lista de municipios
	 * @see {@link Municipio}
	 */
	@SuppressWarnings("rawtypes")
	Collection<Municipio> search(Map map);
}