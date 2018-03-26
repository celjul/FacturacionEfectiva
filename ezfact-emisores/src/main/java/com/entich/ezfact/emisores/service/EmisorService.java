package com.entich.ezfact.emisores.service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.entich.ezfact.emisores.model.Emisor;

/**
 * Interface que define el comportamiento del servicio de los emisores.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
public interface EmisorService {

	/**
	 * Método que obtiene una lista de los emisores existentes.
	 * 
	 * @return una lista de emisores
	 * @see {@link Emisor}
	 */
	Collection<Emisor> getAll();

	/**
	 * Método que crea un nuevo emisor.
	 * 
	 * @param emisor
	 *            emisor
	 * @see {@link Emisor}
	 */
	void create(Emisor emisor);

	/**
	 * Método que obtiene un emisor a partir de un id.
	 * 
	 * @param id
	 *            id
	 * @return un emisor
	 * @see {@link Emisor}
	 */
	Emisor read(Long id);

	/**
	 * Método que actualiza un emisor.
	 * 
	 * @param emisor
	 *            emisor
	 * @see {@link Emisor}
	 */
	void update(Emisor emisor);

	/**
	 * Método que elimina un emisor.
	 * 
	 * @param emisor
	 *            emisor
	 * @see {@link Emisor}
	 */
	void delete(Emisor emisor);

	/**
	 * Método que obtiene una lista de emisores existentes de acuerdo a los
	 * criterios especificados en el mapa.
	 * 
	 * @param map
	 *            mapa de criterios
	 * @return una lista de productos
	 * @see {@link Emisor}
	 */
	@SuppressWarnings("rawtypes")
	Collection<Emisor> search(Map map);

	Integer getFoliosUtilizados(Date fechaInicio, Date fechaFin, Emisor... emisores);
}