package com.entich.ezfact.usuarios.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entich.ezfact.usuarios.dao.RolDao;
import com.entich.ezfact.usuarios.model.Rol;
import com.entich.ezfact.usuarios.service.RolService;

@Service("rolService")
public class RolServiceImpl implements RolService {
	@Autowired
	private RolDao rolDao;
	
	@Override
	public Collection<Rol> getRoles() {
		return rolDao.findAll();
	}

}
