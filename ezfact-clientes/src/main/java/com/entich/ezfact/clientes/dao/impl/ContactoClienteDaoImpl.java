package com.entich.ezfact.clientes.dao.impl;

import org.springframework.stereotype.Repository;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.clientes.dao.ContactoClienteDao;
import com.entich.ezfact.clientes.model.ContactoCliente;

@Repository("contactoClienteDao")
public class ContactoClienteDaoImpl extends GenericHibernateDaoImpl<ContactoCliente, Long> implements
		ContactoClienteDao {
}
