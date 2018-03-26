package com.entich.ezfact.usuarios.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.usuarios.dao.RolDao;
import com.entich.ezfact.usuarios.model.Rol;

@Repository("rolDao")
public class RolDaoImpl extends GenericHibernateDaoImpl<Rol, Long> implements
		RolDao {
	
}
