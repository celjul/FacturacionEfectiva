package com.entich.ezfact.emisores.service;

import java.util.Collection;
import java.util.Map;

import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;

/**
 * Interface que define el comportamiento del servicio de los certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 27/11/2013
 */
public interface CertificadoService {

	/**
	 * Método que obtiene una lista de los certificados existentes.
	 * 
	 * @return una lista de certificados
	 * @see {@link Certificado}
	 */
	Collection<Certificado> getAll();

	/**
	 * Método que crea un nuevo certificado.
	 * 
	 * @param certificado
	 *            certificado
	 * @see {@link Certificado}
	 */
	void create(Certificado certificado);

	/**
	 * Método que obtiene un certificado a partir de un id.
	 * 
	 * @param id
	 *            id
	 * @return un certificado
	 * @see {@link Certificado}
	 */
	Certificado read(Long id);
	
	/**
	 * Método que obtiene un certificado a partir del emisor.
	 * 
	 * @param emisor Emisor del cual se busca el certificado.
	 * 
	 * @return Datos de certificado.
	 */
	Certificado read(Emisor emisor);

	/**
	 * Método que actualiza un certificado.
	 * 
	 * @param certificado
	 *            certificado
	 * @see {@link Certificado}
	 */
	void update(Certificado certificado);

	/**
	 * Método que elimina un certificado.
	 * 
	 * @param certificado
	 *            certificado
	 * @see {@link Certificado}
	 */
	void delete(Certificado certificado);

	/**
	 * Método que obtiene una lista de certificados existentes de acuerdo a los
	 * criterios especificados en el mapa.
	 * 
	 * @param map
	 *            mapa de criterios
	 * @return una lista de certificados
	 * @see {@link Certificado}
	 */
	@SuppressWarnings("rawtypes")
	Collection<Certificado> search(Map map);
	
	byte[] createPKCS12(Certificado certificado);
}