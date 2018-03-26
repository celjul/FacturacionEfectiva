package com.entich.commons.direcciones.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.direcciones.dao.MunicipioDao;
import com.entich.commons.direcciones.model.Municipio;

/**
 * Acceso a datos de los municipios.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 03/12/2013
 */
@Repository
public class MunicipioDaoImpl extends GenericHibernateDaoImpl<Municipio, Long>
		implements MunicipioDao {
}