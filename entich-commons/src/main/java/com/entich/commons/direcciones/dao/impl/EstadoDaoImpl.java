package com.entich.commons.direcciones.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.direcciones.dao.EstadoDao;
import com.entich.commons.direcciones.model.Estado;

/**
 * Acceso a datos de los estados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Repository
public class EstadoDaoImpl extends GenericHibernateDaoImpl<Estado, Long>
		implements EstadoDao {
}