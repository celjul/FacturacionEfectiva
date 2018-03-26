package com.entich.ezfact.clientes.dao.impl;

import java.util.Collection;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.ezfact.clientes.dao.ClienteDao;
import com.entich.ezfact.clientes.model.Cliente;

/**
 * Implementacion de la Interface <code>ClienteDao</code> encargada del acceso a
 * datos para el modulo de Clientes
 * 
 * @author Pedro Josue Mendoza Islas
 * 
 */
@Repository
public class ClienteDaoImpl extends GenericHibernateDaoImpl<Cliente, Long>
		implements ClienteDao {
	
	@Transactional(readOnly = true)
	@Override
	public Cliente read(Long id) {
		Cliente cliente = super.read(id);
		if (cliente != null) {
			cliente.getContactos().size();
		}
		
		return cliente;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Collection<Cliente> find(Map<String, Object> parametros) {
		
		return this.sessionFactory.getCurrentSession()
			.createQuery("from Cliente c where (c.razonSocial like :razonSocial or c.nombre like :nombre or c.apellidoPaterno like :apellidoPaterno or c.apellidoMaterno like :apellidoMaterno) and c.emisor = :emisor").setProperties(parametros).list();
		
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente readByRfc(String rfc) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Cliente.class);
		criteria.add(Restrictions.eq("rfc", rfc));
		return (Cliente) criteria.uniqueResult();
	}
}