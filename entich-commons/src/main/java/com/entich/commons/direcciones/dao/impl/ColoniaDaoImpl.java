package com.entich.commons.direcciones.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.direcciones.dao.ColoniaDao;
import com.entich.commons.direcciones.model.Colonia;

/**
 * Acceso a datos de las colonias.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Repository
public class ColoniaDaoImpl extends GenericHibernateDaoImpl<Colonia, Long>
		implements ColoniaDao {
}