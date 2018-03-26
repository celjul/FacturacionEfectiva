package com.entich.ezfact.facturacion.dao.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.facturacion.dao.ComprobanteDao;
import com.entich.ezfact.facturacion.model.Comprobante;

@Repository
public class ComprobanteDaoImpl extends GenericHibernateDaoImpl<Comprobante, Long>
		implements ComprobanteDao {
	private static final Logger LOG = LoggerFactory.getLogger(ComprobanteDaoImpl.class);
	@Transactional
	@Override
	public Comprobante read(Long id) {
		Comprobante remision = super.read(id);

		if (remision != null) {
			remision.getConceptos().size();
			remision.getEmisor().getDirecciones().size();

			remision.getCliente().getContactos().size();
		}
		
		return remision;
	}
	
	@Transactional
	@Override
	public void update(Comprobante obj) {
		try {
			LOGGER.debug("Iniciando la actualizacion de información de una "
					+ "instancia persistente.");
			obj = (Comprobante) this.sessionFactory.getCurrentSession().merge(obj);
			this.sessionFactory.getCurrentSession().flush();
		} catch (HibernateOptimisticLockingFailureException ex) {
			LOGGER.error("Error en la actualización del objeto", ex);
			throw new DataAccessException("No se actualizó ningún objeto", ex);
		} catch (HibernateException ex) {
			LOGGER.error("Error en la actualización del objeto", ex);
			throw new DataAccessException("Error de acceso a datos", ex);
		} catch (ConstraintViolationException ex) {
			LOGGER.error("Error en la actualización del objeto", ex);
			throw new DataAccessException("Se esta violando una restricción "
					+ "en la base de datos.", ex);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error en la actualización del objeto", ex);
			throw new DataAccessException(
					"Existen argumentos no válidos al intentar guardar el objeto.",
					ex);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	@Override
	public Collection<Comprobante> search(Set<Map.Entry<String, Object>> set) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(getType());
			for (Map.Entry entry : set) {
				if (entry.getValue() instanceof String) {
					criteria.add(Restrictions.like((String) entry.getKey(),
							"%" + entry.getValue() + "%").ignoreCase());
				} else {
					criteria.add(Restrictions.eq((String) entry.getKey(),
							entry.getValue()));
				}
			}
			criteria.addOrder(Order.desc("fechaCreacion"));
			criteria.addOrder(Order.desc("id"));
			return criteria.list();
		} catch (HibernateException ex) {
			String message = String.format(
					"Error al recuperar los objetos del tipo {%s} desde la base "
							+ "de datos.", getType().getSimpleName());
			LOG.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public boolean folioMasivoDisponible(Emisor emisor, Integer folio) {
		String hql = "select count(c.folioMasivo) from Comprobante c where c.folioMasivo = :param and c.uuid is not null and c.emisor.id = :param1";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("param", folio);
		query.setLong("param1", emisor.getId());
		Long resultados = (Long) query.list().get(0);
		return resultados < 1;
	}
}
