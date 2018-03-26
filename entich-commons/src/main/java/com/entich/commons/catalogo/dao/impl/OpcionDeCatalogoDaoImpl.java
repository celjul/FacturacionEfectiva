package com.entich.commons.catalogo.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.entich.commons.catalogo.dao.OpcionDeCatalogoDao;
import com.entich.commons.catalogo.model.OpcionDeCatalogo;
import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.exceptions.dao.DataAccessException;

@Repository
public class OpcionDeCatalogoDaoImpl extends GenericHibernateDaoImpl<OpcionDeCatalogo, Long> implements OpcionDeCatalogoDao {

	/**
	 * Log para el registro de eventos
	 */
	private static Logger LOGGER = LoggerFactory
			.getLogger(OpcionDeCatalogoDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Collection<? extends OpcionDeCatalogo> findAll(
			Class<? extends OpcionDeCatalogo> clazz) {

		try {
			return this.sessionFactory.getCurrentSession()
					.createQuery(String.format("from %s oc where activo = 1 order by descripcion asc", 
							clazz.getSimpleName())).list();
		} catch (HibernateException ex) {
			String message = String.format("Error al intentar el catalogo de opciones de tipo %s.", clazz.getSimpleName());
			LOGGER.info(message, ex);
			throw new DataAccessException(message, ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public OpcionDeCatalogo get(Class<? extends OpcionDeCatalogo> tipo, String clave) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(tipo);
		criteria.add(Restrictions.eq("clave", clave));
		List<OpcionDeCatalogo> catalogos = criteria.list();
		return !CollectionUtils.isEmpty(catalogos) ? catalogos.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<? extends OpcionDeCatalogo> search(
			Class<? extends OpcionDeCatalogo> clazz, String key) {

		try {
			Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(clazz);
			criteria.add(Restrictions.disjunction(Restrictions.like("clave", key, MatchMode.ANYWHERE), Restrictions.like("descripcion", key, MatchMode.ANYWHERE)));
			return criteria.list();
		} catch (HibernateException ex) {
			String message = String.format("Error al intentar el catalogo de opciones de tipo %s.", clazz.getSimpleName());
			LOGGER.info(message, ex);
			throw new DataAccessException(message, ex);
		}
	}
}
