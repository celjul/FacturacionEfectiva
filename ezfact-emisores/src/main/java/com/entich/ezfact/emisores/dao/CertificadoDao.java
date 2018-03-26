package com.entich.ezfact.emisores.dao;

import com.entich.commons.dao.GenericDao;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;

/**
 * Interface que define el comportamiento del acceso a datos de los
 * certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 27/11/2013
 */
public interface CertificadoDao extends GenericDao<Certificado, Long> {

	Certificado read(Emisor emisor);
}