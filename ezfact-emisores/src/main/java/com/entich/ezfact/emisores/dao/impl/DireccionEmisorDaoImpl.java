package com.entich.ezfact.emisores.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.emisores.dao.DireccionEmisorDao;
import com.entich.ezfact.emisores.model.DireccionEmisor;

@Repository("direccionEmisorDao")
public class DireccionEmisorDaoImpl extends GenericHibernateDaoImpl<DireccionEmisor, Long> 
		implements DireccionEmisorDao {

}
