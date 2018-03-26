package com.entich.ezfact.clientes.dao;

import java.util.Collection;
import java.util.Map;

import com.entich.commons.dao.GenericDao;
import com.entich.ezfact.clientes.model.Cliente;

/**
 * Interface que define los metodos de acceso a datos para el modulo de Clientes
 * 
 * @author Pedro Josue Mendoza Islas
 * 
 */
public interface ClienteDao extends GenericDao<Cliente, Long> {

	Collection<Cliente> find(Map<String, Object> parametros);

	Cliente readByRfc(String rfc);
}