package com.entich.ezfact.folios.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.folios.model.PaqueteEmisor;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.ezfact.emisores.dao.impl.CertificadoDaoImpl;
import com.entich.ezfact.folios.model.PaqueteUsuario;
import com.entich.ezfact.folios.dao.PaqueteUsuarioDao;
import com.entich.ezfact.folios.model.Paquete;
import com.entich.ezfact.usuarios.model.Usuario;

@Repository("PaqueteUsuarioDao")
public class PaqueteUsuarioDaoImpl extends GenericHibernateDaoImpl<PaqueteUsuario, Long>
implements PaqueteUsuarioDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(PaqueteUsuarioDao.class);
	
	@Transactional
	@Override
	public PaqueteUsuario read(Paquete paquete) {
		try {
			return (PaqueteUsuario) this.sessionFactory.getCurrentSession()
					.createQuery("from PaqueteUsuario pu where pu.paquete.id = :id")
					.setLong("id", paquete.getId()).uniqueResult();
		} catch (HibernateException ex) {
			String message = "Error al intentar recuperar el paquete del Usuario.";
			LOG.warn(message, ex);
			throw new DataAccessException(message, ex);
		} 
 	}
	
	@Transactional
	@Override
	public PaqueteUsuario read(Usuario usuario) {
		try {
			return (PaqueteUsuario) this.sessionFactory.getCurrentSession()
					.createQuery("from PaqueteUsuario pu where pu.usuario.id = :id and pu.activo = 1")
					.setLong("id", usuario.getId()).uniqueResult();
		} catch (HibernateException ex) {
			String message = "Error al intentar recuperar el paquete del Usuario.";
			LOG.warn(message, ex);
			throw new DataAccessException(message, ex);
		} 
 	}

	@javax.transaction.Transactional
	@Override
	public PaqueteUsuario getPaqueteActivo(Usuario usuario) {
		try {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(getType());

			criteria.add(Restrictions.eq("usuario", usuario))
					.add(Restrictions.eq("activo", true))
					.add(Restrictions.le("adquirido", calendar.getTime()))
					.add(Restrictions.ge("vencimiento", calendar.getTime()))

					/*.add(Restrictions.eq("activo", true))
					.add(Restrictions.le("p.adquirido", calendar.getTime()))
					.add(Restrictions.ge("p.vencimiento", calendar.getTime()))*/
			;

			return (PaqueteUsuario) criteria.uniqueResult();
		} catch (HibernateException ex) {
			String message = String.format(
					"No se pudo recuparar el paquete activo del cliente.", getType().getSimpleName());
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}

	@javax.transaction.Transactional
	@Override
	public Integer getFoliosUtilizados(PaqueteUsuario paquete, Emisor... emisor) {
		try {
			String query = "select count(*) from Comprobante c where "
					+ "c.uuid != null and c.emisor in (:emisor) "
					+ "and c.fechaCreacion between :inicio and :fin";

			Query hQuery = this.sessionFactory.getCurrentSession().createQuery(query);

			hQuery.setParameterList("emisor", emisor);
			hQuery.setParameter("inicio", paquete.getAdquirido());
			hQuery.setParameter("fin", paquete.getVencimiento());


			return ((Long) hQuery.uniqueResult()).intValue();
		} catch(HibernateException ex) {
			String message = String.format(
					"No se pudo recuparar el paquete activo del cliente.", getType().getSimpleName());
			LOGGER.error(message, ex);
			throw new DataAccessException(message, ex);
		}
	}

}
