package com.entich.ezfact.facturacion.service.impl;

import javax.transaction.Transactional;

import org.apache.xmlbeans.impl.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.service.impl.CertificadoServiceImpl;
import com.mx.profact.ws.ArrayOfAnyType;
import com.mx.profact.ws.Timbrado;
import com.mx.profact.ws.TimbradoSoap;

@Service("certificadoServiceProfact")
public class CertificadoServiceProfact extends CertificadoServiceImpl {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CertificadoServiceProfact.class);

	@Value("${facturacion.user.name}")
	private String user;

	@Transactional
	@Override
	public void create(Certificado certificado) {
		super.create(certificado);

		Timbrado timbrado = new Timbrado();
		TimbradoSoap cfdi = timbrado.getTimbradoSoap();

		ArrayOfAnyType res = cfdi.registraEmisor(user, certificado.getEmisor()
				.getRfc(),
				new String(Base64.encode(certificado.getCertificado())),
				new String(Base64.encode(certificado.getClave())), certificado
						.getPassword());

		Integer code = (Integer) res.getAnyType().get(1);

		if (code.intValue() != 0) {
			String message = (String) res.getAnyType().get(2);
			throw new ServiceException(message);
		}
	}
}
