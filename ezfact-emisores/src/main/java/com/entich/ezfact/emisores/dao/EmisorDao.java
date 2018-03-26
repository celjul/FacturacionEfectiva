package com.entich.ezfact.emisores.dao;

import com.entich.commons.dao.GenericDao;
import com.entich.ezfact.emisores.model.Emisor;

import java.util.Date;

/**
 * Interface que define el comportamiento del acceso a datos de los emisores.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
public interface EmisorDao extends GenericDao<Emisor, Long> {

	/**
	 * Método que actualiza el logo del emisor.
	 * 
	 * @param emisor
	 *            un emisor
	 * @see {@link Emisor}
	 */
	void updateLogo(Emisor emisor);

	Integer getFoliosUtilizados(Date fechaInicio, Date fechaFin, Emisor... emisores);
}