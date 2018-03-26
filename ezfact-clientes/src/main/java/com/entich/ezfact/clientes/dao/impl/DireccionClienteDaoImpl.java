package com.entich.ezfact.clientes.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.clientes.dao.DireccionClienteDao;
import com.entich.ezfact.clientes.model.DireccionCliente;

@Repository("direccionClienteDao")
public class DireccionClienteDaoImpl extends
		GenericHibernateDaoImpl<DireccionCliente, Long> implements
		DireccionClienteDao {

}
