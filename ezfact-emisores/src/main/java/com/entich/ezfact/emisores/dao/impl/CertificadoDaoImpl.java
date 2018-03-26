package com.entich.ezfact.emisores.dao.impl;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entich.commons.dao.impl.GenericHibernateDaoImpl;
import com.entich.commons.exceptions.dao.DataAccessException;
import com.entich.ezfact.emisores.dao.CertificadoDao;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;

/**
 * Acceso a datos de los certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 27/11/2013
 */
@Repository
public class CertificadoDaoImpl extends
		GenericHibernateDaoImpl<Certificado, Long> implements CertificadoDao {

	private static final Logger LOG = LoggerFactory.getLogger(CertificadoDaoImpl.class);
	@Transactional
	@Override
	public Certificado read(Emisor emisor) {
		try {
			return (Certificado) this.sessionFactory.getCurrentSession()
					.createQuery("from Certificado cer where cer.emisor.id = :id")
					.setLong("id", emisor.getId()).uniqueResult();
		} catch (HibernateException ex) {
			String message = "Error al intentar recuerar el certificado del emisor.";
			LOG.warn(message, ex);
			throw new DataAccessException(message, ex);
		} 
 	}
}