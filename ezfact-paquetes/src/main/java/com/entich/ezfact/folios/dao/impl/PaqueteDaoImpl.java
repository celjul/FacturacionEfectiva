package com.entich.ezfact.folios.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.folios.dao.PaqueteDao;
import com.entich.ezfact.folios.model.Paquete;

@Repository("paquteDao")
public class PaqueteDaoImpl extends GenericHibernateDaoImpl<Paquete, Long> implements PaqueteDao {

}
