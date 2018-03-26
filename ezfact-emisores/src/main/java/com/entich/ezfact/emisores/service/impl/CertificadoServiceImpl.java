package com.entich.ezfact.emisores.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.ssl.PKCS8Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.commons.exceptions.service.ValidationException;
import com.entich.ezfact.emisores.dao.CertificadoDao;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.service.CertificadoService;

/**
 * Servicio de los certificados.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 27/11/2013
 */
@Service("certificadoService")
public class CertificadoServiceImpl implements CertificadoService {

	/**
	 * Constante que representa el log.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(CertificadoServiceImpl.class);

	/**
	 * Dependencia del acceso a datos.
	 */
	@Autowired
	private CertificadoDao certificadoDao;

	/**
	 * Dependencia del validador de beans.
	 */
	@Autowired
	private Validator validator;

	@Override
	public Collection<Certificado> getAll() {
		LOG.debug("Obteniendo los emisores existentes.");
		Collection<Certificado> certificados = null;
		certificados = certificadoDao.findAll();
		if (certificados.isEmpty()) {
			LOG.info("No se encontraron certificados.");
		}
		return certificados;
	}

	@Transactional
	@Override
	public void create(Certificado certificado) {
		LOG.debug("Creando un certificado.");
		try {
			Assert.notNull(certificado, "El certificado es nulo.");
			process(certificado);
			validar(certificado);
			Assert.notNull(certificado.getEmisor().getId(),
					"El id del emisor es nulo.");

			certificadoDao.create(certificado);
		} catch (IllegalArgumentException ex) {
			LOG.warn(ex.getMessage());
			throw new ServiceException(ex.getMessage());
		}
	}

	private void validar(Certificado certificado) {
		Set<ConstraintViolation<Certificado>> violations = validator
				.validate(certificado);

		if (CollectionUtils.isNotEmpty(violations)) {
			String message = "El certificado tiene errores de validación.";
			LOG.warn(message + " " + violations);
			throw new ValidationException(message, violations);
		}
	}

	@Override
	public Certificado read(Long id) {
		LOG.debug("Obteniendo un certificado por id.");
		try {
			Assert.notNull(id);
			return certificadoDao.read(id);
		} catch (IllegalArgumentException ex) {
			String message = "El identificador es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Transactional
	@Override
	public void update(Certificado certificado) {
		LOG.debug("Actualizando un certificado.");
		try {
			Assert.notNull(certificado, "El certificado es nulo.");
			Assert.notNull(certificado.getId(),
					"El id del certificado es nulo.");
			validar(certificado);
			Assert.notNull(certificado.getEmisor().getId(),
					"El id del emisor es nulo.");

			certificadoDao.update(certificado);
		} catch (IllegalArgumentException ex) {
			LOG.warn(ex.getMessage());
			throw new ServiceException(ex.getMessage());
		}
	}

	@Transactional
	@Override
	public void delete(Certificado certificado) {
		LOG.debug("Eliminando un certificado.");
		try {
			Assert.notNull(certificado, "El certificado es nulo.");
			Assert.notNull(certificado.getId(),
					"El id del certificado es nulo.");
			certificadoDao.delete(certificado);
		} catch (IllegalArgumentException ex) {
			LOG.warn(ex.getMessage());
			throw new ServiceException(ex.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Certificado> search(Map map) {
		LOG.debug("Buscando certificados");
		Collection<Certificado> certificados = null;
		if (map != null && !map.isEmpty()) {
			certificados = certificadoDao.search(map.entrySet());
		} else {
			certificados = certificadoDao.findAll();
		}
		if (certificados.isEmpty()) {
			LOG.info("No se encontraron certificados.");
		}
		return certificados;
	}

	/**
	 * Método que obtiene la información del certificado y asigna la fecha de
	 * inicio, de fin y la serie al mismo.
	 * 
	 * @param certificado
	 *            el certificado
	 */
	private void process(Certificado certificado) {
		InputStream stream = null;
		Hex hex = new Hex();
		try {
			if (certificado.getCertificado() != null) {
				stream = new ByteArrayInputStream(certificado.getCertificado());
				CertificateFactory factory = CertificateFactory
						.getInstance("X.509");
				X509Certificate certificate = (X509Certificate) factory
						.generateCertificate(stream);
				certificado.setFin(certificate.getNotAfter());
				certificado.setInicio(certificate.getNotBefore());
				certificado.setSerie(new String(hex.decode(certificate
						.getSerialNumber().toString(16).getBytes())));
			}
			byte[] pfx = createPKCS12(certificado);
			if (pfx == null) {
				throw new ServiceException(
						"Error al procesar el certificado, valide los archivos y el password proporcionados.");
			}
			certificado.setPfx(pfx);
		} catch (CertificateException ex) {
			String message = "Error al obtener la información del certificado.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		} catch (DecoderException ex) {
			String message = "Error al obtener el numero de serie del certificado.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException ex) {
				String message = "Ocurrrió un error al liberar el strem.";
				LOG.warn(message, ex);
				throw new ServiceException(message, ex);
			}
		}
	}

	@Override
	public Certificado read(Emisor emisor) {
		LOG.debug("Obteniendo un certificado por id.");
		try {
			Assert.notNull(emisor);
			return certificadoDao.read(emisor);
		} catch (IllegalArgumentException ex) {
			String message = "El identificador es nulo.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public byte[] createPKCS12(Certificado certificado) {
		try {
			String file = "certificado.pfx";

			PrivateKey privateKey = getPrivateKey(certificado);
			Certificate[] outChain = getCertificate(certificado);

			if (privateKey != null && outChain != null) {
				KeyStore outStore = KeyStore.getInstance("PKCS12");
				outStore.load(null, certificado.getPassword().toCharArray());
				outStore.setKeyEntry("certificadoPFX", privateKey, certificado
						.getPassword().toCharArray(), outChain);
				OutputStream outputStream = new FileOutputStream(file);
				outStore.store(outputStream, certificado.getPassword()
						.toCharArray());

				outputStream.flush();
				outputStream.close();

				File filePfx = new File(file);

				byte[] byteArray = FileUtils.readFileToByteArray(filePfx);

				if (filePfx.exists()) {
					filePfx.delete();
				}

				return byteArray;
			}
		} catch (IOException | KeyStoreException | NoSuchAlgorithmException
				| CertificateException ex) {
			LOG.warn("Problemas para generar el archivo PFX..", ex);
		}
		return null;
	}

	private Certificate[] getCertificate(Certificado certificado) {
		Certificate[] chain = null;

		InputStream in = new ByteArrayInputStream(certificado.getCertificado());
		CertificateFactory certFactory;

		try {
			certFactory = CertificateFactory.getInstance("X.509");
			chain = new Certificate[] { certFactory.generateCertificate(in) };
		} catch (CertificateException ex) {
			LOG.warn("Error al generar la cadena de certificados.", ex);
		}

		return chain;
	}

	private PrivateKey getPrivateKey(Certificado certificado) {
		PKCS8Key pkcs8;
		try {
			pkcs8 = new PKCS8Key(certificado.getClave(), certificado
					.getPassword().toCharArray());

			return pkcs8.getPrivateKey();
		} catch (GeneralSecurityException ex) {
			LOG.warn("Error al generar la clave privada.", ex);
		}
		return null;
	}
}