package com.entich.ezfact.clientes.service;

import java.util.Collection;
import java.util.Map;

import com.entich.ezfact.clientes.model.Cliente;

/**
 * Inteface que define los metodos de la capa de servicio para el modulo de
 * Clientes
 * 
 * @author Pedro Josue Mendoza Islas
 */
public interface ClienteService {

	void create(Cliente cliente);

	Cliente read(Long id);

	void update(Cliente cliente);

	Collection<Cliente> findAll();
	
	Collection<Cliente> search(Map<String, Object> map);
	
	Collection<Cliente> find(Map<String, Object> map);
	
	Cliente readByRfc(String rfc);
}