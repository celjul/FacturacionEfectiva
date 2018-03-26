package com.entich.emisores.test.dummies;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.entich.commons.exceptions.service.ServiceException;
import com.entich.ezfact.emisores.factory.CertificadoFactory;
import com.entich.ezfact.emisores.factory.EmisorFactory;
import com.entich.ezfact.emisores.model.Certificado;
import com.entich.ezfact.emisores.model.Emisor;
import com.entich.ezfact.emisores.model.EmisorMoral;

/**
 * Clase que crea instancias dummy para las pruebas unitarias de un emisor.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 29/11/2013
 */
public class CertificadoDummyGenerator {

	private static int index = 0;

	public static Certificado getCertificado() {

		try {
			Certificado certificado = CertificadoFactory.newInstance();
			Resource cert = new ClassPathResource("/certificados/emisor.cer");
			certificado.setCertificado(FileUtils
					.readFileToByteArray(cert.getFile()));
			Resource key = new ClassPathResource("/certificados/emisor.key");
			certificado.setClave(FileUtils.readFileToByteArray(key.getFile()));
			certificado.setEmisor((Emisor) EmisorFactory.newInstance(
					EmisorMoral.class, 2L));
			certificado.setFin(getDate(2016, 12, 31));
			certificado.setInicio(getDate(2013, 01, 01));
			certificado.setNombre("Certificado de Prueba");
			certificado.setPassword("12345678a");
			certificado.setSerie("0000100000020256926" + index);
			index++;
			return certificado;
		} catch (IOException ex) {
			throw new ServiceException (
					"Ocurrió un error al crear el certificado", ex);
		}
	}

	public static String insertChars(String string, int chars) {
		StringBuilder builder = new StringBuilder(string);
		for (int i = string.length(); i <= chars; i++) {
			builder.append("x");
		}
		return builder.toString();
	}

	private static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}