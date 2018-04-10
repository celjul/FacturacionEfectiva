package com.entich.ezfact.folios.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entich.ezfact.folios.dao.PaqueteDao;
import com.entich.ezfact.folios.model.Paquete;
import com.entich.ezfact.folios.service.PaqueteService;

@Service
public class PaqueteServiceImpl implements PaqueteService {

	@Autowired
	private PaqueteDao paqueteDao;
	
	@Override
	public Collection<Paquete> getPaquetes() {
		return paqueteDao.findAll();
	}
}
