package com.entich.ezfact.emisores.dao.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.ezfact.emisores.dao.EmisorDao;
import com.entich.ezfact.emisores.model.Emisor;

/**
 * Acceso a datos de los emisores.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 21/11/2013
 */
@Repository
public class EmisorDaoImpl extends GenericHibernateDaoImpl<Emisor, Long>
		implements EmisorDao {
	
	@Transactional(readOnly = true)
	@Override
	public Emisor read(Long id) {
		Emisor emisor = super.read(id);
		if (emisor != null) {
			emisor.getDirecciones().size();
		}
		
		return emisor;
	}

	@Transactional
	@Override
	public void updateLogo(Emisor emisor) {

		try {
			String queryString = "update Emisor e set e.logo = :logoEmisor "
					+ "where e.id = :idEmisor";
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					queryString);
			query.setBinary("logoEmisor", emisor.getLogo());
			query.setLong("idEmisor", emisor.getId());
			query.executeUpdate();
		} catch (HibernateException ex) {
			String message = "Error al actualizar el logo del emisor";
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Collection<Emisor> findAll() {
		try {
			LOGGER.debug("Iniciando la recuperacion de las instancias "
					+ "persistentes.");

			Query query = sessionFactory.getCurrentSession().createQuery(
					String.format("from %s obj order by razonSocial, nombre, "
							+ "apellidoPaterno, apellidoMaterno asc", 
							getType().getSimpleName()));
			
			return query.list();
		} catch (HibernateException ex) {
			String mensaje = String.format("Error al recuperar los objetos "
					+ "de tipo {%s} en la base de datos", getType()
					.getSimpleName());
			LOGGER.error(mensaje, ex);
			throw new DataAccessException(mensaje, ex);
		}
	}

	@Transactional
	@Override
	public Integer getFoliosUtilizados(Date fechaInicio, Date fechaFin, Emisor... emisores) {
		try {
			String query = "select count(*) from Comprobante c where "
					+ "c.uuid != null and c.emisor in (:emisor) "
					+ "and c.fechaTimbrado between :inicio and :fin";

			Query hQuery = this.sessionFactory.getCurrentSession().createQuery(query);

			hQuery.setParameterList("emisor", emisores);
			hQuery.setParameter("inicio", fechaInicio);
			hQuery.setParameter("fin", fechaFin);

			return ((Long) hQuery.uniqueResult()).intValue();
		} catch(HibernateException ex) {
			String message = String.format(
					"No se pudo recuparar el paquete activo del cliente.", getType().getSimpleName());
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}
}