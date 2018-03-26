package com.entich.commons.direcciones.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.direcciones.dao.PaisDao;
import com.entich.commons.direcciones.model.Pais;

/**
 * Acceso a datos de los paises.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Repository
public class PaisDaoImpl extends GenericHibernateDaoImpl<Pais, Long> implements
		PaisDao {
}