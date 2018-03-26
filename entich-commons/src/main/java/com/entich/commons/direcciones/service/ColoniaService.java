package com.entich.commons.direcciones.service;

import java.util.Collection;
import java.util.Map;

import com.entich.commons.direcciones.model.Colonia;

/**
 * Interface que define el comportamiento del servicio para las colonias.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
public interface ColoniaService {

	/**
	 * Método que crea una nueva colonia.
	 * 
	 * @param colonia
	 *            colonia
	 * @see {@link Colonia}
	 */
	void create(Colonia colonia);

	/**
	 * Método que obtiene una lista de colonias existentes de acuerdo a los
	 * criterios especificados en el mapa.
	 * 
	 * @param map
	 *            mapa de criterios
	 * @return una lista de colonias
	 * @see {@link Colonia}
	 */
	@SuppressWarnings("rawtypes")
	Collection<Colonia> search(Map map);
}